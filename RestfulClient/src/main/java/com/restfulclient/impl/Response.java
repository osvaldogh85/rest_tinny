package com.restfulclient.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.restfulclient.interfaces.IResponse;
import com.restfulclient.interfaces.IResponseResult;
import java.util.List;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import com.restfulclient.interfaces.IClient;

public class Response implements IResponse {

    private StringBuilder content;
    private int responseCode = 0;

    private Response() {

    }

    public static IResponse build() {
        return new Response();
    }

    @Override
    public void process(IClient client) throws IOException, ApiException {    
        responseCode = client.getResponseCode();         
        try {              
            content = readResponse(client.isOKHTTP() ? client.getResponseStream() : client.getErrorStream());               
        } catch (IOException | ApiException e) {
            throw new ApiException(client.getResponseCode(), client.getResponseMessage(),client.getResponseHeader(),content.toString());
        } finally {
            try {                   
                if( responseCode >= HttpURLConnection.HTTP_BAD_REQUEST){
                      throw new ApiException(client.getResponseCode(), client.getResponseMessage(),client.getResponseHeader(),content.toString());   
                }                                                 
            } catch (IOException | ApiException e) {
                throw new ApiException(client.getResponseCode(), client.getResponseMessage(),client.getResponseHeader(),content.toString());
            }
        }
    }

    private StringBuilder readResponse(InputStreamReader response) throws IOException,ApiException {
        StringBuilder sb = new StringBuilder();      
        BufferedReader br = new BufferedReader(response);
        try {               
           var output = br.readLine();
            while (output != null) {
              sb.append(output);
              output = br.readLine();
            }
        }catch(IOException e){
             throw e;
        }  
        finally{
            br.close();
            response.close();
        }     
        return sb;
    }

    @Override
    public IResponseResult getIResponseResult() {
        return new IResponseResult() {
            @Override
            public Map<String, Object> getMap() throws ApiException {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    return objectMapper.readValue(content.toString(), HashMap.class);
                } catch (JsonProcessingException ex) {
                    throw new ApiException(getResponseCode(), ex.getMessage());
                }
            }

            @Override
            public List<Map<String, Object>> getList() throws ApiException {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    return objectMapper.readValue(content.toString(), new TypeReference<List<Map<String, Object>>>() {
                    });
                } catch (JsonProcessingException ex) {
                    throw new ApiException(getResponseCode(), ex.getMessage());
                }
            }

            @Override
            public StringBuilder getResponseContent() {
                return content;
            }

            @Override
            public int getResponseCode() {
                return responseCode;
            }

            @Override
            public boolean isSuccessful() {
                return (getResponseCode() >= HttpURLConnection.HTTP_OK) && (getResponseCode() <= HttpURLConnection.HTTP_PARTIAL);
            }
        };
    }

}
