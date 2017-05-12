package example;

import java.io.IOException;

import jxl.read.biff.BiffException;

public class DbGenBothExamples {

	public static void main(String[] args) throws BiffException, IOException {
		DbGenExamples.main(args);
		DbToolIncreamentalExamples.main(args);
	}

}
