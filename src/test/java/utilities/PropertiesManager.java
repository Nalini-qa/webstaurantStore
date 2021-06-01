package utilities;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import org.testng.Reporter;

public class PropertiesManager {

	static Properties properties = new Properties();

	public PropertiesManager() {
		try {
			FileInputStream fileInputStream = new FileInputStream(
					System.getProperty("user.dir") + "/Config.properties");
			properties.load(fileInputStream);
			Set<String> props = properties.stringPropertyNames();
			props.forEach(key -> {
				if (System.getProperty(key) != null)
					System.setProperty(key, System.getProperty(key));
				else
					System.setProperty(key, properties.getProperty(key));
			});
		} catch (Exception e) {
			Reporter.log("issue in reading properties from prop or system due to " + e.getMessage(), true);
		}
	}

}
