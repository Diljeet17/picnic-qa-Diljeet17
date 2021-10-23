package com.utils;

import io.restassured.path.json.JsonPath;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonUtility {

	public Object getJsonPathFieldValue(String response, String jsonPath) {
		Object jsonPathValue = null;
		
		JsonPath responseJsonPath = new JsonPath(response);
		jsonPathValue = responseJsonPath.get(jsonPath);
		
		return jsonPathValue;
	}
	
	public String readFileAsString(String file) throws IOException {
        return new String(Files.readAllBytes(Paths.get(file)));
    }
}