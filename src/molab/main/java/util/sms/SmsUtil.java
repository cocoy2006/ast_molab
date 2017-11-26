package molab.main.java.util.sms;

import java.io.File;

import molab.main.java.util.Molab;

public class SmsUtil {

	public static String getMobilenum(Integer pid) {
		return Ypyun.getMobilenum(pid);
	}
	
	public static String getVcode(Integer pid, String mobile) {
		return Ypyun.getVcode(pid, mobile);
	}
	
	private static final String PID_PROPERTIES = "pid.ypyun.properties";
	public static String parseVcode(Integer pid, String sms) {
		String parser = Molab.getProperty(Molab.getPidDir() + File.separator
				+ PID_PROPERTIES, Integer.toString(pid));
		if(parser != null) {
			int startIndex = Integer.parseInt(parser.substring(0, parser.indexOf("|")));
			int length = Integer.parseInt(parser.substring(parser.indexOf("|") + 1));
			return sms.substring(startIndex, startIndex + length);
		}
		return null;
	}
	
}
