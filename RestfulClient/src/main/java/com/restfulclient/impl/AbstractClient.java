package com.restfulclient.impl;

import com.restfulclient.interfaces.IRequest;
import com.restfulclient.interfaces.IResponse;
import com.restfulclient.interfaces.IResponseResult;
import java.io.IOException;
import com.restfulclient.interfaces.IClient;
import com.restfulclient.interfaces.IAbstractClient;
import com.restfulclient.interfaces.IRequestBody;

/**
 * Abstract representation of any restful call
 */
public abstract class AbstractClient implements IAbstractClient {

    private IRequest request = null;
    
    public AbstractClient(final String server, final String service,Method method) throws Exception {
       request = Request.build(method, RequestPath.build(server, service));     
    }

    @Override
    public abstract IAbstractClient addAutentication(String apiKey); 
    @Override
    public abstract IAbstractClient addAutentication(String user, String password); 

    @Override
    public IResponseResult execute() throws IOException, ApiException {
      IClient stream=null;
      IResponse response;
        try {   
              request.buildPathForRequest();
              switch(request.getBody().getBodyType()){
                case NONE:                           
                    break;
                case URL_XFORM_ENCODED:
                    String encoded =  request.buildXFormURLEncodedParameters();
                    request.getBody().setBodyContent(encoded);
                    request.getBody().setBody(encoded.getBytes());
                    break;                    
                case RAW:
                    break;
                case BINARY:
                     break;
                case FORM_DATA:
                    break;
                case MULTIPART_FORM:
                    break;
                default:  
            } 
        
            stream = Client.build(request);
            response = stream.execute();
            return response.getIResponseResult();
        } catch (IOException | ApiException ex) {
            throw ex;
        }finally{
            stream.clean();
            stream=null;
        }        
    }

   
    @Override
    public IAbstractClient addHeader(String name, Object value) {
        if (request != null) {
            request.addHeader(name, value);
        }
        return this;
    }
    @Override
    public IAbstractClient addFormMultipartParams(String name, Object value) {
        if (request != null) {
            request.addFormMultipartParams(name, value);
        }
        return this;
    }

    @Override
    public IAbstractClient addRequestBody(IRequestBody body) {
        if (request != null) {
            request.addBody(body);
        }
        return this;
    }

    @Override
    public IAbstractClient addNewRequestQueryParameter(String name, Object value)throws Exception {
       if(request != null) { 
          if(request.existsParameterType() == false){
              request.addRequestParameterInstance(RequestParameters.build(ParameterType.QUERY_PARAMS));
              request.addNewRequestParameter(name, value);
          }else{
            if(request.compareParameterType(ParameterType.QUERY_PARAMS)){
               request.addNewRequestParameter(name, value);
            } 
            else{
                throw new ApiException("Ilegal access exception you called before a parameter type of : " + request.getCurrentParameterType().name());
            }
          }                      
       }
       return this;
    } 
    
    @Override
    public IAbstractClient addNewRequestPathParameter(String name, Object value)throws Exception {
      if(request != null) { 
          if(request.existsParameterType() == false){
              request.addRequestParameterInstance(RequestParameters.build(ParameterType.PATH_VARIABLES));
              request.addNewRequestParameter(name, value);
          }else{
            if(request.compareParameterType(ParameterType.PATH_VARIABLES)){
               request.addNewRequestParameter(name, value);
            } 
            else{
                throw new ApiException("Ilegal access exception you called before a parameter type of : " + request.getCurrentParameterType().name());
            }
          }                      
       }
       return this;
    } 
    
     @Override
    public IAbstractClient addNewRequestXFormURLEncodeParameter(String name, Object value)throws Exception {
      if(request != null) { 
          if(request.existsParameterType() == false){
              request.addRequestParameterInstance(RequestParameters.build(ParameterType.FOR_URL_ENCODED));
              request.addNewRequestParameter(name, value);
          }else{
            if(request.compareParameterType(ParameterType.FOR_URL_ENCODED)){
               request.addNewRequestParameter(name, value);
            } 
            else{
                throw new ApiException("Ilegal access exception you called before a parameter type of : " + request.getCurrentParameterType().name());
            }
          }                      
       }
       return this;
    } 
    
     @Override
    public IAbstractClient addNewPathParameter(String name, Object value)throws Exception {
       if (request != null) {          
              request.addNewPathParameter(name, value);
       }
       return this;
    } 
          
}
