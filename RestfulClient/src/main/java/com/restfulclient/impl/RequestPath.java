package com.restfulclient.impl;


import com.restfulclient.interfaces.IRequestParameters;
import com.restfulclient.interfaces.IRequestPath;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;

public class RequestPath implements IRequestPath {

    private StringBuilder path = null;
    private String serverBasePath = null, methodBasePath = null;
    private IRequestParameters requestParameters = null;
    private LinkedHashMap<String, Object> optionalPathParameters = null;

    private RequestPath(final String url, final String service) throws Exception {
        try {
            this.serverBasePath = url;
            this.methodBasePath = service;
            if (serverBasePath == null) {
                throw new Exception("Error while creating path there is no server url defined to request");
            }

            if (methodBasePath == null) {
                throw new Exception("Error while creating path there is no service endpoint defined to request");
            }         
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static IRequestPath build(final String url, final String service) throws Exception {
        return new RequestPath(url, service);
    }

    @Override
    public void buildPath() throws ApiException {
        StringBuilder serviceToCall = new StringBuilder();
        if (methodBasePath.charAt(0) == '/') {
            serviceToCall.append(methodBasePath.substring(1, methodBasePath.length()));
        } else {
            serviceToCall.append(methodBasePath);
        }

        /**
         * Means service method is on format with path parameters
         * /interviews/{interview_id}/call/actions/{call_id}/execute
         *
         * The key of the map must be the name of the parameter defined on the
         * path key value interview_id 123 call_id ABC
         *
         * You must add parameters depends on path definition in this example
         * you have to call 2 times the add parameter method because you have
         * defined 2 path parameters into the path
         */
        if (optionalPathParameters != null && !optionalPathParameters.isEmpty()) {
            serviceToCall = new StringBuilder();
            for (String key : optionalPathParameters.keySet()) {
                methodBasePath = methodBasePath.replaceAll("\\{" + key + "\\}", escapeString(optionalPathParameters.get(key).toString()));
            }
            if (methodBasePath.charAt(0) == '/') {
                serviceToCall.append(methodBasePath.substring(1, methodBasePath.length()));
            } else {
                serviceToCall.append(methodBasePath);
            }
        }

        path = new StringBuilder();
        if (!serverBasePath.substring(serverBasePath.length() - 1).equals("/")) {
            path.append(serverBasePath).append("/").append(serviceToCall.toString());
        } else {
            path.append(serverBasePath).append(serviceToCall.toString());
        }

        if (requestParameters != null && requestParameters.getParamterType() != ParameterType.FOR_URL_ENCODED) {
            requestParameters.buildParameters();
            path.append(requestParameters.getEncodedParameters().toString());
        }
    }

    @Override
    public String getPath() {
        return path.toString();
    }

    @Override
    public String buildXFormURLEncodedParameters() throws ApiException {
        if (requestParameters != null) {
            requestParameters.buildParameters();
            return requestParameters.getEncodedParameters().toString();
        }
        return null;
    }

    @Override
    public void addRequestParameter(IRequestParameters parameters) {
        this.requestParameters = parameters;
    }

    @Override
    public void clean() {
        if (requestParameters != null) {
            requestParameters.clean();
        }
        if (optionalPathParameters != null) {
            optionalPathParameters.clear();
        }
        optionalPathParameters=null;
        requestParameters = null;
        path = null;
    }

    @Override
    public void addNewRequestParameter(String name, Object value) {
        if (requestParameters != null) {
            requestParameters.addParameter(name, value);
        }
    }

    @Override
    public ParameterType getParameterType() {
        if (requestParameters != null) {
            return requestParameters.getParamterType();
        }
        return null;
    }

    @Override
    public void addNewPathParameter(String name, Object value) {
        if (optionalPathParameters == null) {
            optionalPathParameters = new LinkedHashMap<String, Object>();
        }

        optionalPathParameters.put(name, value);
    }

    /**
     * Escape the given string to be used as URL query value.
     *
     * @param str String to be escaped
     * @return Escaped string
     */
    private String escapeString(String str) {
        try {
            return URLEncoder.encode(str, "utf8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            return str;
        }
    }
}
