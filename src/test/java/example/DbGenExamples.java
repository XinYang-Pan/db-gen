package example;

import java.io.IOException;
import java.net.URL;

import io.github.xinyangpan.commons.tostring.ToStringUtils;
import io.github.xinyangpan.dbgen.DbTools;
import io.github.xinyangpan.dbgen.sql.PostgresSqlBuilder;
import jxl.read.biff.BiffException;

public class DbGenExamples {

	public static void main(String[] args) throws BiffException, IOException {
		URL url = DbGenExamples.class.getResource("test1.xls");
		DbTools dbTools = DbTools.build(url.getPath());
		//
		dbTools.setSqlBuilder(new PostgresSqlBuilder());
//		dbTool.setPrintToConsole(true);
		System.out.println("********************************");
		System.out.println(ToStringUtils.wellFormat(dbTools.getDbConfig()));
		System.out.println("********************************");
		dbTools.generateCreateDdls();
		System.out.println("********************************");
		dbTools.generateEnums();
		System.out.println("********************************");
		dbTools.generatePoAndDaos();
		System.out.println("********************************");
	}

}
