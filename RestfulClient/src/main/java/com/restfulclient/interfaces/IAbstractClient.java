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
     public IResponseResult executeCall()throws IOException, ApiException;
}
