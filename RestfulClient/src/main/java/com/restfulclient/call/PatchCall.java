/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.restfulclient.call;

import com.restfulclient.impl.AbstractCall;
import com.restfulclient.impl.Call;
import com.restfulclient.impl.Method;
import com.restfulclient.interfaces.ICall;

public class PatchCall extends Call {

    private final String serverURL;
    private final String requestMethodURL;
    private final String JSONBodyContent;


    private PatchCall(String serverURL, String requestMethodPath, String JSONBodyContent, String apiToken) {
        super(apiToken,null,null);
        this.serverURL = serverURL;
        this.requestMethodURL = requestMethodPath;
        this.JSONBodyContent = JSONBodyContent;
       
    }

    public static ICall build(String serverURL, String requestMethodPath, String JSONBodyContent, String apiToken) {
        return new PatchCall(serverURL, requestMethodPath, JSONBodyContent, apiToken);
    }

    private PatchCall(String serverURL, String requestMethodPath, String JSONBodyContent, String user, String password) {
        super(null,user,password);
        this.serverURL = serverURL;
        this.requestMethodURL = requestMethodPath;
        this.JSONBodyContent = JSONBodyContent;       
    }

    public static ICall build(String serverURL, String requestMethodPath, String JSONBodyContent, String user, String password) {
        return new PatchCall(serverURL, requestMethodPath, JSONBodyContent, user, password);
    }
  @Override
  public void prepareCall() {
    super.prepareCall();
    super.addURLInfo(serverURL, requestMethodURL, Method.PATCH, AbstractCall.TYPE_JSON_QUERY);
    super.addRequestBody(BodyContent.build(JSONBodyContent));
  }  
}