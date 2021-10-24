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
public class DeleteGistTest  {
    private GistsAPIService gistAPIService;
    JsonUtility jsonUtility = new JsonUtility();
    ObjectMapper mapper = new ObjectMapper();
    
    @BeforeClass
    public void init(){
    	gistAPIService =new GistsAPIService();
    }

	/*
	 * This test creates a gist first, then deletes it & does a GET call to verify
	 * gist should not be found
	 */
    @Test
    public void testDeleteGist() throws IOException{
    	 
        String postRequestPayload;
    	
    	// Fetching request payload from testData folder
    	postRequestPayload = jsonUtility.readFileAsString("resources/requestPayload2.json");
    	
    	// POST call to Create a Gist
    	Response postCallResponse = gistAPIService.createGist(postRequestPayload);
    	Assert.assertEquals(postCallResponse.getStatusCode(), HttpStatus.SC_CREATED);
    	
    	// Fetch gistId from POST call's response
    	Object gistId = jsonUtility.getJsonPathFieldValue(postCallResponse.asString(), "id");
    	Response deleteCallresponse = gistAPIService.deleteGist(gistId.toString());
    	Assert.assertEquals(deleteCallresponse.getStatusCode(), HttpStatus.SC_NO_CONTENT);
    	
    	// Make GET call with deleted gistId
    	Response getCallResponse = gistAPIService.getGist(gistId.toString());
    	Assert.assertEquals(getCallResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND);
    }
    
}
