package io.github.xinyangpan.dbgen;

import java.util.Collections;
import java.util.Formatter;
import java.util.List;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

import io.github.xinyangpan.codegen.classfile.type.ClassType;
import io.github.xinyangpan.codegen.classfile.type.EnumType;
import io.github.xinyangpan.commons.FormatterWrapper;
import io.github.xinyangpan.dbgen.config.raw.DbGlobalConfigRawData;
import io.github.xinyangpan.dbgen.java.DataLoader;
import io.github.xinyangpan.dbgen.java.PojoBuildUtils;
import io.github.xinyangpan.dbgen.sql.GenericSqlBuilder;
import io.github.xinyangpan.dbgen.sql.SqlBuilder;
import io.github.xinyangpan.dbgen.vo.DbEnum;
import io.github.xinyangpan.dbgen.vo.DbTable;
import io.github.xinyangpan.dbgen.vo.DbTablePair;

public class DbTools {
	private final String excelPath;
	private String previousExcelPath;
	private boolean printToConsole;
	private SqlBuilder sqlBuilder = new GenericSqlBuilder();
	// Internal process fields
	private DbGlobalConfigRawData dbConfig;
	private List<DbTablePair> dbTablePairs;
	private List<DbEnum> dbEnums;
	
	private DbTools(String excelPath) {
		super();
		this.excelPath = excelPath;
	}

	public static DbTools build(String excelPath) {
		DbTools dbTools = new DbTools(excelPath);
		dbTools.init();
		return dbTools;
	}

	public static DbTools build(String excelPath, String previousExcelPath) {
		DbTools dbTools = new DbTools(excelPath);
		dbTools.previousExcelPath = previousExcelPath;
		dbTools.init();
		return dbTools;
	}
	
	private void init() {
		DataLoader loader = DataLoader.build(excelPath);
		DataLoader previousLoader = null;
		if (previousExcelPath != null) {
			previousLoader = DataLoader.build(previousExcelPath);
		}
		dbConfig = loader.getDbConfig();
		dbTablePairs = this.buildDbTablePair(loader, previousLoader);
		dbEnums = loader.getDbEnums();
	}

	private List<DbTablePair> buildDbTablePair(DataLoader loader, DataLoader previousLoader) {
		List<DbTable> curr = loader.getDbTables();
		List<DbTable> prev = Collections.emptyList();
		if (previousLoader != null) {
			prev = previousLoader.getDbTables();
		}
		List<DbTablePair> dbTablePairs = Lists.newArrayList();
		// 
		for (DbTable c : curr) {
			DbTablePair dbTablePair = new DbTablePair();
			dbTablePair.setCurrent(c);
			dbTablePairs.add(dbTablePair);
			for (DbTable p : prev) {
				if (Objects.equal(c.getName(), p.getName())) {
					dbTablePair.setPrevious(p);
					break;
				}
			}
		}
		return dbTablePairs;
	}

	public void generateCreateDdls() {
		FormatterWrapper formatterWrapper;
		if (printToConsole) {
			formatterWrapper = new FormatterWrapper(new Formatter(System.out));
		} else {
			String filePath = String.format("%s/%s", dbConfig.getDdlDir(), dbConfig.getDdlFileName());
			formatterWrapper = new FormatterWrapper(FormatterWrapper.createFormatter(filePath));
		}
		for (DbTablePair dbTablePair : dbTablePairs) {
			formatterWrapper.format(sqlBuilder.createOrAlterSql(dbTablePair));
		}
		formatterWrapper.close();
	}
	
	public void generateEnums() {
		for (DbEnum dbEnum : dbEnums) {
			// 
			EnumType enumType = new EnumType();
			enumType.setPackageName(dbEnum.getPackageName());
			enumType.setName(dbEnum.getName());
			enumType.setValues(dbEnum.getValues());
			if (printToConsole) {
				enumType.processToConsole();
			} else {
				enumType.processToFile(dbConfig.getSourceDir());
			}
		}
	}
	
	public void generatePoAndDaos() {
		for (DbTablePair dbTablePair : dbTablePairs) {
			ClassType poClass = PojoBuildUtils.buildEntityClass(dbTablePair.getCurrent(), dbConfig);
			ClassType daoClass = PojoBuildUtils.buildDaoClass(poClass, dbConfig);
			if (printToConsole) {
				poClass.processToConsole();
				daoClass.processToConsole();
			} else {
				poClass.processToFile(dbConfig.getSourceDir());
				daoClass.processToFile(dbConfig.getSourceDir());
			}
		}
	}
	
	// -----------------------------
	// ----- Get Set ToString HashCode Equals
	// -----------------------------
	
	public String getExcelPath() {
		return excelPath;
	}

	public DbGlobalConfigRawData getDbConfig() {
		return dbConfig;
	}

	public boolean isPrintToConsole() {
		return printToConsole;
	}

	public SqlBuilder getSqlBuilder() {
		return sqlBuilder;
	}

	public void setSqlBuilder(SqlBuilder sqlBuilder) {
		this.sqlBuilder = sqlBuilder;
	}

	public void setPrintToConsole(boolean printToConsole) {
		this.printToConsole = printToConsole;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DbTool [excelPath=");
		builder.append(excelPath);
		builder.append(", previousExcelPath=");
		builder.append(previousExcelPath);
		builder.append(", printToConsole=");
		builder.append(printToConsole);
		builder.append(", sqlBuilder=");
		builder.append(sqlBuilder);
		builder.append(", dbConfig=");
		builder.append(dbConfig);
		builder.append(", dbTablePairs=");
		builder.append(dbTablePairs);
		builder.append(", dbEnums=");
		builder.append(dbEnums);
		builder.append("]");
		return builder.toString();
	}

}
