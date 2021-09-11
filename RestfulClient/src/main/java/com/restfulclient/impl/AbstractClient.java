package com.restfulclient.impl;

import com.restfulclient.interfaces.IRequest;
import com.restfulclient.interfaces.IResponse;
import com.restfulclient.interfaces.IResponseResult;
import java.io.IOException;
import com.restfulclient.interfaces.IClient;
import com.restfulclient.interfaces.IAbstractClient;
import com.restfulclient.interfaces.IRequestBody;
import com.restfulclient.interfaces.IRequestPath;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Abstract representation of any restful call
 */
public abstract class AbstractClient implements IAbstractClient {

    private IRequest request = null;
    
    public AbstractClient(final String server, final String service,Method method) throws Exception {
       request = Request.build(method, RequestPath.build(server, service));
       if(method == Method.GET || method == Method.HEAD)
         request.addBody(RequestBody.build(BodyType.NONE));
    }

    @Override
    public abstract IAbstractClient addAutentication(String apiKey); 
    @Override
    public abstract IAbstractClient addAutentication(String user, String password); 

    @Override
    public IResponseResult execute() throws IOException, ApiException {
      IClient stream=null;
      IResponse response =null;
        try {   
              switch(request.getBody().getBodyType()){
                case NONE:                   
                    break;
                case URL_FORM_ENCODED:
                    request.getRequestPath().getRequestParameter().processParameters();
                    String encoded = this.request.getRequestPath().getRequestParameter().getEncodedParameters();
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
    public IAbstractClient buildRequestParameterAccessor(ParameterType type) {
          if (request.getRequestPath() != null) {
            try {
                request.getRequestPath().addRequestParameter(RequestParameters.build(type));
            } catch (Exception ex) {
                Logger.getLogger(AbstractClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       return this;
    }

    @Override
    public IAbstractClient addNewRequestParameter(String name, Object value)throws Exception {
       if (request.getRequestPath() != null) {
           if(request.getRequestPath().getRequestParameter() != null){
              request.getRequestPath().getRequestParameter().addParameter(name, value);
           }
       }
       return this;
    }  
       
}
