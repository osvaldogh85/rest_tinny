/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.restfulclient.interfaces;

import com.restfulclient.impl.ParameterType;
import java.util.LinkedHashMap;

/**
 *
 * @author odge
 */
public interface IRequestParameters {
     public void addParameter(ParameterType type,LinkedHashMap<String, Object> params) throws Exception;
     public String getEncodedParameters();
     public void clean();
}
