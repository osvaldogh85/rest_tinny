package com.restfulclient.impl;

import com.restfulclient.interfaces.IAuthorization;
import com.restfulclient.interfaces.IHTTPCall;
import com.restfulclient.interfaces.IMessage;
import com.restfulclient.interfaces.IRequest;
import com.restfulclient.interfaces.IRequestBody;
import com.restfulclient.interfaces.IRequestPath;
import com.restfulclient.interfaces.IResponse;
import com.restfulclient.interfaces.IResponseResult;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Abstract representation of any restful call
 */
public abstract class AbstractCall implements IHTTPCall {

    private IRequestPath path = null;
    private IRequest request = null;
    private IRequestBody body = null;
    private boolean USE_BODY_ON_REQUEST = false;

    public AbstractCall() {
    }

    private void create() {
        path = RequestPathImpl.build();
        body = RequestBodyImpl.build();
        request = RequestImpl.build();
    }

    private void fillRequest() {
        request.addRequestPath(path);
        request.addRequestBody(body);
    }

    private void checkRequest() {
        if (USE_BODY_ON_REQUEST && this.body.getRequestBody() == null) {
            try {
                throw new Exception("Request is for POST or PUT or PATCH or DELETE but not anybut TYPE_JSON_BODY not found");
            } catch (Exception ex) {
                Logger.getLogger(AbstractCall.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (!USE_BODY_ON_REQUEST && this.body.getRequestBody() != null) {
                try {
                    throw new Exception("Request is NOT POST or PUT but TYPE_JSON_BODY not found");
                } catch (Exception ex) {
                    Logger.getLogger(AbstractCall.class.getName()).log(Level.SEVERE, null, ex);
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
    public IResponseResult executeCall() {
        create();
        checkRequest();
        addAutentication();
        prepareCall();
        fillRequest();
        IResponse response = StreamManagerImpl.build(request).execute();
        return response.getIResponseResult();
    }

    protected void addQueryParameter(String parameter, Object value) {
        if (path != null) {
            path.addQueryParameter(parameter, value);
        }
    }

    protected void addHeader(String name, String value) {
        if (request != null) {
            request.addHeader(name, value);
        }
    }

    protected void addRequestBody(IMessage iMessage) {
        if (body != null) {
            body.setRequestBody(iMessage);
        }
    }

    protected void addURLInfo(String url, String webServiceMethod, Method httpMethod, RequestType requestType) {
        if (path != null) {
            
            switch (requestType) {
                case TYPE_JSON_QUERY :
                    path.addPathForJSONQuery(url, webServiceMethod, httpMethod);
                    break;
                case TYPE_HTTP_QUERY:
                    path.addPathForHTTPQuery(url, webServiceMethod, httpMethod);
                    break;
                case TYPE_JSON_BODY:
                    USE_BODY_ON_REQUEST = true;
                    path.addPathForRequestForJSONBody(url, webServiceMethod, httpMethod);
                    break;
                case TYPE_JSON_QUERY_AND_BODY:
                    USE_BODY_ON_REQUEST = true;
                    path.addPathForJSONQuery(url, webServiceMethod, httpMethod);
                    break;
                case TYPE_HTTP_QUERY_AND_BODY:
                    USE_BODY_ON_REQUEST = true;
                    path.addPathForHTTPQuery(url, webServiceMethod, httpMethod);
                    break;
            }
        }
    }
    
     public enum RequestType {
        TYPE_JSON_QUERY, TYPE_HTTP_QUERY, TYPE_JSON_BODY,TYPE_JSON_QUERY_AND_BODY,TYPE_HTTP_QUERY_AND_BODY;
    } 
}
