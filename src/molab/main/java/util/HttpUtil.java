package molab.main.java.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class HttpUtil {

	private static final Logger log = Logger.getLogger(HttpUtil.class.getName());

	public static int httpRequest(String url) {
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		// 创建GET方法的实例
		GetMethod getMethod = new GetMethod(url);

		try {
			// 执行getMethod
			int statusCode = httpClient.executeMethod(getMethod);
			return statusCode;
		} catch (Exception e) {
			log.severe(e.getMessage());
		} finally {
			getMethod.releaseConnection();
		}
		return -1;
	}

	public static int httpFilePost(String host, File file,
			Map<String, String> params) throws FileNotFoundException {
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(host);
		Part[] parts = { new FilePart(file.getName(), file) };
		int size = 0;
		if (!params.isEmpty()) {
			size = params.size();
			parts = new Part[size + 1];
			parts[0] = new FilePart(file.getName(), file);
			int i = 1;
			for (String name : params.keySet()) {
				parts[i++] = new StringPart(name, params.get(name));
			}
		}
		postMethod.setRequestEntity(new MultipartRequestEntity(parts,
				postMethod.getParams()));
		try {
			int statusCode = httpClient.executeMethod(postMethod);
			return statusCode;
		} catch (Exception e) {
			log.severe(e.getMessage());
		} finally {
			postMethod.releaseConnection();
		}
		return -1;

	}

	public static int httpFilePost(String host, File file)
			throws FileNotFoundException {
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(host) {
			public String getRequestCharSet() {
				return "UTF-8";
			}
		};
		Part[] parts = { new FilePart(file.getName(), file, null, "UTF-8") };
		postMethod.setRequestEntity(new MultipartRequestEntity(parts,
				postMethod.getParams()));
		try {
			int statusCode = httpClient.executeMethod(postMethod);

			if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY
					|| statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
				// 从头中取出转向的地址
				Header locationHeader = postMethod.getResponseHeader("location");
				String location = null;
				if (locationHeader != null) {
					location = locationHeader.getValue();
					System.out.println("The page was redirected to:" + location);
				} else {
					System.err.println("Location field value is null.");
				}
			}


			return statusCode;
		} catch (Exception e) {
			log.severe(e.getMessage());
		} finally {
			postMethod.releaseConnection();
		}
		return -1;
	}

	public static int httpFilePost(String host, List<File> files)
			throws FileNotFoundException {
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(host) {
			public String getRequestCharSet() {
				return "UTF-8";
			}
		};
		Part[] parts = new Part[files.size()];
		for (int i = 0; i < files.size(); i++) {
			parts[i] = new FilePart(files.get(i).getName(), files.get(i), null,
					"UTF-8");
		}
		postMethod.setRequestEntity(new MultipartRequestEntity(parts,
				postMethod.getParams()));
		try {
			int statusCode = httpClient.executeMethod(postMethod);
			return statusCode;
		} catch (Exception e) {
			log.severe(e.getMessage());
		} finally {
			postMethod.releaseConnection();
		}
		return -1;
	}
	
	public static String get(String url) {
		HttpClient client = new HttpClient();
		GetMethod get = new GetMethod(url);
		get.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
		try {
			int statusCode = client.executeMethod(get);
			if(statusCode == HttpStatus.SC_OK) {
				return get.getResponseBodyAsString();
			}
		} catch (Exception e) {
			log.severe(e.getMessage());
		} finally {
			get.releaseConnection();
		}
		return null;
	}
	
	public static String post(String host, Map<String, String> params) {
		HttpClient httpClient = new HttpClient();
		PostMethod post = new PostMethod(host);
		if (!params.isEmpty()) {
			int i = 0;
			NameValuePair[] pairs = new NameValuePair[params.size()];
			for (String name : params.keySet()) {
				pairs[i++] = new NameValuePair(name, params.get(name));
			}
			post.addParameters(pairs);
		}
		try {
			int statusCode = httpClient.executeMethod(post);
			if(statusCode == HttpStatus.SC_OK) {
				return post.getResponseBodyAsString();
			}
		} catch (Exception e) {
			log.severe(e.getMessage());
		} finally {
			post.releaseConnection();
		}
		return null;

	}
	
}
