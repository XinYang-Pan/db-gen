package example;

import java.io.IOException;
import java.net.URL;

import io.github.xinyangpan.commons.tostring.ToStringUtils;
import io.github.xinyangpan.dbgen.DbTool;
import io.github.xinyangpan.dbgen.sql.PostgresSqlBuilder;
import jxl.read.biff.BiffException;

public class DbGenExamples {

	public static void main(String[] args) throws BiffException, IOException {
		URL url = DbGenExamples.class.getResource("test1.xls");
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
