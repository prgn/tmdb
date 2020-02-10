package com.prao.ainterview.tmdb.automation.util;

import static org.testng.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Properties;
//import org.springframework.http.HttpStatus;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import io.restassured.RestAssured;
import io.restassured.response.Response;


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

	/*public URI appendURI(String url, Map<String, String> params) {
		URI uri = null;
		try {
			uri = new URI(url);
			Object[] keys = params.entrySet().toArray();
			for (Object appendQuery : keys) {
				String query = uri.getQuery();
				//System.out.println("query : " + query);
				if (query == null) {
					query = appendQuery.toString();
				} else {
					query += "&" + appendQuery;
				}
				try {
					uri = new URI(uri.getScheme(), uri.getAuthority(), uri.getPath(), query, uri.getFragment());
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
			}
		} catch (URISyntaxException e1) {
		}

		return uri;
	}

	
	public Response getResponse(String url, Map<String, String> params, String token) throws IOException {
		URI uri = appendURI(url, params);
		Response response = RestAssured.
							given().
							contentType("application/json").
							when().
							header("predix-zone-id", globalProperties.get("em.ui.predix-zone-id")).
							header("Authorization", "Bearer " + token).
							get(uri).
							then().
							extract().
							response();
		assertTrue((response.statusCode() == HttpStatus.OK.value()), "Assertion Failed:Response Code expected is 200 - Code returned is:" + response.statusCode()+ "\n uri : " + uri + "\n");
		return response;
	}*/


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
