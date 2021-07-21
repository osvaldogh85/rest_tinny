package com.restfulclient.impl;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import com.restfulclient.interfaces.IHeader;
import com.restfulclient.interfaces.IRequest;
import com.restfulclient.interfaces.IRequestBody;
import com.restfulclient.interfaces.IRequestPath;
import com.restfulclient.interfaces.IResponse;
import com.restfulclient.interfaces.IStreamManager;

public class RequestImpl implements IRequest {

  private final int DEFAULT_TIME_OUT  = 60000;
  private final CPHeaderImpl headerImpl;
  private IRequestPath path;
  private IRequestBody body;
  private int maxTimeOutInMills;

  private RequestImpl(){
    this.headerImpl = new CPHeaderImpl();
  }

  public static IRequest build() {
    return new RequestImpl();
  }

  @Override
  public Map<String,Object> getHeader(){
    return headerImpl.getHeader();
  }

  @Override
  public void addHeader(String name  , String value){
    if(headerImpl!=null)
      headerImpl.addHeader(name,value);
  }

  @Override
  public void addRequestPath(IRequestPath requestPath) {
    this.path=requestPath;
  }

  @Override
  public void addRequestBody(IRequestBody requestBody) {
    this.body=requestBody;
  }

  @Override
  public IRequestPath getRequestPath() {
    return path;
  }

  @Override
  public void setTimeOut(int timeout) {
    this.maxTimeOutInMills=timeout;
  }

  @Override
  public int getTimeOut()  {
    if(maxTimeOutInMills==0)
      maxTimeOutInMills = DEFAULT_TIME_OUT;

    return maxTimeOutInMills;
  }

  @Override
  public IResponse call(IStreamManager client)throws Exception {
    if(body.getRequestBody() != null){
        try {
            writeBodyRequest(client);
        } catch (IOException ex) {
            Logger.getLogger(RequestImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    IResponse response = ResponseImpl.build();
    response.process(client);
    return response;
  }

  private void writeBodyRequest(IStreamManager manager) throws IOException{
    DataOutputStream wr = null;
      try {
          wr = new DataOutputStream(manager.getRequestStream());
          JSONObject jsonInput = body.getRequestBody().getMessage();
          wr.write(jsonInput.toString().getBytes() , 0 , jsonInput.toString().getBytes().length);
          wr.close();
      } catch (Exception ex) {
          Logger.getLogger(RequestImpl.class.getName()).log(Level.SEVERE, null, ex);
      } finally {
          try {
              if(wr!=null)
                wr.close();
          } catch (IOException ex) {
              Logger.getLogger(RequestImpl.class.getName()).log(Level.SEVERE, null, ex);
          }
      }
  }

  private class CPHeaderImpl  implements IHeader{      
    private final Map<String, Object> headerMap;    
    public CPHeaderImpl(){
      headerMap = new HashMap<>();
    }
    @Override
    public Map<String, Object> getHeader() {
      return headerMap;
    }
    @Override
    public void addHeader(String name, String value) {
       headerMap.put(name,value);
    }
  }
}