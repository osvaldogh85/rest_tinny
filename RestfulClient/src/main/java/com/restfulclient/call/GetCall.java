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
import java.util.Map;

public class GetCall extends Call {

    private final String serverURL;
    private final String requestMethodURL; 
    private final Map<String,String> queryParameters;
   
   private GetCall(String serverURL,String requestMethodPath,String apiToken,Map<String,String> queryParameters) {
    super(apiToken,null,null);
    this.serverURL=serverURL;
    this.requestMethodURL=requestMethodPath;  
    this.queryParameters=queryParameters;
  
   }

  public static ICall build(String serverURL,String requestMethodURL,String apiToken,Map<String,String> queryParameters)   {
    return new GetCall(serverURL,requestMethodURL,apiToken,queryParameters);
  }
  
   private GetCall(String serverURL,String requestMethodPath,Map<String,String> queryParameters,String user, String password) {
    super(null,user,password);
    this.serverURL=serverURL;
    this.requestMethodURL=requestMethodPath;  
    this.queryParameters=queryParameters;   
   }

  public static ICall build(String serverURL,String requestMethodURL,String apiToken,Map<String,String> queryParameters,String user, String password)   {
    return new GetCall(serverURL,requestMethodURL,queryParameters,user,password);
  }

  @Override
  public void prepareCall() {
     super.prepareCall();

    super.addURLInfo(serverURL, requestMethodURL, Method.POST, AbstractCall.TYPE_JSON_QUERY);
    for(String key : queryParameters.keySet())
      super.addQueryParameter(key, queryParameters.get(key));
  }

}