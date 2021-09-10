package com.restfulclient.interfaces;

import com.restfulclient.impl.ApiException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface IClient {

  public OutputStream getRequestStream()throws IOException,ApiException;
  public InputStreamReader getErrorStream()throws IOException,ApiException;
  public InputStreamReader getResponseStream()throws  IOException,ApiException;
  public void close()throws IOException;
  public int getResponseCode()  throws IOException,ApiException;
  public String getResponseMessage()  throws IOException,ApiException;
  public Map<String, List<String>> getResponseHeader()  throws IOException,ApiException;;
  public IResponse execute() throws IOException,ApiException;
  public boolean isOKHTTP() throws IOException;
  public void addRequestProperty(String param, String value);
  public void clean();
}