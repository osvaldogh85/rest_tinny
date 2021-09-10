/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.restfulclient.interfaces;

import com.restfulclient.impl.ApiException;
import com.restfulclient.impl.ParameterType;

/**
 *
 * @author odge
 */
public interface IRequestParameters {
     public void addParameter(String key , Object value);
     public String getEncodedParameters();
     public void setParamterType(ParameterType type);
     public void clean();
     public void processParameters()  throws ApiException;
}
