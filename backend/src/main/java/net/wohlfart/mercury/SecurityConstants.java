package net.wohlfart.mercury;

public class SecurityConstants {

    public static final String CATCH_ALL = "/**";

    // resourecs
    public static final String ROOT = "/";
    public static final String API = "/api";
    public static final String ASSETS = "/assets/**";
    public static final String HOME = "/index.html";
    public static final String JS_RESOURCES = "/*.js";  // TODO: try to move the js resources into a subdir
    public static final String FONTAWESOME = "/fontawesome-*";


    // jwt token constants
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_HEADER = "Authorization";

    // auth endpoints
    public static final String OAUTH_ENDPOINT = "/oauth"; // not part of the api
    public static final String AUTHENTICATE_ENDPOINT = API + "/authenticate";
    public static final String SIGNUP_ENDPOINT = API + "/signup";
    public static final String REFRESH_ENDPOINT = API + "/refresh";

    // admin endpoints
    public static final String ACCOUNTS_ENDPOINT = API + "/accounts";
    public static final String USERS_ENDPOINT = API + "/users";


    // admin endpoints
    public static final String H2_CONSOLE_URL = "/h2-console";


    public static final String FIELDNAME_USERNAME = "username";
    public static final String FIELDNAME_PASSWORD = "password";

}