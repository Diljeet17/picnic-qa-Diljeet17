package tests;


import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.helpers.GistsAPIService;
import com.model.Gists;
import com.utils.ReportUtility;
import com.utils.ConfigManager;
import com.utils.DateTimeUtility;
import com.utils.JsonUtility;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.io.IOException;

@Listeners(com.utils.ReportUtility.class)
public class CreateGistTest extends ReportUtility {
	
    private GistsAPIService gistAPIService;
    JsonUtility jsonUtility = new JsonUtility();
    ObjectMapper mapper = new ObjectMapper();
    DateTimeUtility timeUtility = new DateTimeUtility();
    
    @BeforeClass
    public void init(){
    	gistAPIService =new GistsAPIService();
    }
    
	/*
	 * This test validates gist creation by authenticated user & verifies various
	 * data points in the response
	 */
    @Test(priority=1)
    public void testCreateGist() throws IOException {
    	
    	String postRequestPayload;
    	Object responseFileObject;
    	Object gistID;
    	String username = ConfigManager.getInstance().getString("basic_oauth_username");
    	
    	// Fetching request payload from testData folder
    	postRequestPayload = jsonUtility.readFileAsString("resources/requestPayload1.json");
    	
    	// Fetching filename, content, description, public details from request payload
    	Object requestFilesObject = jsonUtility.getJsonPathFieldValue(postRequestPayload, "files");
    	String requestFilesObjectString = requestFilesObject.toString();
        String requestFilesObjectStringArray[] = requestFilesObjectString.replaceAll("\\{", "").replaceAll("\\}", "").split("=content=");
        String requestFileName = requestFilesObjectStringArray[0];
        String requestContent = requestFilesObjectStringArray[1];
        Object requestDescription = jsonUtility.getJsonPathFieldValue(postRequestPayload, "description");
    	Object requestPublic = jsonUtility.getJsonPathFieldValue(postRequestPayload, "public");
    	
    	// POST call to Create a Gist
    	Response response = gistAPIService.createGist(postRequestPayload);
    	AssertJUnit.assertEquals(response.getStatusCode(), HttpStatus.SC_CREATED);
    	
    	// fetching date from timestamp of the POST operation
    	String currentDate = timeUtility.getCurrentUTCTime().split(":")[0]; 
    	
    	
    	// Mapping response to POJO
    	Gists postGistResponse = mapper.readValue(response.getBody().asString(), Gists.class);
    	
    	// Fetching gistId & fileObject from POST call's response
    	responseFileObject = jsonUtility.getJsonPathFieldValue(response.asString(), "files");
    	gistID = jsonUtility.getJsonPathFieldValue(response.asString(), "id");
    	
    	// Compare request details against response
    	AssertJUnit.assertEquals(postGistResponse.getDescription(), requestDescription);
    	AssertJUnit.assertEquals(postGistResponse.getPublic().toString(), requestPublic.toString());
        AssertJUnit.assertTrue(responseFileObject.toString().contains(requestFileName));
        AssertJUnit.assertTrue(responseFileObject.toString().contains(requestContent));
        
        // Validate Gist Details in response
        AssertJUnit.assertTrue(postGistResponse.getForksUrl().contains(gistID.toString()));
        AssertJUnit.assertTrue(postGistResponse.getGitPullUrl().contains(gistID.toString()));
        AssertJUnit.assertTrue(postGistResponse.getGitPushUrl().contains(gistID.toString()));
        AssertJUnit.assertTrue(postGistResponse.getCommitsUrl().contains(gistID.toString()));
        AssertJUnit.assertTrue(postGistResponse.getHtmlUrl().contains(gistID.toString()));
        AssertJUnit.assertTrue(postGistResponse.getCreatedAt().contains(currentDate));
        
        // Validate 'owner' node Details in response
        AssertJUnit.assertEquals(postGistResponse.getOwner().getLogin(),username);
        AssertJUnit.assertTrue(postGistResponse.getOwner().getUrl().contains(username));
        AssertJUnit.assertTrue(postGistResponse.getOwner().getFollowersUrl().contains(username));
        AssertJUnit.assertTrue(postGistResponse.getOwner().getFollowingUrl().contains(username));
        AssertJUnit.assertTrue(postGistResponse.getOwner().getGistsUrl().contains(username));
        AssertJUnit.assertTrue(postGistResponse.getOwner().getStarredUrl().contains(username));
        AssertJUnit.assertTrue(postGistResponse.getOwner().getSubscriptionsUrl().contains(username));
        AssertJUnit.assertTrue(postGistResponse.getOwner().getOrganizationsUrl().contains(username));
        AssertJUnit.assertTrue(postGistResponse.getOwner().getReposUrl().contains(username));
        AssertJUnit.assertTrue(postGistResponse.getOwner().getEventsUrl().contains(username));
        AssertJUnit.assertTrue(postGistResponse.getOwner().getReceivedEventsUrl().contains(username));
        
        // Validate 'history' node Details in response
        for(int i=0; i<postGistResponse.getHistory().size();i++) {
        	if(postGistResponse.getHistory().get(i).getUser().getLogin().equals(username)) {
        		AssertJUnit.assertTrue(postGistResponse.getHistory().get(i).getUser().getUrl().contains(username));
                AssertJUnit.assertTrue(postGistResponse.getHistory().get(i).getUser().getFollowersUrl().contains(username));
                AssertJUnit.assertTrue(postGistResponse.getHistory().get(i).getUser().getFollowingUrl().contains(username));
                AssertJUnit.assertTrue(postGistResponse.getHistory().get(i).getUser().getGistsUrl().contains(username));
                AssertJUnit.assertTrue(postGistResponse.getHistory().get(i).getUser().getStarredUrl().contains(username));
                AssertJUnit.assertTrue(postGistResponse.getHistory().get(i).getUser().getSubscriptionsUrl().contains(username));
                AssertJUnit.assertTrue(postGistResponse.getHistory().get(i).getUser().getOrganizationsUrl().contains(username));
                AssertJUnit.assertTrue(postGistResponse.getHistory().get(i).getUser().getReposUrl().contains(username));
                AssertJUnit.assertTrue(postGistResponse.getHistory().get(i).getUser().getEventsUrl().contains(username));
                AssertJUnit.assertTrue(postGistResponse.getHistory().get(i).getUser().getReceivedEventsUrl().contains(username));
        	}
        }
    }

	/*
	 * This test verifies UnAuthenticated user should not be allowed to create a
	 * gist
	 */
    @Test(priority=2)
    public void testCreateGistWithoutAuthentication() throws IOException{
    	
    	String postRequestPayload;
    	
    	// Fetching request payload from testData folder
    	postRequestPayload = jsonUtility.readFileAsString("resources/requestPayload2.json");
    	
    	// POST call to Create a Gist without authentication
    	Response postCallResponse = gistAPIService.createGistWithoutAuthentication(postRequestPayload);
    	AssertJUnit.assertEquals(postCallResponse.getStatusCode(), HttpStatus.SC_UNAUTHORIZED);
    
    }
}
