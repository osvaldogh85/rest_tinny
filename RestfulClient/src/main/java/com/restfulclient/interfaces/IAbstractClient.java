/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.restfulclient.interfaces;

import com.restfulclient.impl.ApiException;
import java.io.IOException;

/**
 *
 * @author odge
 */
public interface IAbstractClient {
     public IResponseResult execute()throws IOException, ApiException;
     public IAbstractClient addAutentication(String apiKey); 
     public IAbstractClient addAutentication(String user, String password); 
     public IAbstractClient addHeader(String name, Object value); 
     public IAbstractClient addNewRequestQueryParameter(String name, Object value)throws Exception;
     public IAbstractClient addNewRequestPathParameter(String name, Object value)throws Exception;
     public IAbstractClient addRequestBody(IRequestBody body);
     public IAbstractClient addFormMultipartParams(String name, Object value);
     public IAbstractClient addNewPathParameter(String name, Object value)throws Exception;
     public IAbstractClient addNewRequestXFormURLEncodeParameter(String name, Object value)throws Exception;
}
