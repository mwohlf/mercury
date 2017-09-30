package net.wohlfart.mercury;

public class SecurityConstants {
    public static final byte[] SECRET = "SecretKeyToGenJWTs".getBytes();
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/sign-up";
    public static final String SIGN_IN_URL = "/users/sign-in";
    public static final String TOKEN_REFRESH_URL = "/users/refresh";
    public static final String API_URL = "/api";
    public static final String H2_CONSOLE_URL = "/h2-console";


    public static final String FIELDNAME_USERNAME = "username";
    public static final String FIELDNAME_PASSWORD = "password";
}