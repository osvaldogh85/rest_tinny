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
    private boolean USE_BODY_ON_REQUEST = false;
    private MediaType callType=null;
    
    public AbstractClient(MediaType callType) {
        this.callType=callType;
        request = Request.build(RequestPath.build());
    }

    private void checkRequest() throws ApiException {
        if (USE_BODY_ON_REQUEST && this.request.useBodyRequest()==false) {
            try {
                throw new ApiException("Request is for POST or PUT or PATCH or DELETE but not anybut TYPE_JSON_BODY not found");
            } catch (ApiException ex) {
                throw ex;
            }
        } else {
            if (!USE_BODY_ON_REQUEST &&  this.request.useBodyRequest()) {
                try {
                    throw new ApiException("Request is NOT POST or PUT but TYPE_JSON_BODY not found");
                } catch (ApiException ex) {
                    throw ex;
                }
            }
        }
    }

    /**
     * Method to implement the restful API call
     */
    public abstract void prepareCall();
    public abstract void addAutentication();      

    @Override
    public IResponseResult executeCall() throws IOException, ApiException {
      IClient stream=null;
      IResponse response =null;
        try {            
            checkRequest();
            addAutentication();
            prepareCall();          
            stream = Client.build(request);
            response = stream.execute();
            return response.getIResponseResult();
        } catch (IOException | ApiException ex) {
            throw ex;
        }finally{
            stream=null;
        }        
    }

    protected void addQueryParameter(String parameter, Object value) {
        if (request.getRequestPath() != null) {
            request.getRequestPath().addQueryParameter(parameter, value);
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
            request.setRequestBody(body);
        }
    }

    protected void addURLInfo(String url, String webServiceMethod, Method httpMethod) {
        if (request.getRequestPath() != null) {              
            switch (this.callType) {
                case JSON_QUERY:
                    request.getRequestPath().addPathForJSONQuery(url, webServiceMethod, httpMethod);
                    break;
                case HTTP_QUERY:
                    request.getRequestPath().addPathForHTTPQuery(url, webServiceMethod, httpMethod);
                    break;
                case PLAIN_BODY:
                    USE_BODY_ON_REQUEST = true;
                    request.getRequestPath().addPathForRequestForJSONBody(url, webServiceMethod, httpMethod);
                    break;
                case JSON_QUERY_AND_BODY:
                    USE_BODY_ON_REQUEST = true;
                    request.getRequestPath().addPathForJSONQuery(url, webServiceMethod, httpMethod);
                    break;
                case HTTP_QUERY_AND_BODY:
                    USE_BODY_ON_REQUEST = true;
                    request.getRequestPath().addPathForHTTPQuery(url, webServiceMethod, httpMethod);
                    break;
                case MULTIPART_FORM_JSON_QUERY:
                    USE_BODY_ON_REQUEST = true;
                    request.getRequestPath().addPathForJSONQuery(url, webServiceMethod, httpMethod);
                    break;
                case MULTIPART_FORM_HTTP_QUERY:
                    USE_BODY_ON_REQUEST = true;
                    request.getRequestPath().addPathForHTTPQuery(url, webServiceMethod, httpMethod);
                    break;
                case MULTIPART_FORM:
                    USE_BODY_ON_REQUEST = true;
                    request.getRequestPath().addPathForHTTPQuery(url, webServiceMethod, httpMethod);
                    break;
                case BINARY:
                    USE_BODY_ON_REQUEST = true;
                    request.getRequestPath().addPathForRequestForJSONBody(url, webServiceMethod, httpMethod);
                    break;    
            }
        }
    }

}
