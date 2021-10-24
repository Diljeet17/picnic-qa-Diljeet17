package com.constants;

/*This class contains core & search rate limis 
as per rules*/

public class RateLimits {
    public static final Integer authenticatedUserCoreRateLimit = 5000;
    public static final Integer authenticatedUserSearchRateLimit = 30;
    public static final Integer unAuthenticatedUserCoreRateLimit = 60;
    public static final Integer unAuthenticatedUserSearchRateLimit = 10;
}
