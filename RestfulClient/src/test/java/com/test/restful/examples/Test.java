/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.restful.examples;
import com.restfulclient.call.RestClientConstants;

import com.restfulclient.call.RestClient;
import com.restfulclient.impl.AbstractCall.RequestType;
import com.restfulclient.impl.Method;
import com.restfulclient.interfaces.IResponseResult;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author odge
 */
public class Test {
    public static void main(String args[]){
       String json="{\n" +
                "  \"name\": \"Jhon Doe\",\n" +
                "  \"location\": \"Canada\",\n"  +
                "  \"birthDate\": \"1975-02-07T06:00:00.000+00:00\" \n}";
        
    /*     String json="{   \n" +
"      \"id\":\"5\"\n" +
"}";*/
      Map<String,Object> e =  new HashMap<String,Object>();
      e.put("id",10001);
  /*  String json ="{\n" +
"  \"interviewee\": {\n" +
"    \"firstName\": \"Osvaldo\",\n" +
"    \"lastName\": \"Gonzalez\",\n" +
"    \"role\": \"Admin\",\n" +
"    \"language\": \"EN\",\n" +
"    \"interpreter\": true\n" +
"  },\n" +
"  \"claim\": {\n" +
"    \"claimNumber\": \"CN-12345\",\n" +
"    \"insured\": {\n" +
"      \"firstName\": \"Osvaldo\",\n" +
"      \"lastName\": \"Gonzalez\"\n" +
"    },\n" +
"    \"lossOccurredAt\": \"2021-05-14T12:00:00.000Z\"\n" +
"  }\n" +
"}";*/
      
       
     
        IResponseResult response = (IResponseResult) RestClient.build("http://localhost:8080/api/test", "getPersonQuery", Method.GET,RequestType.TYPE_JSON_QUERY,e,e, null).executeCall();
        
        
     //  var ls = response.getList();
     //    System.out.println(ls.toString());
        
        var content = response.getResponseContent();
        System.out.println(content);
        
        
         var mp = response.getMap();
         System.out.println(mp.toString());
       
    }
    
}
