package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helpers.GistsAPIService;
import com.utils.Reports;
import com.utils.ConfigManager;
import com.utils.JsonUtility;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.List;


public class ListGistTest extends Reports {
    private GistsAPIService gistAPIService;
    JsonUtility jsonUtility = new JsonUtility();
    ObjectMapper mapper = new ObjectMapper();
    String username = ConfigManager.getInstance().getString("basic_oauth_username");
    
    @BeforeClass
    public void init(){
    	gistAPIService =new GistsAPIService();
    }
    
    @Test
    public void testListGistForAuthenticatedUser() throws IOException{
    	
    	String postRequestPayloadWithPublicFalse;
    	String postRequestPayloadWithPublicTrue;
    	
    	// Fetching request payloads from testData folder
    	postRequestPayloadWithPublicFalse = jsonUtility.readFileAsString("resources/requestPayload1.json");
    	postRequestPayloadWithPublicTrue = jsonUtility.readFileAsString("resources/requestPayload2.json");
    	
    	// POST call to Create a Gist with 'public' as false
    	Response postCallResponse1 = gistAPIService.createGist(postRequestPayloadWithPublicFalse);
    	Assert.assertEquals(postCallResponse1.getStatusCode(), HttpStatus.SC_CREATED,"Created");
    	
    	// Fetch gistId from POST call's response
    	Object gistIdWithPublicFalse = jsonUtility.getJsonPathFieldValue(postCallResponse1.asString(), "id");
    	
    	// POST call to Create a Gist with 'public' as true
    	Response postCallResponse2 = gistAPIService.createGist(postRequestPayloadWithPublicTrue);
    	Assert.assertEquals(postCallResponse2.getStatusCode(), HttpStatus.SC_CREATED,"Created");
    	
    	// Fetch gistId from POST call's response
    	Object gistIdWithPublicTrue = jsonUtility.getJsonPathFieldValue(postCallResponse2.asString(), "id");
    	
    	// GET call to list gist endpoint
    	Response listGistGetCallResponse = gistAPIService.listGistForAuthenticatedUser();
    	Assert.assertEquals(listGistGetCallResponse.getStatusCode(), HttpStatus.SC_OK,"OK");
    	
    	// Fetch all gistId from list gist GET call's response
    	List<Object> allGistIDs = (List<Object>) jsonUtility.getJsonPathFieldValue(listGistGetCallResponse.asString(), "id");
    	
    	// Validate any gist with public attribute as true or false, all are available in the response
    	Assert.assertTrue(allGistIDs.contains(gistIdWithPublicFalse));
        Assert.assertTrue(allGistIDs.contains(gistIdWithPublicTrue));
    	
    }
    
    @Test
    public void testListGistForUnAuthenticatedUser() throws IOException{
    	
    	String postRequestPayloadWithPublicFalse;
    	String postRequestPayloadWithPublicTrue;
    	
    	// Fetching request payloads from testData folder
    	postRequestPayloadWithPublicFalse = jsonUtility.readFileAsString("resources/requestPayload1.json");
    	postRequestPayloadWithPublicTrue = jsonUtility.readFileAsString("resources/requestPayload2.json");
    	
    	// POST call to Create a Gist
    	Response postCallResponse1 = gistAPIService.createGist(postRequestPayloadWithPublicFalse);
    	Assert.assertEquals(postCallResponse1.getStatusCode(), HttpStatus.SC_CREATED,"Created");
    	
    	// Fetch gistId from POST call's response
    	Object gistIdWithPublicFalse = jsonUtility.getJsonPathFieldValue(postCallResponse1.asString(), "id");
    	
    	// POST call to Create a Gist
    	Response postCallResponse2 = gistAPIService.createGist(postRequestPayloadWithPublicTrue);
    	Assert.assertEquals(postCallResponse2.getStatusCode(), HttpStatus.SC_CREATED,"Created");
    	
    	// Fetch gistId from POST call's response
    	Object gistIdWithPublicTrue = jsonUtility.getJsonPathFieldValue(postCallResponse2.asString(), "id");
    	
    	
    	// GET call to list gist endpoint
    	Response listGistGetCallResponse = gistAPIService.listGistForUnAuthenticatedUser();
    	Assert.assertEquals(listGistGetCallResponse.getStatusCode(), HttpStatus.SC_OK,"OK");
    	
    	// Fetch gistId from POST call's response
    	List<Object> allGistIDs = (List<Object>) jsonUtility.getJsonPathFieldValue(listGistGetCallResponse.asString(), "id");
    	
		/*
		 * Validate gist with public attribute as true is only available & gist with
		 * public attribute as false is not available in the response
		 */
    	Assert.assertFalse(allGistIDs.contains(gistIdWithPublicFalse));
        Assert.assertTrue(allGistIDs.contains(gistIdWithPublicTrue));
    }
    
    @Test
    public void testListGistWithUsernameForAuthenticatedUser() throws IOException{
    	
    	String postRequestPayloadWithPublicFalse;
    	String postRequestPayloadWithPublicTrue;
    	
    	// Fetching request payloads from testData folder
    	postRequestPayloadWithPublicFalse = jsonUtility.readFileAsString("resources/requestPayload3.json");
    	postRequestPayloadWithPublicTrue = jsonUtility.readFileAsString("resources/requestPayload4.json");
    	
    	// POST call to Create a Gist with 'public' as false
    	Response postCallResponse1 = gistAPIService.createGist(postRequestPayloadWithPublicFalse);
    	Assert.assertEquals(postCallResponse1.getStatusCode(), HttpStatus.SC_CREATED,"Created");
    	
    	// Fetch gistId from POST call's response
    	Object gistIdWithPublicFalse = jsonUtility.getJsonPathFieldValue(postCallResponse1.asString(), "id");
    	
    	// POST call to Create a Gist with 'public' as true
    	Response postCallResponse2 = gistAPIService.createGist(postRequestPayloadWithPublicTrue);
    	Assert.assertEquals(postCallResponse2.getStatusCode(), HttpStatus.SC_CREATED,"Created");
    	
    	// Fetch gistId from POST call's response
    	Object gistIdWithPublicTrue = jsonUtility.getJsonPathFieldValue(postCallResponse2.asString(), "id");
    	
    	// GET call to list gist endpoint
    	Response listGistGetCallResponse = gistAPIService.listGistWithUsernameForAuthenticatedUser(username);
    	Assert.assertEquals(listGistGetCallResponse.getStatusCode(), HttpStatus.SC_OK,"OK");
    	
    	// Fetch all gistId from list gist GET call's response
    	List<Object> allGistIDs = (List<Object>) jsonUtility.getJsonPathFieldValue(listGistGetCallResponse.asString(), "id");
    	
    	// Validate any gist with public attribute as true or false, all are available in the response
    	Assert.assertTrue(allGistIDs.contains(gistIdWithPublicFalse));
        Assert.assertTrue(allGistIDs.contains(gistIdWithPublicTrue));
    	
    }
    
    @Test
    public void testListGistWithUsernameForUnAuthenticatedUser() throws IOException{
    	
    	String postRequestPayloadWithPublicFalse;
    	String postRequestPayloadWithPublicTrue;
    	
    	// Fetching request payloads from testData folder
    	postRequestPayloadWithPublicFalse = jsonUtility.readFileAsString("resources/requestPayload3.json");
    	postRequestPayloadWithPublicTrue = jsonUtility.readFileAsString("resources/requestPayload4.json");
    	
    	// POST call to Create a Gist with 'public' as false
    	Response postCallResponse1 = gistAPIService.createGist(postRequestPayloadWithPublicFalse);
    	Assert.assertEquals(postCallResponse1.getStatusCode(), HttpStatus.SC_CREATED,"Created");
    	
    	// Fetch gistId from POST call's response
    	Object gistIdWithPublicFalse = jsonUtility.getJsonPathFieldValue(postCallResponse1.asString(), "id");
    	
    	// POST call to Create a Gist with 'public' as true
    	Response postCallResponse2 = gistAPIService.createGist(postRequestPayloadWithPublicTrue);
    	Assert.assertEquals(postCallResponse2.getStatusCode(), HttpStatus.SC_CREATED,"Created");
    	
    	// Fetch gistId from POST call's response
    	Object gistIdWithPublicTrue = jsonUtility.getJsonPathFieldValue(postCallResponse2.asString(), "id");
    	
    	// GET call to list gist endpoint
    	Response listGistGetCallResponse = gistAPIService.listGistWithUsernameForUnAuthenticatedUser(username);
    	Assert.assertEquals(listGistGetCallResponse.getStatusCode(), HttpStatus.SC_OK,"OK");
    	
    	// Fetch all gistId from list gist GET call's response
    	List<Object> allGistIDs = (List<Object>) jsonUtility.getJsonPathFieldValue(listGistGetCallResponse.asString(), "id");
    	
    	/*
		 * Validate gist with public attribute as true is only available & gist with
		 * public attribute as false is not available in the response
		 */
    	Assert.assertFalse(allGistIDs.contains(gistIdWithPublicFalse));
        Assert.assertTrue(allGistIDs.contains(gistIdWithPublicTrue));
    	
    }
}
