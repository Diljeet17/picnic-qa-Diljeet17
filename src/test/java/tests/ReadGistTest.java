package tests;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.helpers.GistsAPIService;
import com.utils.Reports;
import com.utils.JsonUtility;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

@Listeners(com.utils.Reports.class)
public class ReadGistTest extends Reports {
	
	private GistsAPIService gistAPIService;
    JsonUtility jsonUtility = new JsonUtility();
    ObjectMapper mapper = new ObjectMapper();

    @BeforeClass
    public void init(){
    	gistAPIService =new GistsAPIService();
    }

    @Test
    public void testReadGist() throws IOException{
    	
    	String postRequestPayload;
    	
    	// Fetching request payload from testData folder
    	postRequestPayload = jsonUtility.readFileAsString("resources/requestPayload2.json");
    	
    	// POST call to Create a Gist
    	Response postCallResponse = gistAPIService.createGist(postRequestPayload);
    	Assert.assertEquals(postCallResponse.getStatusCode(), HttpStatus.SC_CREATED,"Created");
    	
    	// Fetch gistId from POST call's response
    	Object gistId = jsonUtility.getJsonPathFieldValue(postCallResponse.asString(), "id");
    	
    	// GET call with above created gistId
    	Response getCallResponse = gistAPIService.getGist(gistId.toString());
    	Assert.assertEquals(getCallResponse.getStatusCode(), HttpStatus.SC_OK,"OK");
    	
    	// Validate GET Call's response against POST's call response
    	Assert.assertEquals(postCallResponse.getBody().asString(), getCallResponse.getBody().asString());
    }
  
}
