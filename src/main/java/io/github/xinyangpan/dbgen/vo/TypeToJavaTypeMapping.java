package io.github.xinyangpan.dbgen.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import io.github.xinyangpan.codegen.classfile.wrapper.ClassWrapper;

public class TypeToJavaTypeMapping {

	private static Map<String, Class<?>> typeStrToJavaType = Maps.newHashMap();

	static {
		// 
		typeStrToJavaType.put("String", String.class);
		typeStrToJavaType.put("Integer", Integer.class);
		typeStrToJavaType.put("Long", Long.class);
		typeStrToJavaType.put("Date", Date.class);
		typeStrToJavaType.put("Boolean", Boolean.class);
		typeStrToJavaType.put("BigDecimal", BigDecimal.class);
	}

	public static Class<?> getJavaType(String rawType) {
		Preconditions.checkNotNull(rawType);
		return Preconditions.checkNotNull(typeStrToJavaType.get(rawType), String.format("No java class found for %s.", rawType));
	}

	public static void populateJavaType(DbType dbType) {
		Preconditions.checkNotNull(dbType);
		Class<?> javaType = typeStrToJavaType.get(dbType.getRawType());
		Preconditions.checkNotNull(javaType, String.format("No java class found for %s.", dbType));
		dbType.setJavaType(ClassWrapper.of(javaType));
	}

}
