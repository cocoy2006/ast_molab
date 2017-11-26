package molab.main.java.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import molab.main.java.entity.Application;
import molab.main.java.entity.Subtask;
import molab.main.java.util.zip.ApkHelper;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.android.sdklib.xml.ManifestData;

public class Molab {

	private static final Logger log = Logger.getLogger(Molab.class.getName());
	public static final String DEFAULT_USERS = "100";
	public static final String DEFAULT_RETENTION = "20";
	
	public static String getProperty(String file, String key) {
		try {
			Properties props = loadProperties(file);
			return props.getProperty(key);
		} catch (Exception e) {
			log.severe(e.getMessage());
			return null;
		}
	}
	
	private static Properties loadProperties(String file) {
		try {
			InputStream is = new FileInputStream(file);
			return loadProperties(is);
		} catch (Exception e) {
			log.severe(e.getMessage());
			return null;
		}
	}
	
	private static Properties loadProperties(InputStream is) {
		Properties props = new Properties();
		try {
			props.load(is);
			return props;
		} catch (Exception e) {
			log.severe(e.getMessage());
			return null;
		}
	}
	
	private static HttpSession getSession() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		return request.getSession();
	}
	
	public static String getPidDir() {
		HttpSession session = getSession();
		return session.getServletContext().getRealPath("/pid");
	}
	
	public static String getUploadDir() {
		HttpSession session = getSession();
		return session.getServletContext().getRealPath("/upload");
	}
	
	public static Application parse(File file) {
		Application application = new Application();
		try {
			application.setName(file.getName());
			application.setSize(file.length());
			application.setMd5(MD5Util.getFileMD5(file));
			ManifestData md = ApkHelper.getManifestData(file.getAbsolutePath());
			if(md != null) {
				application.setPackagename(ApkHelper.getPackage(md));
				application.setStartactivity(ApkHelper.getStartActivity(md));
			}
		} catch(Exception e) {
			log.severe(e.getMessage());
		}
		return application;
	}
	
	public static String randomImei() {
		int r1 = 1000000 + new java.util.Random().nextInt(9000000);  
	    int r2 = 1000000 + new java.util.Random().nextInt(9000000);  
	    String input = r1 + "" + r2;  
	    char[] ch = input.toCharArray();  
	    int a = 0, b = 0;  
	    for (int i = 0; i < ch.length; i++) {  
	        int tt = Integer.parseInt(ch[i] + "");  
	        if (i % 2 == 0) {  
	            a = a + tt;  
	        } else {  
	            int temp = tt * 2;  
	            b = b + temp / 10 + temp % 10;  
	        }  
	    }  
	    int last = (a + b) % 10;  
	    if (last == 0) {  
	        last = 0;  
	    } else {  
	        last = 10 - last;  
	    }  
	    return input + last;
	}

	public static String randomImsi() {
		// 460022535025034
		String title = "4600";
		int second = 0;
		do {
			second = new java.util.Random().nextInt(8);
		} while (second == 4);
		int r1 = 10000 + new java.util.Random().nextInt(90000);
		int r2 = 10000 + new java.util.Random().nextInt(90000);
		return title + "" + second + "" + r1 + "" + r2;
	}

	public static String randomMac() {
		char[] char1 = "abcdef".toCharArray();
		char[] char2 = "0123456789".toCharArray();
		StringBuffer mBuffer = new StringBuffer();
		for (int i = 0; i < 6; i++) {
			int t = new java.util.Random().nextInt(char1.length);
			int y = new java.util.Random().nextInt(char2.length);
			int key = new java.util.Random().nextInt(2);
			if (key == 0) {
				mBuffer.append(char2[y]).append(char1[t]);
			} else {
				mBuffer.append(char1[t]).append(char2[y]);
			}

			if (i != 5) {
				mBuffer.append(":");
			}
		}
		return mBuffer.toString();
	}
	
	public static String randomAndroidid() {
		char[] chars = "0123456789abcdef".toCharArray();
		StringBuffer mBuffer = new StringBuffer();
		for (int i = 0; i < 16; i++) {
			int t = new java.util.Random().nextInt(chars.length);
			mBuffer.append(chars[t]);
		}
		return mBuffer.toString();
	}
	
//	public static String randomSn() {
//		return String.valueOf(System.currentTimeMillis());
//	}
	
	public static String today() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(new Date());
	}
	
	public static String tomorrow() {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		return format.format(cal.getTime());
	}
	
//	public static long nextMonth(long date) {
//		Calendar cal = Calendar.getInstance();
//		cal.setTimeInMillis(date);
//		cal.add(Calendar.MONTH, 1);
//		return cal.getTimeInMillis();
//	}
	
	public static long parseDate(String date) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.parse(date).getTime();
	}
	
	public static long parseDate(String date, int diffDays) throws ParseException {
		return parseDate(date) + diffDays * 24 * 3600 * 1000L;
	}
	
	public static long parseDate(long date, int diffDays) throws ParseException {
		return date + diffDays * 24 * 3600 * 1000L;
	}
	
	public static long parseDate() throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.parse(format.format(new Date())).getTime();
	}
	
//	public static long parseDate(int diffDays) throws ParseException {
//		return parseDate() + diffDays * 24 * 3600 * 1000;
//	}
	
	public static int diffDays(String before, String after) throws ParseException {
		return (int) ((parseDate(after) - parseDate(before)) / (24 * 3600 * 1000) + 1);
	}
	
	public static int diffDays(long before, long after) {
		return (int) ((after - before) / (24 * 3600 * 1000));
	}
	
	public static double percentage(int percent) {
		return percent / 100D;
	}
	
	public static Map<Long, Subtask> initStMap(int users, 
			int dayRetention, int weekRetention, int monthRetention, 
			String startDay, String endDay) throws ParseException {
		int diffDays = diffDays(startDay, endDay);
		Map<Long, Subtask> stMap = new LinkedHashMap<Long, Subtask>();
		Subtask st;
		for(int day = 0; day < diffDays; day++) {
			st = new Subtask();
			st.setUsers(users);
			long startDate = parseDate(startDay, day);
			st.setStartDate(startDate);
			st.setEndDate(parseDate(startDate, 1) - 1);
			stMap.put(startDate, st);
		}
		// calculate day retention if exist
		if(dayRetention > 0) {
			int drUsers = (int) (users * percentage(dayRetention));
			for(int day = 0; day < diffDays; day++) {
				long startDate = parseDate(startDay, day + 1);
				if(stMap.containsKey(startDate)) {
					st = stMap.get(startDate);
				} else {
					st = new Subtask();
					st.setStartDate(startDate);
					st.setEndDate(parseDate(startDate, 1) - 1);
					stMap.put(startDate, st);
				}
				st.setDrUsers(drUsers);
			}
		}
		// calculate week retention if exist
		if(weekRetention > 0) {
			int wrUsers = (int) (users * percentage(weekRetention));
			for(int day = 0; day < diffDays; day++) {
				long startDate = parseDate(startDay, day + 7);
				if(stMap.containsKey(startDate)) {
					st = stMap.get(startDate);
				} else {
					st = new Subtask();
					st.setStartDate(startDate);
					st.setEndDate(parseDate(startDate, 1) - 1);
					stMap.put(startDate, st);
				}
				st.setWrUsers(wrUsers);
			}
		}
		// calculate month retention if exist
		if(monthRetention > 0) {
			int mrUsers = (int) (users * percentage(monthRetention));
			for(int day = 0; day < diffDays; day++) {
				long startDate = parseDate(startDay, day + 30);
				if(stMap.containsKey(startDate)) {
					st = stMap.get(startDate);
				} else {
					st = new Subtask();
					st.setStartDate(startDate);
					st.setEndDate(parseDate(startDate, 1) - 1);
					stMap.put(startDate, st);
				}
				st.setMrUsers(st.getMrUsers() + mrUsers);
			}
		}
		return stMap;
	}
	
	public static int total(Map<Long, Subtask> stMap) {
		if(stMap != null && stMap.size() > 0) {
			int total = 0;
			for(Subtask st :stMap.values()) {
				total += st.getUsers() + st.getDrUsers() + st.getWrUsers() + st.getMrUsers();
			}
			return total;
		}
		return 0;
	}
	
	public static List<Subtask> buildStList(Map<Long, Subtask> stMap,
			int taskId, int conversion, Integer[][] periodArray) {
		List<Subtask> stList = new ArrayList<Subtask>();
		for(Subtask subtask :stMap.values()) {
			for(int i = 0; i < periodArray.length; i++) {
				Subtask st = subtask;
				long subStartDate = subtask.getStartDate() + periodArray[i][0] * 3600000;
				long subEndDate = subtask.getStartDate() + periodArray[i][1] * 3600000 - 1;
				int percent = periodArray[i][2];
				double percentage = percentage(percent);
				int subUsers = (int) (subtask.getUsers() * percentage);
				int subSilentUsers = (int) (subtask.getUsers() * percentage(100 - conversion) * percentage);
				int subDrUsers = (int) (subtask.getDrUsers() * percentage);
				int subWrUsers = (int) (subtask.getWrUsers() * percentage);
				int subMrUsers = (int) (subtask.getMrUsers() * percentage);
				st.setTaskId(taskId);
				st.setPercent(percent);
				st.setStartDate(subStartDate);
				st.setEndDate(subEndDate);
				st.setUsers(subUsers);
				st.setSilentUsers(subSilentUsers);
				st.setDrUsers(subDrUsers);
				st.setWrUsers(subWrUsers);
				st.setMrUsers(subMrUsers);
				st.setTotal(subUsers + subDrUsers + subWrUsers + subMrUsers);
				stList.add(st);
			}
		}
		return stList;
	}
	
}
