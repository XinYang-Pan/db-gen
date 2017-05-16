package example;

import java.io.IOException;
import java.net.URL;

import jxl.read.biff.BiffException;
import io.github.xinyangpan.commons.tostring.ToStringUtils;
import io.github.xinyangpan.dbgen.DbTools;
import io.github.xinyangpan.dbgen.sql.PostgresSqlBuilder;

public class DbToolIncreamentalExamples {

	public static void main(String[] args) throws BiffException, IOException {
		URL url = DbToolIncreamentalExamples.class.getResource("test1.xls");
		URL pre = DbToolIncreamentalExamples.class.getResource("test0.xls");
		DbTools dbTools = DbTools.build(url.getPath(), pre.getPath());
		//
		dbTools.setSqlBuilder(new PostgresSqlBuilder());
		dbTools.setPrintToConsole(true);
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
