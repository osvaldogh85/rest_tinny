package com.restfulclient.impl;

import com.restfulclient.interfaces.IRequestParameters;
import com.restfulclient.interfaces.IRequestPath;

public class RequestPath implements IRequestPath {

    private StringBuilder path = null;
    private IRequestParameters parameters = null;

    private RequestPath(final String url, final String service) throws Exception {
        try {
            create(url, service);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static IRequestPath build(final String url, final String service) throws Exception {
        return new RequestPath(url, service);
    }

    private void create(final String url, final String service) throws Exception {
        if (url == null) {
            throw new Exception("Error while creating path there is no server url defined to request");
        }
        if (service == null) {
            throw new Exception("Error while creating path there is no service endpoint defined to request");
        }

        StringBuilder serviceToCall = new StringBuilder();

        if (service.charAt(0) == '/') {
            serviceToCall.append(service.substring(1, service.length()));
        } else {
            serviceToCall.append(service);
        }

        path = new StringBuilder();
        if (!url.substring(url.length() - 1).equals("/")) {
            path.append(url).append("/").append(serviceToCall.toString());
        } else {
            path.append(url).append(serviceToCall.toString());
        }
    }

    @Override
    public String getPath() {
        if (parameters != null && parameters.getParamterType() != ParameterType.FOR_URL_ENCODED) {
            path.append(parameters.getEncodedParameters());
        }
        return path.toString();
    }

    @Override
    public String getXFormURLEncodedParameters() {
        if (parameters != null  && parameters.getParamterType() == ParameterType.FOR_URL_ENCODED) {
           return this.parameters.getEncodedParameters();
        }
        return null;
    }

    @Override
    public void addRequestParameter(IRequestParameters parameters)  {
        this.parameters = parameters;
    }

    @Override
    public void clean() {  
        if(parameters!=null)
         parameters.clean();
        
        parameters = null;
        path = null;
    }

    @Override
    public IRequestParameters getRequestParameter() {
        return parameters;
    }
}
