package com.constants;

/*This class contains all the endpoints related to 
Gists & Rate Limit APIs*/

public class EndPoints {
    public static final String gist_get = "/gists/{gist_id}";
    public static final String gist_create = "/gists";
    public static final String gist_update = "/gists/{gist_id}";
    public static final String gist_delete = "/gists/{gist_id}";
    public static final String rate_limit = "/rate_limit";
    public static final String list_gist = "/gists";
    public static final String list_gist_for_username = "/users/{username}/gists";
}
