package molab.test.java.util;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import molab.main.java.entity.Subtask;

public class RetentionTester {
	
	private static final Logger log = Logger.getLogger(RetentionTester.class.getName());

	public static void main(String[] args) {
		Map<Integer, Subtask> stMap = new HashMap<Integer, Subtask>();
		log.info(String.valueOf(stMap == null));
		
		Subtask st = new Subtask();
		st.setActual(100);
		stMap.put(1, st);
		
		Subtask st1 = new Subtask();
		stMap.put(2, st1);
		st1.setActual(200);
		
		log.info(String.valueOf(stMap.get(1).getActual()));
		log.info(String.valueOf(stMap.get(2).getActual()));
		
		test(stMap);
		log.info(String.valueOf(stMap.get(1).getActual()));
		
		log.info("DONE");
	}
	
	private static void test(Map<Integer, Subtask> stMap) {
		stMap.get(1).setActual(150);
	}

}
