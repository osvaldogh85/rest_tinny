package com.restfulclient.interfaces;

import java.util.Map;

public interface IHeader {

  public void addHeader(String name  , Object value);
  public Map<String, Object> getHeader(); 
  public void clean();
}