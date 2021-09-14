package com.restfulclient.impl;

import com.restfulclient.interfaces.IRequestBody;

public class RequestBody implements IRequestBody {

    public static final int ZERO_LENGTH = 0;
    private byte[] body;
    private BodyType bodyType;
    private Object content=null;

    private RequestBody(final BodyType bodyType, Object content,final byte[] body) {
        this.body = body;
        this.bodyType = bodyType;
        this.content=content;
    }

    public static IRequestBody build(final BodyType bodyType,String body) {
        return new RequestBody(bodyType, body, body.getBytes());
    }
    
    public static IRequestBody build(final BodyType bodyType) {
        return new RequestBody(bodyType, null, null);
    }
    
     public static IRequestBody buildDefault() {
        return new RequestBody(BodyType.NONE, null, null);
    }

    public static IRequestBody build(final BodyType bodyType,Object content,final byte[] body) {
        return new RequestBody(bodyType, content , body);
    }

     
    @Override
    public byte[] getBody() {
        return body;
    }

    @Override
    public void setBody(byte[] body) {
        this.body = body;
    }
    
    @Override
    public BodyType getBodyType() {
        return bodyType;
    }

    @Override
    public void clean() {
        body=null;
        bodyType=null;
    }

    @Override
    public long getBodyLength() {
        return this.body.length;
    }

    @Override
    public Object getBodyContent() {
        return content;
    }

    @Override
    public void setBodyContent(Object content) {
       this.content=content;
    }

}
