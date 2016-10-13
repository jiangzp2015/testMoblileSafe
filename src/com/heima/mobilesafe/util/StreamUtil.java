package com.heima.mobilesafe.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamUtil {
	public static String readStream(InputStream inputStream)throws Exception {
		
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		byte[] buffer=new byte[1024];
		int len;
		while ((len=inputStream.read(buffer))>0) {
			bos.write(buffer, 0, len);
		}
		
		return bos.toString();
	}
}
