package com.restfulclient.interfaces;

import com.restfulclient.impl.BodyType;

public interface IRequestBody {
   public BodyType getBodyType();
   public long getBodyLength();
   public Object getBodyContent();
   public void setBodyContent(Object content);
   public byte[] getBody();
   public void setBody(byte[] body);
   public void clean();
}