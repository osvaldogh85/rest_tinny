package com.restfulclient.interfaces;

public interface IRequestPath {

  public String getPath();
  public String getEncodedParameters();
  public void addRequestParameter(IRequestParameters parameters);
  public IRequestParameters getRequestParameter() ;
  public void clean();
  }