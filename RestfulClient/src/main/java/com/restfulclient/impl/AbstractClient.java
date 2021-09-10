package com.restfulclient.impl;

import com.restfulclient.interfaces.IRequest;
import com.restfulclient.interfaces.IResponse;
import com.restfulclient.interfaces.IResponseResult;
import java.io.IOException;
import com.restfulclient.interfaces.IClient;
import com.restfulclient.interfaces.IAbstractClient;
import com.restfulclient.interfaces.IRequestBody;
import com.restfulclient.interfaces.IRequestParameters;

/**
 * Abstract representation of any restful call
 */
public abstract class AbstractClient implements IAbstractClient {

    private IRequest request = null;
    
    public AbstractClient(final String server, final String service,Method method) throws Exception {
       request = Request.build(method, RequestPath.build(server, service));
    }

    /**
     * Method to implement the restful API call
     * @throws com.restfulclient.impl.ApiException
     */
    public abstract void prepareCall()throws ApiException;
    @Override
    public abstract IAbstractClient addAutentication(String apiKey); 
    @Override
    public abstract IAbstractClient addAutentication(String user, String password); 

    @Override
    public IResponseResult executeCall() throws IOException, ApiException {
      IClient stream=null;
      IResponse response =null;
        try {         
            prepareCall();          
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

    protected void addRequestParameter(IRequestParameters requestParameter) throws Exception {
        if (request.getRequestPath() != null) {
            request.getRequestPath().addRequestParameter(requestParameter);
        }
    }

    protected void addHeader(String name, String value) {
        if (request != null) {
            request.addHeader(name, value);
        }
    }
    
    protected void addFormMultipartParams(String name, Object value) {
        if (request != null) {
            request.addFormMultipartParams(name, value);
        }
    }

    protected void addRequestBody(IRequestBody body) {
        if (request != null) {
            request.addBody(body);
        }
    }
}
