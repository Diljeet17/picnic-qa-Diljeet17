package tests;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.helpers.GistsAPIService;
import com.model.Gists;
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

@Listeners(com.utils.ReportUtility.class)
public class GistAccessibilityTest extends ReportUtility {
	
    private GistsAPIService gistAPIService;
    JsonUtility jsonUtility = new JsonUtility();
    ObjectMapper mapper = new ObjectMapper();

    @BeforeClass
    public void init(){
    	gistAPIService =new GistsAPIService();
    }
    
	/*
	 * This test verifies when file content in the request is above 1 Megabyte then
	 * content in the response is truncated & truncated attribute in file object is
	 * true
	 */
    
    @Test(priority=1)
    public void testCreateGistWithLargeFileContent() throws IOException {
    	
    	String postRequestPayload;
    	Object responseFileObject;
    	Object gistID;
    	String username = ConfigManager.getInstance().getString("basic_oauth_username");
    	
    	// Fetching request payload from testData folder
    	postRequestPayload = jsonUtility.readFileAsString("resources/requestPayloadWithLargeContent.json");
    	
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
    	Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_CREATED,"Post Call Failed");
    	
    	// Mapping response to POJO
    	Gists postGistResponse = mapper.readValue(response.getBody().asString(), Gists.class);
    	
    	// Fetching gistId & fileObject from POST call's response
    	responseFileObject = jsonUtility.getJsonPathFieldValue(response.asString(), "files");
    	gistID = jsonUtility.getJsonPathFieldValue(response.asString(), "id");
    	
    	// Validate truncated is true as file content is beyond 1 megabyte
    	Assert.assertTrue(responseFileObject.toString().contains("truncated=true"));
    }
}
