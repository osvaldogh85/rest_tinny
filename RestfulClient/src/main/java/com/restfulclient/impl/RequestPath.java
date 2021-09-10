package com.restfulclient.impl;

import com.restfulclient.interfaces.IRequestPath;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RequestPath implements IRequestPath {

    private StringBuilder path = null;
    private Method method;
    private final LinkedHashMap<String, Object> parameters = new LinkedHashMap<String, Object>();

    private RequestPath() {
          path = null;
    }
    
    public static IRequestPath build() {
        return new RequestPath();
    }

    @Override
    public String getPath() {
        return path.toString();
    }

    @Override
    public Method getMethod() {
        return method;
    }    
    
    @Override
    public void addQueryParameter(String parameter, Object value) {
        this.parameters.put(parameter, value);
    }

    @Override
    public void addPathForJSONQuery(String url, String webServiceMethod, Method httpMethod) {
        try {
            create(url, webServiceMethod, httpMethod);
            processParamters(path, true);
        } catch (Exception ex) {
            Logger.getLogger(RequestPath.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void addPathForHTTPQuery(String url, String webServiceMethod, Method httpMethod) {
        try {
            create(url, webServiceMethod, httpMethod);
            processParamters(path, false);
        } catch (Exception ex) {
            Logger.getLogger(RequestPath.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void addPathForRequestForJSONBody(String url, String webServiceMethod, Method httpMethod) {
        try {
            create(url, webServiceMethod, httpMethod);
        } catch (Exception ex) {
            Logger.getLogger(RequestPath.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //IF useJSONParams true = method  createPath assume that the path for the request should be like this https://server_url/methodName/000-00-000003-1
    private void processParamters(StringBuilder path, boolean useJSONParams)throws Exception{
         if (useJSONParams) {
            if (parameters.isEmpty()) {
                throw new Exception("Error while creating path there is no parameters to json request path");
            }
            //URL  https://server_example_urlm/up/v5/
            //key                 value
            //workItem          workItem
            //workItem_value    00-00-000003-1
            parameters.keySet().forEach(key -> {
                path.append("/").append(parameters.get(key).toString());
                //1 loop https://server_url/methodName
                //2 loop https://server_url/methodName/000-00-000003-1
            });
            parameters.clear();
        } else {
            if (parameters.isEmpty()) {
                return;
            }
            // https://server_url/methodName
            //parameter map format
            //key                 value
            //param1             value1
            //param2             value2
            //***************************
            //paramN-1             valueN-1
            path.append("?");
            var paramCount = parameters.keySet().size() - 1;
            var count = 0;
            // https://server_url/methodName?
            for (String key : parameters.keySet()) {
                if (count < paramCount) {
                    //loop https://server_url/methodName?param1=value1&param2=value3&
                    path.append(key.trim()).append("=").append( parameters.get(key).toString().trim()).append("&");
                } else {
                    //else https://server_url/methodName?param1=value1&param2=value3&paramN-1=valueN-1
                     path.append(key.trim()).append("=").append(parameters.get(key).toString().trim());
                }
                count++;
            }
            parameters.clear();
        }
    }

    private void create(final String url, final String service, final Method method) throws Exception {
        if (url == null) {
            throw new Exception("Error while creating path there is no url to request");
        }
        if (service == null) {
            throw new Exception("Error while creating path there is no service to request");
        }
        if (method == null) {
            throw new Exception("Error while creating path there is no method to request");
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

        this.method = method;
    }
}
