package com.utils;

import io.restassured.path.json.JsonPath;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;

public class DateTimeUtility {

	// Function to return current UTC timestamp
	public String getCurrentUTCTime() {
		Instant instant = Instant.now();
		return instant.toString();
	}
	
	
}