package ext.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class IOUtils {
	public static InputStream openResource(String name) {
		return IOUtils.class.getResourceAsStream("/" + name);
	}
	public static BufferedReader openReaderResource(String name) {
		InputStream s = openResource(name);
		if(s == null) return null;
		
		return new BufferedReader(new InputStreamReader(s));
	}
	
	public static InputStream openFile(String file) {
		File f = new File(file);
		if(!f.exists()) return null;
		try {
			return new FileInputStream(f);
		} catch (FileNotFoundException e) {
			return null;
		}
	}
	public static BufferedReader openReaderFile(String file) {
		InputStream s = openResource(file);
		if(s == null) return null;
		
		return new BufferedReader(new InputStreamReader(s));
	}	

	public static String readFull(BufferedReader reader) {
        StringBuffer fileData = new StringBuffer();
        char[] buf = new char[1024];
        int numRead=0;
        try {
			while((numRead=reader.read(buf)) != -1){
			    String readData = String.valueOf(buf, 0, numRead);
			    fileData.append(readData);
			}
		} catch (IOException e) {
			return null;
		}
        return fileData.toString();
	}
}
