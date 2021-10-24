package tests;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.helpers.GistsAPIService;
import com.utils.ReportUtility;
import com.utils.JsonUtility;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

@Listeners(com.utils.ReportUtility.class)
public class ReadGistTest extends ReportUtility {
	
	private GistsAPIService gistAPIService;
    JsonUtility jsonUtility = new JsonUtility();
    ObjectMapper mapper = new ObjectMapper();

    @BeforeClass
    public void init(){
    	gistAPIService =new GistsAPIService();
    }

	/*
	 * This test creates a gist first, stores gist's id & then does a GET call with
	 * that id to validate all the details are same in both POST & GET call responses
	 */
    
    @Test
    public void testReadGist() throws IOException{
    	
    	String postRequestPayload;
    	
    	// Fetching request payload from testData folder
    	postRequestPayload = jsonUtility.readFileAsString("resources/requestPayload2.json");
    	
    	// POST call to Create a Gist
    	Response postCallResponse = gistAPIService.createGist(postRequestPayload);
    	Assert.assertEquals(postCallResponse.getStatusCode(), HttpStatus.SC_CREATED);
    	
    	// Fetch gistId from POST call's response
    	Object gistId = jsonUtility.getJsonPathFieldValue(postCallResponse.asString(), "id");
    	
    	// GET call with above created gistId
    	Response getCallResponse = gistAPIService.getGist(gistId.toString());
    	Assert.assertEquals(getCallResponse.getStatusCode(), HttpStatus.SC_OK);
    	
    	// Validate GET Call's response against POST's call response
    	Assert.assertEquals(getCallResponse.getBody().asString(), postCallResponse.getBody().asString());
    }
  
}
