package com.restfulclient.interfaces;

import com.restfulclient.impl.ApiException;
import com.restfulclient.impl.ParameterType;

public interface IRequestPath {

  public String getPath();
  public String buildXFormURLEncodedParameters()throws ApiException;
  public void addRequestParameter(IRequestParameters parameters);
  public void addNewRequestParameter(String name, Object value);
  public void addNewPathParameter(String name, Object value);
  public void clean();
  public ParameterType getParameterType();
  public void buildPath() throws ApiException;
}