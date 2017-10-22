package net.wohlfart.mercury;

public class SecurityConstants {

    public static final String ROOT = "/";
    public static final String CATCH_ALL = "/**";
    public static final String API = "/api";

    // jwt token constants
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_HEADER = "Authorization";

    // auth endpoints
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