package molab.main.java.util.zip;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import com.android.sdklib.xml.AndroidManifestParser;
import com.android.sdklib.xml.ManifestData;

public class ApkHelper {
	
	private static final Logger log = Logger.getLogger(ApkHelper.class.getName());
	private static ZipInputStream zipIn;
	private static ZipEntry zipEntry;
	private static byte[] buf = new byte[512];
	private static int readedBytes;
	private static final float[] RADIX_MULTS = { 0.0039063F, 3.051758E-005F,
			1.192093E-007F, 4.656613E-010F };

	public static void unzip(File file) {
		try {
			String folderPath = file.getParent().concat(File.separator)
				.concat(file.getName()).concat("_zip").concat(File.separator);
			ZipFile zipFile = new ZipFile(file);
			ZipInputStream zis = new ZipInputStream(new BufferedInputStream(
					new FileInputStream(file)));
			ZipEntry zipEntry = null;
			while ((zipEntry = zis.getNextEntry()) != null) {
				String fileName = zipEntry.getName();
				if("AndroidManifest.xml".equalsIgnoreCase(fileName)
						|| fileName.indexOf("drawable") > -1) {
					File temp = new File(folderPath + fileName);
					if (!temp.getParentFile().exists())
						temp.getParentFile().mkdirs();
					OutputStream os = new FileOutputStream(temp);
					// 通过ZipFile的getInputStream方法拿到具体的ZipEntry的输入流
					InputStream is = zipFile.getInputStream(zipEntry);
					int len = 0;
					System.out.println(zipEntry.getName());
					while ((len = is.read()) != -1)
						os.write(len);
					os.close();
					is.close();
				}
			}
			zis.close();
			zipFile.close();
		} catch (Exception ex) {

		}
	}
	
	/**
	 * unzip .apk file, and return 'AndroidManifest.xml' file
	 * 
	 * @param .apk file's absolute path
	 * @return AndroidManifest.xml file or null
	 * */
	public static File unZip(String apkFilePath) {
		try {
			File apkFile = new File(apkFilePath);
			String apkFileParent = apkFile.getParent();
			String apkFileName = apkFile.getName();
			zipIn = new ZipInputStream(new BufferedInputStream(
					new FileInputStream(apkFile)));

			while ((zipEntry = zipIn.getNextEntry()) != null) {
				if ("AndroidManifest.xml".equalsIgnoreCase(zipEntry.getName())) {
					File file = new File(apkFileParent + File.separatorChar + apkFileName + ".xml");
					FileOutputStream fileOut = new FileOutputStream(file);
					while ((readedBytes = zipIn.read(buf)) > 0) {
						fileOut.write(buf, 0, readedBytes);
					}
					fileOut.close();
					return file;
				}
				zipIn.closeEntry();
			}
			
			
		} catch (IOException e) {
			log.severe(e.getMessage());
		}
		return null;
	}
	
	public static ManifestData getManifestData(String apkFilePath) {
		return getManifestData(unZip(apkFilePath));
	}
	
	public static ManifestData getManifestData(File androidManifestXml) {
		File amx = AXMLPrinter.parseToText(androidManifestXml.getAbsolutePath());
		try {
			return AndroidManifestParser.parse(new FileInputStream(amx));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public static String getStartActivity(ManifestData md) {
		if(md != null) {
			return md.getPackage().concat("/").concat(md.getLauncherActivity().getName()); 
		}
		return null;
	}
	
	public static String getPackage(ManifestData md) {
		if(md != null) {
			return md.getPackage();
		}
		return null;
	}

	public static float complexToFloat(int complex) {
		return (complex & 0xFFFFFF00) * RADIX_MULTS[(complex >> 4 & 0x3)];
	}
}
