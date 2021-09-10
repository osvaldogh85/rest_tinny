/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.restfulclient.impl;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 *
 * @author odge
 */
public class AuthenticatorService extends Authenticator{
    private final String user;
    private final String password;
  
    
   private AuthenticatorService(String user,String password){
       this.user=user;
       this.password=password;
   }
       
   public static Authenticator build(String user,String password)  {
      return new AuthenticatorService(user,password);
   }
  
   @Override
   protected PasswordAuthentication getPasswordAuthentication() {
     return new PasswordAuthentication(user, password.toCharArray());
   }
 
}
