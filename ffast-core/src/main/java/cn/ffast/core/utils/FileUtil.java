package cn.ffast.core.utils;

import org.apache.commons.io.FileUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 文件处理类
 */
public class FileUtil {

	/**
	 * 下载文件
	 * @param response
	 * @param filePath
	 * @param fileName
	 */
	public static void downloadFile(HttpServletResponse response,
			String filePath, String fileName) {
		
		InputStream is = null;
		OutputStream os = null;
		BufferedOutputStream bos = null;
		File file = new File(filePath);

		try {
			if(file.exists()){
				response.setContentType("APPLICATION/OCTET-STREAM;charset=UTF-8");
				fileName = fileName.replace(",", "，");
				String formatFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
				response.setHeader("Content-Disposition", "attachment; filename="+ formatFileName);
				is = new BufferedInputStream(new FileInputStream(file));
				os = response.getOutputStream();
				bos = new BufferedOutputStream(os);

				byte[] buffer = new byte[1024];

				int len = 0;

				while (-1 != (len = is.read(buffer))) {
					bos.write(buffer, 0, len);

				}
				bos.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bos != null) {
					bos.close();
				}
				if (os != null) {
					os.close();
				}
				if (is != null) {
					is.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	
	public static void deleteFile(String filePath){
		File file = new File(filePath);
		if(file.exists()){
			FileUtils.deleteQuietly(file);
		}
	}
	public static void saveFileNew(InputStream source, File dest) {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new BufferedInputStream(source);

			os = new BufferedOutputStream(new FileOutputStream(dest));
			
			int len = 0;

			byte[] buffer = new byte[1024];

			while (-1 != (len = is.read(buffer))) {
				os.write(buffer, 0, len);

			}
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


}
