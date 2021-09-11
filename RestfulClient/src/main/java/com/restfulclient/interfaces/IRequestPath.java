package com.restfulclient.interfaces;

public interface IRequestPath {

  public String getPath();
  public String getXFormURLEncodedParameters();
  public void addRequestParameter(IRequestParameters parameters);
  public IRequestParameters getRequestParameter() ;
  public void clean();
  }