package io.github.xinyangpan.dbgen.java;

import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;

import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.github.xinyangpan.codegen.classfile.pojo.PojoField;
import io.github.xinyangpan.codegen.classfile.type.ClassType;
import io.github.xinyangpan.codegen.classfile.wrapper.AnnotationWrapper;
import io.github.xinyangpan.codegen.classfile.wrapper.ClassWrapper;
import io.github.xinyangpan.codegen.classfile.wrapper.annotation.ColumnWrapper;
import io.github.xinyangpan.codegen.classfile.wrapper.annotation.EnumeratedWrapper;
import io.github.xinyangpan.codegen.classfile.wrapper.annotation.GeneratedValueWrapper;
import io.github.xinyangpan.codegen.classfile.wrapper.annotation.SequenceGeneratorWrapper;
import io.github.xinyangpan.codegen.classfile.wrapper.annotation.TableWrapper;
import io.github.xinyangpan.codegen.tool.Tools;
import io.github.xinyangpan.dbgen.config.DbTableConfig;
import io.github.xinyangpan.dbgen.config.raw.DbGlobalConfigRawData;
import io.github.xinyangpan.dbgen.vo.DbColumn;
import io.github.xinyangpan.dbgen.vo.DbTable;
import io.github.xinyangpan.dbgen.vo.DbType;
import io.github.xinyangpan.persistent.dao.po.activeable.ActiveablePo;
import io.github.xinyangpan.persistent.dao.po.id.HasId;
import io.github.xinyangpan.persistent.dao.po.traceable.TraceablePo;

public class PojoBuildUtils {
	private static Converter<String, String> COLUMN_NAME_TO_FIELD_NAME = CaseFormat.UPPER_UNDERSCORE.converterTo(CaseFormat.LOWER_CAMEL);
	private static Converter<String, String> TABLE_NAME_TO_CLASS_NAME = CaseFormat.UPPER_UNDERSCORE.converterTo(CaseFormat.UPPER_CAMEL);

	public static ClassType buildDaoClass(ClassType poClass, DbGlobalConfigRawData dbConfig) {
		ClassType daoClass = new ClassType();
		daoClass.setPackageName(dbConfig.getDaoPackage());
		daoClass.setName(String.format("%sDao", poClass.getName()));
		daoClass.addAnnotationWrapper(new AnnotationWrapper(ClassWrapper.of("org.springframework.stereotype.Repository")));
		//
		Map<String, String> valueMap = Maps.newHashMap();
		valueMap.put("poName", poClass.getFullName());
		String superClassString = StrSubstitutor.replace(dbConfig.getDaoSuperclass(), valueMap);
		//
		daoClass.setSuperClass(ClassWrapper.of(superClassString));
		return daoClass;

	}

	public static ClassType buildEntityClass(DbTable dbTable, DbGlobalConfigRawData dbConfig) {
		if (dbTable == null) {
			return null;
		}
		DbTableConfig dbTableConfig = dbTable.getDbTableConfig();
		//
		List<PojoField> pojoFields = Lists.newArrayList();
		PojoField pk = buildEntityField(dbTable, dbTable.getPk(), dbConfig, true);
		if (pk != null) {
			pojoFields.add(pk);
		}
		for (DbColumn dbColumn : dbTable.getDbColumns()) {
			pojoFields.add(buildEntityField(dbTable, dbColumn, dbConfig, false));
		}
		//
		ClassType classType = new ClassType();
		classType.setPackageName(dbConfig.getPoPackage());
		classType.setName(TABLE_NAME_TO_CLASS_NAME.convert(dbTable.getName()));
		// Annotations 
		classType.addAnnotationWrappers(new AnnotationWrapper(Entity.class), new TableWrapper(dbTable.getName()), new AnnotationWrapper(SuppressWarnings.class, "(\"serial\")"));
		// Fields and Methods
		classType.addPojoFields(pojoFields);
		classType.addMethodPart(Tools.generateToString(classType.getFieldParts(), classType.getName()));
		// Super Class
		String poSuperclass = dbConfig.getPoSuperclass();
		if (poSuperclass != null) {
			classType.setSuperClass(ClassWrapper.of(poSuperclass));
		}
		// Interface
		List<String> poInterfacesInList = dbConfig.getPoInterfacesInList();
		for (String className : poInterfacesInList) {
			classType.addInterface(ClassWrapper.of(className));
		}
		if (dbTableConfig.isTraceable()) {
			classType.addInterface(ClassWrapper.of(TraceablePo.class, dbTableConfig.getTraceType().getJavaType().getFullName()));
		}
		if (dbTableConfig.isActiveable()) {
			classType.addInterface(ActiveablePo.class);
		}
		if (dbTableConfig.isHasId()) {
			classType.addInterface(ClassWrapper.of(HasId.class, dbTableConfig.getIdType().getJavaType().getFullName()));
		}
		return classType;
	}

	private static PojoField buildEntityField(DbTable dbTable, DbColumn dbColumn, DbGlobalConfigRawData dbConfig, boolean isPk) {
		if (dbColumn == null) {
			return null;
		}
		PojoField pojoField = new PojoField();
		pojoField.setName(COLUMN_NAME_TO_FIELD_NAME.convert(dbColumn.getName()));
		if (isPk) {
			pojoField.addGetterAnnotation(Id.class);
			String seq = dbTable.getSeq();
			if (StringUtils.isEmpty(seq)) {
				pojoField.addGetterAnnotation(GeneratedValue.class);
			} else {
				// 
				Converter<String, String> converter = CaseFormat.UPPER_UNDERSCORE.converterTo(CaseFormat.LOWER_CAMEL);
				String seqGenName = converter.convert(seq);
				// 
				pojoField.addGetterAnnotationWrapper(new SequenceGeneratorWrapper(seqGenName, seq));
				pojoField.addGetterAnnotationWrapper(new GeneratedValueWrapper(GenerationType.SEQUENCE, seqGenName));
			}
		}
		pojoField.addGetterAnnotationWrapper(new ColumnWrapper(dbColumn.getName()));
		DbType dbType = dbColumn.getDbType();
		ClassWrapper javaType = dbType.getJavaType();
		if (dbColumn.isEnumType()) {
			EnumType enumeratedType = null;
			if ("varchar".equalsIgnoreCase(dbType.getSqlType()) || "varchar2".equalsIgnoreCase(dbType.getSqlType())) {
				enumeratedType = EnumType.STRING;
			}
			pojoField.addGetterAnnotationWrapper(new EnumeratedWrapper(enumeratedType));
			pojoField.setType(javaType);
		} else {
			pojoField.setType(javaType);
		}
		return pojoField;
	}

}
