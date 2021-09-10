/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.restfulclient.interfaces;

/**
 *
 * @author odge
 */
public interface IAuthorization {
    public default String getAuthorization() {return "Authorization";}
    public String getAuthorizationToken();
    public void clean();
}
