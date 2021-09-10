/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.restfulclient.interfaces;

import com.restfulclient.impl.ApiException;
import com.restfulclient.impl.ParameterType;
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
     public IAbstractClient buildRequestParameterAccessor(ParameterType type); 
     public IAbstractClient addNewRequestParameter(String name, Object value)throws Exception;; 
     public IRequestPath getRequestPath();
}
