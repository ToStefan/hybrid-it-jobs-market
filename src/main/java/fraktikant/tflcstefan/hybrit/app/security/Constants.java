package fraktikant.tflcstefan.hybrit.app.security;

public class Constants {
    public final static String tokenHeader = "Authorization";
    public final static String jwtSecret = "secret";
    public final static Integer jwtExpirationInMs = 8640000;
    public final static String roleAdmin = "role_admin";
    public final static String roleUser = "role_user";
}
