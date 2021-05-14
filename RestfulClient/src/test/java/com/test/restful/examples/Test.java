/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.restful.examples;


import com.restfulclient.call.DeleteCall;
import com.restfulclient.call.GetCall;
import com.restfulclient.call.PatchCall;
import com.restfulclient.call.PostCall;
import com.restfulclient.interfaces.ICall;
import com.restfulclient.interfaces.IResponseResult;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author odge
 */
public class Test {
    public static void main(String args[]){
        /* String json="{\n" +
                "  \"name\": \"Jhon Doiron\",\n" +
                "  \"location\": \"Canada\",\n"  +
                "  \"birthDate\": \"1975-02-07T06:00:00.000+00:00\" \n}";*/
        
    /*     String json="{   \n" +
"      \"id\":\"5\"\n" +
"}";*/
      Map<String,Object> e =  new HashMap<String,Object>();
      e.put("id", 10001);
    String json ="{\n" +
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
"}";
         //.build("http://localhost:8080/api/test", , DeleteCall.TYPE_JSON_BODY , json);
        var call = PostCall.build("https://api.xyz.n2uitive.com/","v1/interviews", json, "0030bfa1a2d50cd36f4daafa99521eefc124bf109bc85c2c90da7ff172747cff63");
        //DeleteCall.build("http://localhost:8080/api/test/", "deletePerson", null, DeleteCall.TYPE_JSON_BODY, json);
        IResponseResult response = (IResponseResult) call.executeCall();
        var result = response.getMap();
        result.entrySet().forEach(param -> System.out.println(param.getKey() +" ---> "+param.getValue().toString()));    
        
        var content = response.getJSON();
        System.out.println(content);
       
    }
    
}
