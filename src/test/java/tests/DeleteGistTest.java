package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.helpers.GistsAPIService;
import com.model.RateLimitResponse;
import com.model.Gists;
import com.model.History;
import com.utils.Reports;
import com.utils.ConfigManager;
import com.utils.JsonUtility;

import io.restassured.response.Response;

import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class DeleteGistTest extends Reports {
    private GistsAPIService gistAPIService;
    JsonUtility jsonUtility = new JsonUtility();
    ObjectMapper mapper = new ObjectMapper();

    @BeforeClass
    public void init(){
    	gistAPIService =new GistsAPIService();
    }

    
    @Test
    public void testDeleteGist() throws IOException{
    	
        String postRequestPayload;
    	
    	// Fetching request payload from testData folder
    	postRequestPayload = jsonUtility.readFileAsString("resources/requestPayload2.json");
    	
    	// POST call to Create a Gist
    	Response postCallResponse = gistAPIService.createGist(postRequestPayload);
    	Assert.assertEquals(postCallResponse.getStatusCode(), HttpStatus.SC_CREATED,"Created");
    	
    	// Fetch gistId from POST call's response
    	Object gistId = jsonUtility.getJsonPathFieldValue(postCallResponse.asString(), "id");
    	Response deleteCallresponse = gistAPIService.deleteGist(gistId.toString());
    	Assert.assertEquals(deleteCallresponse.getStatusCode(), HttpStatus.SC_NO_CONTENT,"No Content");
    	
    	// Make GET call with deleted gistId
    	Response getCallResponse = gistAPIService.getGist(gistId.toString());
    	Assert.assertEquals(getCallResponse.getStatusCode(), HttpStatus.SC_NOT_FOUND,"Not Found");
    }
    
}
