package com.restfulclient.impl;

import com.restfulclient.interfaces.IRequestBody;

public class RequestBody implements IRequestBody {

    public static final int ZERO_LENGTH=0;
    private byte[] body;
    private BodyType bodyType;

    private RequestBody(final BodyType bodyType, final byte[] body) {
        this.body = body;
        this.bodyType = bodyType;
    }

    public static IRequestBody build(final BodyType bodyType,final byte[] body) {
        return new RequestBody(bodyType, body);
    }

    @Override
    public byte[] getMessage() {
        return body;
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

}
