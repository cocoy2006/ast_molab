package molab.test.java.util;

import java.text.ParseException;
import java.util.Date;
import java.util.logging.Logger;

import molab.main.java.util.Molab;

public class MolabTester {

	private static final Logger log = Logger.getLogger(MolabTester.class.getName());
	
	public static void main(String[] args) throws ParseException {
		long today = Molab.parseDate("2016-02-29");
		
//		for(int i = 21; i < 31; i++) {
//			long nextMonth = Molab.parseDate(today, i);
//			System.out.println("i: " + i + ", nextMonth: " + new Date(nextMonth));
//		}
		
		long nextMonth = Molab.parseDate(today, -30);
		System.out.println(new Date(nextMonth));
		
		log.info("DONE");
	}

}
