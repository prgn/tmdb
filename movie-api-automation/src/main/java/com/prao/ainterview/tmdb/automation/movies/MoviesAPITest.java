package com.prao.ainterview.tmdb.automation.movies;
/*import com.cybozu.labs.langdetect.Detector; 
import com.cybozu.labs.langdetect.DetectorFactory; 
import com.cybozu.labs.langdetect.Language;*/
/**
 * Created by Pragna Rao on 02/06/2020.
 */
import static io.restassured.RestAssured.given;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.objectweb.asm.Type;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.prao.ainterview.tmdb.automation.util.TmdbTestUtil;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class MoviesAPITest extends TmdbTestUtil{
	
	private static final String  INTRNL_LANG_CODE = "sa";
	URI popularMovieUri,detailsMovieUri;
	
	
	private void setUp() {
		popularMovieUri = URI.create(getProperty("tmdb.base.uri")+ getProperty("popular.get.uri"));
		detailsMovieUri = URI.create(getProperty("tmdb.base.uri")+ getProperty("details.get.uri")); 
	}
	
	//Set of positive test cases
	/**
	 * @throws Exception
	 * Verify International code works for supported fields :overview.
	 * expected status code :200 OK  
	 * api_key=fb54f1b85b06b34f0b5c921561fc3675&language=hi&page=1
	 */
	@Test
	public void testGetDetailsForMovieIDTwo() throws Exception {
		setUp();

		Response  response =	given().
				queryParam("api_key", getProperty("APIKey")).
				queryParam("language", "en").expect().statusCode(200).
				when().
				get(detailsMovieUri +"2");
	
		JsonPath jsonPath = response.jsonPath();
		
		Map<String, String> prodCompanies = (Map<String, String>) jsonPath.getList("production_companies").get(0);
		
		Assert.assertEquals(jsonPath.getString("title"), "Ariel");
		Assert.assertEquals(jsonPath.getString("adult"), "false");
		Assert.assertEquals(prodCompanies.get("name"), "Villealfa Filmproductions");
		Assert.assertEquals(response.getStatusCode(),HttpStatus.SC_OK, "status code was not Httpstatus.SC_OK");
	}
	
	/**
	 * @throws Exception
	 * Verify International code works for supported fields :overview.
	 * expected status code :200 OK  
	 * api_key=fb54f1b85b06b34f0b5c921561fc3675&language=hi&page=1
	 */
	@Test
	public void testGetPopularMovieWorksForInternalLanguageCode() throws Exception {
		setUp();

		Response  response =	given().
				queryParam("api_key", getProperty("APIKey")).
				queryParam("language", INTRNL_LANG_CODE).expect().statusCode(200).
				when().
				get(popularMovieUri);
		
		Assert.assertEquals(response.getStatusCode(),HttpStatus.SC_OK, "status code was not Httpstatus.SC_OK");
	}
	
	/**
	 * @throws Exception
	 * Verify International code works for 2 digitcode.
	 * expected status code :200 OK  
	 * api_key=fb54f1b85b06b34f0b5c921561fc3675&language=en&page=1
	 */
	@Test
	public void testGetPopularMovieWorksFor2DigitCode() throws Exception {
		setUp();

		Response  response =	given().
				queryParam("api_key", getProperty("APIKey")).
				queryParam("language", "en").expect().statusCode(200).
				when().
				get(popularMovieUri);
	
		Assert.assertEquals(response.getStatusCode(),HttpStatus.SC_OK, "status code was not Httpstatus.SC_OK");
	}
	
	/**
	 * @throws Exception
	 * Verify the overview property of the movie is translated to respective language.
	 * expected status code :200 OK  
	 * api_key=fb54f1b85b06b34f0b5c921561fc3675&language=fi&page=1
	 */
	@Test
	public void testGetMovieDetailsOverviewInInternationalLanguage() throws Exception {
		//TODO
		}
	
	//Set of negative cases and needs improvement
	/***
	 * 
	 * @throws Exception
	 * 
	 *  422 (Unprocessable Entity) expected for invalid ISO 639-1 string.
	 *  A better definition and implementation needed.
	 */
	@Test
	public void testGetMovieDetailsByIdShouldThrowMeaningfulErrorForInvalidLanguageString() throws Exception {
		setUp();
		String invalidLanguage = "xxx";
		Response  response =	given().
				queryParam("api_key", getProperty("APIKey")).
				queryParam("language", invalidLanguage).
				when().
				get(detailsMovieUri+"2");
	
		Assert.assertEquals(response.getStatusCode(),HttpStatus.SC_UNPROCESSABLE_ENTITY, "status code was not Httpstatus.SC_UNPROCESSABLE_ENTITY 422 (Unprocessable Entity) status code means");
	}
	/***
	 * 
	 * @throws Exception
	 * 
	 *  Empty API key  -401 UNauthorized
	 *  A better definition and implementation needed.
	 */
	@Test
	public void testGetMovieDetailsByIdShouldThrowMeaningfulErrorForEmptyAPIKey() throws Exception {
		setUp();
		String invalidLanguage = "xxx";
		Response  response =	given().
				queryParam("api_key", "").
				queryParam("language", "en").
				when().
				get(detailsMovieUri+"2");
	
		Assert.assertEquals(response.getStatusCode(),HttpStatus.SC_UNAUTHORIZED, "status code was not Httpstatus.SC_UNAUTHORIZED");
	}
	/***
	 * 
	 * @throws Exception
	 * 
	 *  422 (Unprocessable Entity) expected for length of character exceeding whats supported.
	 *  A better definition and implementation needed.
	 */
	@Test
	public void testGetMovieDetailsByIdShouldHaveLimitOnIDCharacterLength() throws Exception {
		setUp();
		String moveID = "2222222222222222222222222";
		Response  response =	given().
				queryParam("api_key", getProperty("APIKey")).
				queryParam("language", "en").expect().statusCode(200).
				when().
				get(detailsMovieUri+moveID);
	
		Assert.assertEquals(response.getStatusCode(),HttpStatus.SC_UNPROCESSABLE_ENTITY, "status code was not Httpstatus.SC_UNPROCESSABLE_ENTITY 422 (Unprocessable Entity) status code means");
	}
	
	/***
	 * 
	 * @throws Exception
	 * 
	 *  422 (Unprocessable Entity) expected for negative numbers as MovieID.
	 *  A better definition and implementation needed.
	 */
	@Test
	public void testGetMovieDetailsByIdShouldNotAcceptNegativeNumbersAsID() throws Exception {
		setUp();
		String moveID = "-456";
		Response  response =	given().
				queryParam("api_key", getProperty("APIKey")).
				queryParam("language", "en").
				when().
				get(detailsMovieUri+moveID);
	
		Assert.assertEquals(response.getStatusCode(),HttpStatus.SC_UNPROCESSABLE_ENTITY, "status code was not Httpstatus.SC_UNPROCESSABLE_ENTITY 422 (Unprocessable Entity) status code means");
	}
	/***
	 * works!!!!
	 * @throws Exception
	 * 
	 *  XSS validations should be forbidden with a 400 error -404 .
	 *  A better definition and implementation needed.
	 */
	@Test(dataProvider="listOfXSSStrings")
	public void testGetMovieDetailsByIdShouldNotAcceptXSSVulnerableStrings(String xss) throws Exception {
		setUp();
		Response  response =	given().
				queryParam("api_key", getProperty("APIKey")).
				queryParam("language", "en").
				when().
				get(detailsMovieUri+xss);
	
		Assert.assertEquals(response.getStatusCode(),HttpStatus.SC_NOT_FOUND, "status code was not Httpstatus.SC_BAD_REQUEST");
	}
	/*
	 * public void init(String profileDirectory) throws LangDetectException {
	 * DetectorFactory.loadProfile(profileDirectory); }
	 * 
	 * public String detect(String text) throws LangDetectException { Detector
	 * detector = DetectorFactory.create(); detector.append(text); return
	 * detector.detect(); }
	 * 
	 * public ArrayList detectLangs(String text) throws LangDetectException {
	 * Detector detector = DetectorFactory.create(); detector.append(text); return
	 * detector.getProbabilities(); }
	 */
	
	/**
	 * List of XSS vulnerable strings test input params for.
	 * 
	 * @return
	 * @throws URISyntaxException
	 */
	@DataProvider(name = "listOfXSSStrings")
	public Object[][] getlistOfXSSStrings() throws URISyntaxException {
		Object[][] listOfXSSStrings = new Object[4][1];
		listOfXSSStrings[0][0] = "javascript:alert('XSS');";
		listOfXSSStrings[1][0] = "javascript:alert(&quot;XSS&quot;)";
		listOfXSSStrings[2][0] = "alert(String.fromCharCode(88,83,83))";
		listOfXSSStrings[3][0] = "jav&#x09;ascript:alert('XSS');";
				
		return listOfXSSStrings;
	}
	
}
