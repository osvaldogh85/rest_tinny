package com.restfulclient.impl;
import com.restfulclient.interfaces.IAuthorization;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class HttpAuthorizationImpl implements IAuthorization {

    private String encodedToken;
    private HttpAuthorizationImpl(String user, String password) {
      this.createBasicAuthenticationToken(user, password);
    }
    
    private HttpAuthorizationImpl(String token) {
      createBearerToken(token);
    }
    public static IAuthorization build(String user, String password) {
        return new HttpAuthorizationImpl(user,password);
    }

    public static IAuthorization build(String token) {
        return new HttpAuthorizationImpl(token);
    }

    private void createBasicAuthenticationToken(String user, String password) {
        String auth = user + ":" + password;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
        encodedToken = "Basic " + new String(encodedAuth);
    }

    private void createBearerToken(String token) {
        encodedToken = "Bearer " + token;
    }

    @Override
    public String getAuthorizationToken() {
        return encodedToken;
    }
}
