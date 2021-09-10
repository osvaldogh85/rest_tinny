package com.restfulclient.impl;
import com.restfulclient.interfaces.IAuthorization;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Authorization implements IAuthorization {

    private StringBuilder encodedToken;
    private Authorization(String user, String password) {
      this.createBasicAuthenticationToken(user, password);
    }
    
    private Authorization(String token) {
      createBearerToken(token);
    }
    public static IAuthorization build(String user, String password) {
        return new Authorization(user,password);
    }

    public static IAuthorization build(String token) {
        return new Authorization(token);
    }

    private void createBasicAuthenticationToken(String user, String password) {
     String auth = user + ":" + password;
     byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
     encodedToken= new StringBuilder();
     encodedToken.append("Basic ").append(new String(encodedAuth));
    }

    private void createBearerToken(String token) {
      encodedToken= new StringBuilder();
      encodedToken.append("Bearer ").append(token);
    }
    
    @Override
    public String getAuthorizationToken() {
        return encodedToken.toString();
    }
}
