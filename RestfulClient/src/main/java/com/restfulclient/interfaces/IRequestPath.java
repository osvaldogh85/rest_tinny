package com.restfulclient.interfaces;

public interface IRequestPath {

  public String getPath();
  public String getEncodedParameters();
  public void addRequestParameter(IRequestParameters parameters) throws Exception;
  public void clean();
  }