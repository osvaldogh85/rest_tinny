package com.restfulclient.interfaces;

public interface IResponse {
  public String getContent();
  public IResponseResult getIResponseResult();
  public void process(IClient client);
}