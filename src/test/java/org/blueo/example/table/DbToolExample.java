package org.blueo.example.table;

import java.io.IOException;
import java.net.URL;

import org.blueo.db.DbTool;
import org.blueo.db.sql.PostgresSqlBuilder;

import io.github.xinyangpan.commons.tostring.ToStringUtils;
import jxl.read.biff.BiffException;

public class DbToolExample {

	public static void main(String[] args) throws BiffException, IOException {
		URL url = DbToolExample.class.getResource("test1.xls");
		DbTool dbTool = DbTool.build(url.getPath());
		//
		dbTool.setSqlBuilder(new PostgresSqlBuilder());
		dbTool.setPrintToConsole(true);
		System.out.println("********************************");
		System.out.println(ToStringUtils.wellFormat(dbTool.getDbConfig()));
		System.out.println("********************************");
		dbTool.generateCreateDdls();
		System.out.println("********************************");
		dbTool.generateEnums();
		System.out.println("********************************");
		dbTool.generatePoAndDaos();
		System.out.println("********************************");
	}

}
