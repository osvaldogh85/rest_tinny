/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.restfulclient.impl;

import com.restfulclient.call.Constants;
import com.restfulclient.interfaces.IAuthorization;

/**
 *
 * @author odge
 */
public class Call extends AbstractCall{
  
    private final String apiToken, user, pass;

    public Call(String apiToken, String user, String pass) {
        this.apiToken = apiToken;
        this.user = user;
        this.pass = pass;
    }
        
    @Override
    public void prepareCall() {
        super.addHeader(Constants.API_CONTENT, Constants.API_CONTENT_TYPE);
        super.addHeader(Constants.API_ACCEPT, Constants.API_ACCEPT_CONTENT);
        super.addHeader(Constants.API_USER_AGENT_TITLE, Constants.API_USER_AGENT);
    }

    @Override
    public IAuthorization addAutentication() {
        if (apiToken != null && !apiToken.equals("")) {
            return HttpAuthorizationImpl.build(apiToken);
        }
        return null;
    }

    @Override
    public IAuthorization addUserAndPassAutentication() {
        if (user != null && !user.equals("") && pass != null && !pass.equals("")) {
            return HttpAuthorizationImpl.build(user, pass);
        }
        return null;
    }
    
}
