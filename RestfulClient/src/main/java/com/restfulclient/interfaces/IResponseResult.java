/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.restfulclient.interfaces;

import com.restfulclient.impl.ApiException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author odge
 */
public interface IResponseResult {
     public Map<String, Object> getMap()  throws  ApiException; 
     public List<Map<String, Object>> getList() throws  ApiException ;
     public StringBuilder getResponseContent(); 
     public int getResponseCode();
     public boolean isSuccessful();
}
