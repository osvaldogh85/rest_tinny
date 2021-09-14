/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.restful.examples;

import com.restfulclient.call.RestClient;
import com.restfulclient.call.RestClientConstants;
import com.restfulclient.impl.ApiException;
import com.restfulclient.impl.BodyType;
import com.restfulclient.impl.Method;
import com.restfulclient.impl.ParameterType;
import com.restfulclient.impl.RequestBody;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author odge
 */
public class Test {

    public static void main(String args[]) throws Exception {

        try {
            String apiKey = "key";
            
            String body = "{\n"
                    + "  \"name\": \"Jhon Doe\",\n"
                    + "  \"location\": \"Canada\",\n"
                    + "  \"birthDate\": \"1975-02-07T06:00:00.000+00:00\" \n}";                                 
            
            String service =  "/api/{interview_id}/call/actions/{call_id}/execute";
            var response1 = RestClient.build("http://localhost:8088", service,  Method.POST)
                    .addRequestBody(RequestBody.build(BodyType.RAW, body))                    
                    .addHeader(RestClientConstants.API_CONTENT_TYPE, RestClientConstants.JSON_CONTENT_TYPE)
                    .addHeader(RestClientConstants.API_ACCEPT, RestClientConstants.API_ACCEPT_CONTENT)
                    .addHeader(RestClientConstants.API_USER_AGENT_TITLE, RestClientConstants.API_USER_AGENT)
                    .addHeader(RestClientConstants.API_CONNECTION, RestClientConstants.API_KEEP_ALIVE)      
                    .addNewPathParameter("interview_id","123")
                    .addNewPathParameter("call_id","HJG67KLLSMS") 
                    .addNewRequestQueryParameter("idphone", "102334323")
                    .addNewRequestQueryParameter("idroom", "abs123")
                    .addNewRequestPathParameter("idroom", "abs123")
                    .execute();
                  
            response1 = RestClient.build("http://localhost:8088", service,  Method.GET)
                    .addRequestBody(RequestBody.buildDefault())                    
                    .addHeader(RestClientConstants.API_CONTENT_TYPE, RestClientConstants.JSON_CONTENT_TYPE)
                    .addHeader(RestClientConstants.API_ACCEPT, RestClientConstants.API_ACCEPT_CONTENT)
                    .addHeader(RestClientConstants.API_USER_AGENT_TITLE, RestClientConstants.API_USER_AGENT)
                    .addHeader(RestClientConstants.API_CONNECTION, RestClientConstants.API_KEEP_ALIVE)      
                    .addNewPathParameter("interview_id","123")
                    .addNewPathParameter("call_id","HJG67KLLSMS")                                       
                    .addNewRequestQueryParameter("idphone", "102334323")
                    .addNewRequestQueryParameter("idroom", "abs123")
                    .execute();
            
             response1 = RestClient.build("http://localhost:8088", service,  Method.POST)
                    .addRequestBody(RequestBody.build(BodyType.RAW))                    
                    .addHeader(RestClientConstants.API_CONTENT_TYPE, RestClientConstants.JSON_CONTENT_TYPE)
                    .addHeader(RestClientConstants.API_ACCEPT, RestClientConstants.API_ACCEPT_CONTENT)
                    .addHeader(RestClientConstants.API_USER_AGENT_TITLE, RestClientConstants.API_USER_AGENT)
                    .addHeader(RestClientConstants.API_CONNECTION, RestClientConstants.API_KEEP_ALIVE)      
                    .addNewPathParameter("interview_id","123")
                    .addNewPathParameter("call_id","HJG67KLLSMS")                     
                    .addNewRequestPathParameter("idphone", "102334323")
                    .addNewRequestPathParameter("idroom", "abs123")
                    .execute();
            
            var response = RestClient.build("http://localhost:8088/api/test", "savePerson",  Method.POST)
                    .addRequestBody(RequestBody.build(BodyType.RAW, body))
                    .addHeader(RestClientConstants.API_CONTENT_TYPE, RestClientConstants.JSON_CONTENT_TYPE)
                    .addHeader(RestClientConstants.API_ACCEPT, RestClientConstants.API_ACCEPT_CONTENT)
                    .addHeader(RestClientConstants.API_USER_AGENT_TITLE, RestClientConstants.API_USER_AGENT)
                    .addHeader(RestClientConstants.API_CONNECTION, RestClientConstants.API_KEEP_ALIVE)
                    .execute();
            
            var mp = response.getMap();
            System.out.println(mp.toString());
            var content = response.getResponseContent();
            System.out.println(content);
            
              
            response = RestClient.build("http://localhost:8088/api/test", "getPerson",  Method.GET)
                    .addHeader(RestClientConstants.API_CONTENT_TYPE, RestClientConstants.JSON_CONTENT_TYPE)
                    .addHeader(RestClientConstants.API_ACCEPT, RestClientConstants.API_ACCEPT_CONTENT)
                    .addHeader(RestClientConstants.API_USER_AGENT_TITLE, RestClientConstants.API_USER_AGENT)
                    .addHeader(RestClientConstants.API_CONNECTION, RestClientConstants.API_KEEP_ALIVE)                  
                    .addNewRequestQueryParameter("id", 10001)
                    .addAutentication(apiKey)
                    .execute();
            
            content = response.getResponseContent();
            System.out.println(content);
            
            mp = response.getMap();
            System.out.println(mp.toString());
            
          response = RestClient.build("http://localhost:8088/api/test", "getPerson", Method.GET)
                    .addHeader(RestClientConstants.API_CONTENT_TYPE, RestClientConstants.JSON_CONTENT_TYPE)
                    .addHeader(RestClientConstants.API_ACCEPT, RestClientConstants.API_ACCEPT_CONTENT)
                    .addHeader(RestClientConstants.API_USER_AGENT_TITLE, RestClientConstants.API_USER_AGENT)
                    .addHeader(RestClientConstants.API_CONNECTION, RestClientConstants.API_KEEP_ALIVE)                   
                    .addNewRequestPathParameter("id", 10001)
                    .addAutentication(apiKey)
                    .execute();
            
            content = response.getResponseContent();
            System.out.println(content);
            
            mp = response.getMap();
            System.out.println(mp.toString());
          
        } catch (IOException | ApiException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
