package com.restfulclient.interfaces;

import com.restfulclient.impl.BodyType;

public interface IRequestBody {
   public byte[] getMessage();
   public BodyType getBodyType();
   public void clean();
}