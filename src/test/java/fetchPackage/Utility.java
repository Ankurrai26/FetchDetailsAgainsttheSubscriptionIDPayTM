package fetchPackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Utility {

	public static String fetchFromPropertiesFile(String value) throws IOException {
		
		Properties pro = new Properties();
		FileInputStream file = new FileInputStream("C:\\Users\\PolicyGuru\\NewPayTMClass\\FetchSub\\authdata.properties");
		pro.load(file);
		String Value = pro.getProperty(value);
		
		return Value;
		
		
		
	}
	
	
}
