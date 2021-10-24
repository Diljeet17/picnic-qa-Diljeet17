package com.helpers;

import com.constants.EndPoints;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.RateLimitResponse;
import com.utils.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.Assert;

import static io.restassured.RestAssured.given;

import java.lang.reflect.Type;
import java.util.List;


/*This class has all the reusable methods
  of POST, GET, DELETE operations for Gists API*/

public class GistsAPIService {
    
    private static final String BASE_URL = ConfigManager.getInstance().getString("base_url");
    private static final String header_value = ConfigManager.getInstance().getString("accept_header_value");
    private static final String oAuth_username = ConfigManager.getInstance().getString("basic_oauth_username");
    private static final String oAuth_password = ConfigManager.getInstance().getString("basic_oauth_password");

    public GistsAPIService(){
        RestAssured.baseURI=BASE_URL;
    }

    public Response createGist(String payload){
        
        Response response = given()
                .auth()
                .preemptive()
                .basic(oAuth_username, oAuth_password)
                .header("Accept", header_value)
                .contentType(ContentType.JSON)
                .body(payload)
                .post(EndPoints.gist_create)
                .then().extract().response();

        return response;
    }
    
    public Response getGist(String gist_id){
        
        Response response = given()
                .auth()
                .preemptive()
                .basic(oAuth_username, oAuth_password)
                .header("Accept", header_value)
                .contentType(ContentType.JSON)
                .pathParam("gist_id", gist_id)
                .get(EndPoints.gist_get)
                .then().extract().response();

        return response;
    }
    
   public Response deleteGist(String gist_id){
        
	   Response response = given()
               .auth()
               .preemptive()
               .basic(oAuth_username, oAuth_password)
               .header("Accept", header_value)
               .contentType(ContentType.JSON)
               .pathParam("gist_id", gist_id)
               .delete(EndPoints.gist_delete)
               .then().extract().response();

        return response;
    }
   
   public Response listGistForAuthenticatedUser(int perPageQueryParam, int pageQueryParam){
       
	   Response response = given()
               .auth()
               .preemptive()
               .basic(oAuth_username, oAuth_password)
               .header("Accept", header_value)
               .contentType(ContentType.JSON)
               .queryParam("per-page", perPageQueryParam)
               .queryParam("page", pageQueryParam)
               .get(EndPoints.list_gist)
               .then().extract().response();
	   
        return response;
    }
   
   public Response listGistForUnAuthenticatedUser(int perPageQueryParam, int pageQueryParam){
       
	   Response response = given()
               .header("Accept", header_value)
               .contentType(ContentType.JSON)
               .queryParam("per-page", perPageQueryParam)
               .queryParam("page", pageQueryParam)
               .get(EndPoints.list_gist)
               .then().extract().response();

        return response;
    }
   
   public Response listGistWithUsernameForAuthenticatedUser(String username, int perPageQueryParam, int pageQueryParam){
       
	   Response response = given()
               .auth()
               .preemptive()
               .basic(oAuth_username, oAuth_password)
               .header("Accept", header_value)
               .contentType(ContentType.JSON)
               .pathParam("username", username)
               .queryParam("per-page", perPageQueryParam)
               .queryParam("page", pageQueryParam)
               .get(EndPoints.list_gist_for_username)
               .then().extract().response();
	   
        return response;
    }
   
  public Response listGistWithUsernameForUnAuthenticatedUser(String username, int perPageQueryParam, int pageQueryParam){
       
	   Response response = given()
               .header("Accept", header_value)
               .contentType(ContentType.JSON)
               .pathParam("username", username)
               .queryParam("per-page", perPageQueryParam)
               .queryParam("page", pageQueryParam)
               .get(EndPoints.list_gist_for_username)
               .then().extract().response();
	   
        return response;
    }
   
   public Response createGistWithoutAuthentication(String payload){
       
       Response response = given()
               .header("Accept", header_value)
               .contentType(ContentType.JSON)
               .body(payload)
               .post(EndPoints.gist_create)
               .then().extract().response();

       return response;
   }
}
