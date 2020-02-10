package com.prao.ainterview.tmdb.automation.util;

import java.io.FileInputStream;
import java.util.Properties;
//import org.springframework.http.HttpStatus;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;


/**
 * Created by Pragna Rao on 02/06/2020.
 */

public class TmdbTestUtil {

	private static Properties globalProperties = null;
		
	
	@Parameters({ "envType", "APIKey" })
	@BeforeSuite
	public static void initFramework(@Optional("qa") String configfile,
			@Optional("fb54f1b85b06b34f0b5c921561fc3675") String apiKey) throws Exception {
		configfile= configfile + ".configuration.properties";
		init(configfile);
		globalProperties.put("APIKey", apiKey);
		}
	

	public static void init(String configFile) {
		try {
			FileInputStream fis = new FileInputStream(configFile);
			globalProperties = new Properties();
			globalProperties.load(fis);
		} catch (Exception e) {
		}
	}
	
	protected String getProperty(String name) {
		return globalProperties.getProperty(name);
	}


	/*protected String convertToJson(Object obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	*/


}
