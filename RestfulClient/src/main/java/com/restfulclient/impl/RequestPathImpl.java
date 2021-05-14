package com.restfulclient.impl;

import com.restfulclient.interfaces.IRequestPath;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RequestPathImpl implements IRequestPath {

    private String path = null;
    private Method method;
    private static IRequestPath instance;
    private final LinkedHashMap<String, Object> parameters = new LinkedHashMap<String, Object>();

    private RequestPathImpl() {
     
    }

    public static IRequestPath build() {
        instance = new RequestPathImpl();
        return instance;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public void addPathForJSONQuery(String url, String webServiceMethod, Method httpMethod) {
        try {
            create(url, webServiceMethod, httpMethod, true);
        } catch (Exception ex) {
            Logger.getLogger(RequestPathImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void addPathForHTTPQuery(String url, String webServiceMethod, Method httpMethod) {
        try {
            create(url, webServiceMethod, httpMethod, false);
        } catch (Exception ex) {
            Logger.getLogger(RequestPathImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void addPathForRequestForJSONBody(String url, String webServiceMethod, Method httpMethod) {
        try {
            create(url, webServiceMethod, httpMethod);
        } catch (Exception ex) {
            Logger.getLogger(RequestPathImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void addQueryParameter(String parameter, Object value) {
        this.parameters.put(parameter, value);
    }

    //IF isJSONRequestis true = method  createPath assume that the path for the request should be like this https://server_url/methodName/000-00-000003-1
    private void create(String url, String service, Method method, boolean useJSONParams) throws Exception {
        if (url == null) {
            throw new Exception("Error while creating path there is no url to request");
        }
       String serviceCall;
       
        if(service.charAt(0) == '/')
          serviceCall = service.substring(1,service.length());
        else
          serviceCall = service;
              
        if (serviceCall != null) {
            if (!url.substring(url.length() - 1).equals("/")) {
                path = url + "/" + serviceCall;
            } else {
                path = url + serviceCall;
            }
        } else {
            if (!url.substring(url.length() - 1).equals("/")) {
                path = url + "/";
            } else {
                path = url;
            }
        }
        if (method == null) {
            throw new Exception("Error while creating path there is no method to request");
        }
        this.method = method;

        if (useJSONParams) {
            if (parameters == null) {
                throw new Exception("Error while creating path there is no parameters to json request path");
            }
            //URL  https://server_example_urlm/up/v5/
            //key                 value
            //workItem          workItem
            //workItem_value    00-00-000003-1
            parameters.keySet().forEach(key -> {
                path = path + "/" + parameters.get(key).toString();
                //1 loop https://server_url/methodName
                //2 loop https://server_url/methodName/000-00-000003-1
            });
        } else {
            if (parameters == null) {
                return;
            }
            // https://server_url/methodName
            //parameter map format
            //key                 value
            //param1             value1
            //param2             value2
            //***************************
            //paramN-1             valueN-1
            path = path + "?";
            var paramCount = parameters.keySet().size() - 1;
            var count = 0;
            // https://server_url/methodName?
            for (String key : parameters.keySet()) {
                if (count < paramCount) {
                    //loop https://server_url/methodName?param1=value1&param2=value3&
                    path = path + key.trim() + "=" + parameters.get(key).toString().trim() + "&";
                } else {
                    //else https://server_url/methodName?param1=value1&param2=value3&paramN-1=valueN-1
                    path = path + key.trim() + "=" + parameters.get(key).toString().trim();
                }
                count++;
            }
        }
    }

    private void create(String url, String service, Method method) throws Exception {
        if (url == null) {
            throw new Exception("Error while creating path there is no url to request ");
        }

        String serviceCall;
       
        if(service.charAt(0) == '/')
          serviceCall = service.substring(1,service.length());
        else
          serviceCall = service;
        
        if (serviceCall != null) {
            if (!url.substring(url.length() - 1).equals("/")) {
                path = url + "/" + serviceCall;
            } else {
                path = url + serviceCall;
            }
        } else {
            if (!url.substring(url.length() - 1).equals("/")) {
                path = url + "/";
            } else {
                path = url;
            }
        }
        if (method == null) {
            throw new Exception("Error while creating path there is no method to request");
        }
        this.method = method;
    }
}