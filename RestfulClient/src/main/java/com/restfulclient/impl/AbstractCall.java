package com.restfulclient.impl;

import com.restfulclient.interfaces.ICall;
import com.restfulclient.interfaces.IMessage;
import com.restfulclient.interfaces.IRequest;
import com.restfulclient.interfaces.IRequestBody;
import com.restfulclient.interfaces.IRequestPath;
import com.restfulclient.interfaces.IResponse;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Abstract representation of any restful call
 */
public abstract class AbstractCall implements ICall {
   
  private IRequestPath path = null;
  private IRequest request  = null;
  private IRequestBody body = null;
  public static final RequestType TYPE_JSON_QUERY = RequestType.TYPE_JSON_QUERY;
  public static final RequestType TYPE_HTTP_QUERY = RequestType.TYPE_HTTP_QUERY;
  public static final RequestType TYPE_JSON_BODY = RequestType.TYPE_JSON_BODY;
  private boolean IS_FOR_POST=false;
  
  public AbstractCall(){   
  }  
  
  private void create(){
    path = RequestPathImpl.build();
    body = RequestBodyImpl.build();
    request = RequestImpl.build();
  }
  
  private void fillRequest(){
    request.addRequestPath(path);
    request.addRequestBody(body);
  }
  
  private void checkRequest(){
       if(IS_FOR_POST && this.body.getRequestBody()==null){       
        try {       
            throw new Exception("Request is for POST or PUT  but did not found a TYPE_JSON_BODY selected");
        } catch (Exception ex) {
            Logger.getLogger(AbstractCall.class.getName()).log(Level.SEVERE, null, ex);
        }
    }else{
        if(!IS_FOR_POST && this.body.getRequestBody()!=null){     
            try {
                throw new Exception("Request is NOT POST or PUT but we not found a TYPE_JSON_BODY selected");
            } catch (Exception ex) {
                Logger.getLogger(AbstractCall.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }    
  }
  
  /**
   * Method to implement the restful API call
   */
  public abstract void prepareCall();
  
  @Override
   public Map<Object, Object> executeCall(){
    create();
    checkRequest();
    prepareCall();
    fillRequest();    
    IResponse response  = ClientImpl.build(request).execute();
    return response.getMap();
  }
  
  protected void addQueryParameter(String parameter  , String value ) {
    if(path!=null){
      path.addQueryParameter(parameter,value);
    }
  }
   
  protected void addHeader(String name , String value){
    if(request!=null){
      request.addHeader(name, value);
    }
  }
  
  protected void addRequestBody(IMessage iMessage){
    if(body!=null){
      body.setRequestBody(iMessage);
    }
  }
  
  protected void addURLInfo(String url  , String webServiceMethod , Method httpMethod , RequestType requestType ){
    if(path!=null){
      switch(requestType){
        case TYPE_JSON_QUERY:
          path.addPathForJSONQuery(url,webServiceMethod,httpMethod);
          break;
        case TYPE_HTTP_QUERY:
          path.addPathForHTTPQuery(url, webServiceMethod, httpMethod);
          break;
        case TYPE_JSON_BODY:
          IS_FOR_POST=true;
          path.addPathForRequestForJSONBody(url,webServiceMethod,httpMethod);
          break;
      }
    }
  }
   
  private  enum RequestType{
    TYPE_JSON_QUERY, TYPE_HTTP_QUERY, TYPE_JSON_BODY;   
  }
}