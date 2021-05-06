package com.restfulclient.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.restfulclient.interfaces.IClient;
import com.restfulclient.interfaces.IResponse;

public class ResponseImpl implements IResponse {

    private String content;

    private ResponseImpl() {
    }

    public static IResponse build() {
        return new ResponseImpl();
    }

    @Override
    public  Map<String, Object> getMap() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(content, HashMap.class);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(ResponseImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void process(IClient client) {     
     StringBuilder sb = new StringBuilder();
     BufferedReader br= null;
     try {
         br = client.getResponseStream();
         var output = br.readLine();
         while (output != null) {
           sb.append(output);
           output = br.readLine();
        }
     }catch(IOException e){
         Logger.getLogger(ResponseImpl.class.getName()).log(Level.SEVERE, null, e);
     }catch (Exception ex) {
        Logger.getLogger(ResponseImpl.class.getName()).log(Level.SEVERE, null, ex);
     }
     finally {
         try {
             if(br != null)
                 br.close();
             
             client.close();
             content = sb.toString();
         } catch (IOException ex) {
             Logger.getLogger(ResponseImpl.class.getName()).log(Level.SEVERE, null, ex);
         }
     }      
    }
}