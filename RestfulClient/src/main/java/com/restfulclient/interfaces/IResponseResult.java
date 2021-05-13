/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.restfulclient.interfaces;

import java.util.Map;

/**
 *
 * @author odge
 */
public interface IResponseResult {
     public Map<String, Object> getMap(); 
     public String getJSON(); 
}
