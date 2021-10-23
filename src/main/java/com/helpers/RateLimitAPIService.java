package com.helpers;

import com.constants.EndPoints;
import com.fasterxml.jackson.core.type.TypeReference;
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

public class RateLimitAPIService {
    // functions related to specific api
    // all crud operations reusable methods
    private static final String BASE_URL = ConfigManager.getInstance().getString("base_url");
    private static final String token = ConfigManager.getInstance().getString("token");
    private static final String oAuth_username = ConfigManager.getInstance().getString("basic_oauth_username");
    private static final String oAuth_password = ConfigManager.getInstance().getString("basic_oauth_password");

    public RateLimitAPIService(){
        RestAssured.baseURI=BASE_URL;
    }

    public Response getRateLimitForAuthenticatedUser(){
        
        Response response = given()
                .auth()
                .preemptive()
                .basic(oAuth_username, oAuth_password)
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .contentType(ContentType.JSON)
                .get(EndPoints.rate_limit)
                .then().extract().response();

        return response;
    }
    
    public Response getRateLimitForUnAuthenticatedUser(){
        
        Response response = given()
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .contentType(ContentType.JSON)
                .get(EndPoints.rate_limit)
                .then().extract().response();

        return response;
    }
    
}
