package tests;

import com.constants.RateLimits;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.helpers.RateLimitAPIService;
import com.model.RateLimitResponse;
import com.utils.Reports;
import com.utils.JsonUtility;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(com.utils.Reports.class)
public class RateLimitTest extends Reports {
	
    private RateLimitAPIService rateLimitAPIService;
    JsonUtility jsonUtility = new JsonUtility();
    ObjectMapper mapper = new ObjectMapper();

    @BeforeClass
    public void init(){
    	rateLimitAPIService =new RateLimitAPIService();
    }

    @Test(priority=1)
    public void testRateLimitForAuthenticatedUser() throws JsonMappingException, JsonProcessingException{
    	
    	// GET call to Rate Limit API
    	Response response = rateLimitAPIService.getRateLimitForAuthenticatedUser();
    	Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK,"OK");
    	
    	// Mapping GET call's response to POJO
    	RateLimitResponse rateLimitResponse = mapper.readValue(response.getBody().asString(), RateLimitResponse.class);
    	
        // Fetching rate limits for core & search objects from response
    	Integer responseCoreRateLimit = rateLimitResponse.getResources().getCore().getLimit();
    	Integer responseSearchRateLimit = rateLimitResponse.getResources().getSearch().getLimit();
    	
    	// Validate rate limit as per rules for authenticated user
        Assert.assertEquals(responseCoreRateLimit, RateLimits.authenticatedUserCoreRateLimit);
        Assert.assertEquals(responseSearchRateLimit, RateLimits.authenticatedUserSearchRateLimit);
        
    }

    @Test(priority=2)
    public void testRateLimitForUnAuthenticatedUser() throws JsonMappingException, JsonProcessingException{
    	
    	// GET call to Rate Limit API
    	Response response = rateLimitAPIService.getRateLimitForUnAuthenticatedUser();
    	Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK,"OK");
        
    	// Mapping GET call's response to POJO
    	RateLimitResponse rateLimitResponse = mapper.readValue(response.getBody().asString(), RateLimitResponse.class);
    	
    	// Fetching rate limits for core & search objects from response
    	Integer responseCoreRateLimit = rateLimitResponse.getResources().getCore().getLimit();
    	Integer responseSearchRateLimit = rateLimitResponse.getResources().getSearch().getLimit();
    	
    	// Validate rate limit as per rules for unauthenticated user
        Assert.assertEquals(responseCoreRateLimit, RateLimits.unAuthenticatedUserCoreRateLimit);
        Assert.assertEquals(responseSearchRateLimit, RateLimits.unAuthenticatedUserSearchRateLimit);
    }
    
}
