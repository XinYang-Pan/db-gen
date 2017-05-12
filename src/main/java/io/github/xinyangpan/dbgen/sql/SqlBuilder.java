package io.github.xinyangpan.dbgen.sql;

import io.github.xinyangpan.dbgen.vo.DbTablePair;

public interface SqlBuilder {
	
	public String createOrAlterSql(DbTablePair dbTablePair);
	
}
