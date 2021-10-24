package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helpers.GistsAPIService;
import com.utils.ReportUtility;
import com.utils.ConfigManager;
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
public class ListGistTest extends ReportUtility {
	
    private GistsAPIService gistAPIService;
    JsonUtility jsonUtility = new JsonUtility();
    ObjectMapper mapper = new ObjectMapper();
    String username = ConfigManager.getInstance().getString("basic_oauth_username");
    int perPageQueryParam = Integer.parseInt(ConfigManager.getInstance().getString("per_page_query_param"));
    int pageQueryParam = Integer.parseInt(ConfigManager.getInstance().getString("page_query_param"));
    
    @BeforeClass
    public void init(){
    	gistAPIService =new GistsAPIService();
    }
    
	/*
	 * This test validates that for authenticated user gist with public as
	 * true/false both are available in list gist's response
	 */    
    @Test(priority=1)
    public void testListGistForAuthenticatedUser() throws IOException{
    	
    	String postRequestPayloadWithPublicFalse;
    	String postRequestPayloadWithPublicTrue;
    	
    	// Fetching request payloads from testData folder
    	postRequestPayloadWithPublicFalse = jsonUtility.readFileAsString("resources/requestPayload1.json");
    	postRequestPayloadWithPublicTrue = jsonUtility.readFileAsString("resources/requestPayload2.json");
    	
    	// POST call to Create a Gist with 'public' as false
    	Response postCallResponse1 = gistAPIService.createGist(postRequestPayloadWithPublicFalse);
    	Assert.assertEquals(postCallResponse1.getStatusCode(), HttpStatus.SC_CREATED);
    	
    	// Fetch gistId from POST call's response
    	Object gistIdWithPublicFalse = jsonUtility.getJsonPathFieldValue(postCallResponse1.asString(), "id");
    	
    	// POST call to Create a Gist with 'public' as true
    	Response postCallResponse2 = gistAPIService.createGist(postRequestPayloadWithPublicTrue);
    	Assert.assertEquals(postCallResponse2.getStatusCode(), HttpStatus.SC_CREATED);
    	
    	// Fetch gistId from POST call's response
    	Object gistIdWithPublicTrue = jsonUtility.getJsonPathFieldValue(postCallResponse2.asString(), "id");
    	
    	// GET call to list gist endpoint
    	Response listGistGetCallResponse = gistAPIService.listGistForAuthenticatedUser(perPageQueryParam, pageQueryParam);
    	Assert.assertEquals(listGistGetCallResponse.getStatusCode(), HttpStatus.SC_OK);
    	
    	// Fetch all gistId from list gist GET call's response
    	List<Object> allGistIDs = (List<Object>) jsonUtility.getJsonPathFieldValue(listGistGetCallResponse.asString(), "id");
    	
    	// Validate any gist with public attribute as true or false, all are available in the response
    	Assert.assertTrue(allGistIDs.contains(gistIdWithPublicFalse));
        Assert.assertTrue(allGistIDs.contains(gistIdWithPublicTrue));
    	
    }
    
    /*
	 * This test validates that for UnAuthenticated user only gist with public as
	 * true is available in list gist's response
	 */
    @Test(priority=2)
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
    	Assert.assertEquals(postCallResponse2.getStatusCode(), HttpStatus.SC_CREATED);
    	
    	// Fetch gistId from POST call's response
    	Object gistIdWithPublicTrue = jsonUtility.getJsonPathFieldValue(postCallResponse2.asString(), "id");
    	
    	
    	// GET call to list gist endpoint
    	Response listGistGetCallResponse = gistAPIService.listGistForUnAuthenticatedUser(perPageQueryParam, pageQueryParam);
    	Assert.assertEquals(listGistGetCallResponse.getStatusCode(), HttpStatus.SC_OK);
    	
    	// Fetch gistId from POST call's response
    	List<Object> allGistIDs = (List<Object>) jsonUtility.getJsonPathFieldValue(listGistGetCallResponse.asString(), "id");
    	
		/*
		 * Validate gist with public attribute as true is only available & gist with
		 * public attribute as false is not available in the response
		 */
    	Assert.assertFalse(allGistIDs.contains(gistIdWithPublicFalse));
        Assert.assertTrue(allGistIDs.contains(gistIdWithPublicTrue));
    }
    
    /*
	 * This test validates that for Authenticated user gist with public as
	 * true/false both are available in list gist for username endpoint response
	 */
    @Test(priority=3)
    public void testListGistWithUsernameForAuthenticatedUser() throws IOException{
    	
    	String postRequestPayloadWithPublicFalse;
    	String postRequestPayloadWithPublicTrue;
    	
    	// Fetching request payloads from testData folder
    	postRequestPayloadWithPublicFalse = jsonUtility.readFileAsString("resources/requestPayload3.json");
    	postRequestPayloadWithPublicTrue = jsonUtility.readFileAsString("resources/requestPayload4.json");
    	
    	// POST call to Create a Gist with 'public' as false
    	Response postCallResponse1 = gistAPIService.createGist(postRequestPayloadWithPublicFalse);
    	Assert.assertEquals(postCallResponse1.getStatusCode(), HttpStatus.SC_CREATED);
    	
    	// Fetch gistId from POST call's response
    	Object gistIdWithPublicFalse = jsonUtility.getJsonPathFieldValue(postCallResponse1.asString(), "id");
    	
    	// POST call to Create a Gist with 'public' as true
    	Response postCallResponse2 = gistAPIService.createGist(postRequestPayloadWithPublicTrue);
    	Assert.assertEquals(postCallResponse2.getStatusCode(), HttpStatus.SC_CREATED);
    	
    	// Fetch gistId from POST call's response
    	Object gistIdWithPublicTrue = jsonUtility.getJsonPathFieldValue(postCallResponse2.asString(), "id");
    	
    	// GET call to list gist for username endpoint
    	Response listGistGetCallResponse = gistAPIService.listGistWithUsernameForAuthenticatedUser(username, perPageQueryParam, pageQueryParam);
    	Assert.assertEquals(listGistGetCallResponse.getStatusCode(), HttpStatus.SC_OK);
    	
    	// Fetch all gistId from list gist GET call's response
    	List<Object> allGistIDs = (List<Object>) jsonUtility.getJsonPathFieldValue(listGistGetCallResponse.asString(), "id");
    	
    	// Validate any gist with public attribute as true or false, all are available in the response
    	Assert.assertTrue(allGistIDs.contains(gistIdWithPublicFalse));
        Assert.assertTrue(allGistIDs.contains(gistIdWithPublicTrue));
    	
    }
    
    /*
	 * This test validates that for UnAuthenticated user only gist with public as
	 * true is available in list gist for username endpoint's  response
	 */
    @Test(priority=4)
    public void testListGistWithUsernameForUnAuthenticatedUser() throws IOException{
    	
    	String postRequestPayloadWithPublicFalse;
    	String postRequestPayloadWithPublicTrue;
    	
    	// Fetching request payloads from testData folder
    	postRequestPayloadWithPublicFalse = jsonUtility.readFileAsString("resources/requestPayload3.json");
    	postRequestPayloadWithPublicTrue = jsonUtility.readFileAsString("resources/requestPayload4.json");
    	
    	// POST call to Create a Gist with 'public' as false
    	Response postCallResponse1 = gistAPIService.createGist(postRequestPayloadWithPublicFalse);
    	Assert.assertEquals(postCallResponse1.getStatusCode(), HttpStatus.SC_CREATED);
    	
    	// Fetch gistId from POST call's response
    	Object gistIdWithPublicFalse = jsonUtility.getJsonPathFieldValue(postCallResponse1.asString(), "id");
    	
    	// POST call to Create a Gist with 'public' as true
    	Response postCallResponse2 = gistAPIService.createGist(postRequestPayloadWithPublicTrue);
    	Assert.assertEquals(postCallResponse2.getStatusCode(), HttpStatus.SC_CREATED);
    	
    	// Fetch gistId from POST call's response
    	Object gistIdWithPublicTrue = jsonUtility.getJsonPathFieldValue(postCallResponse2.asString(), "id");
    	
    	// GET call to list gist for username endpoint
    	Response listGistGetCallResponse = gistAPIService.listGistWithUsernameForUnAuthenticatedUser(username, perPageQueryParam, pageQueryParam);
    	Assert.assertEquals(listGistGetCallResponse.getStatusCode(), HttpStatus.SC_OK);
    	
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
