package com.restfulclient.interfaces;
import com.restfulclient.impl.Method;

public interface IRequestPath {

  public String getPath();
  public Method getMethod();
  public void addPathForJSONQuery(String url , String webServiceMethod,Method httpMethod );
  public void addPathForHTTPQuery(String url , String webServiceMethod ,Method httpMethod);
  public void addPathForRequestForJSONBody(String url , String webServiceMethod, Method httpMethod);
  public void addQueryParameter(String parameter , String value);
  }