package it.wego.cross.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService
{
  @Value("${authentication.authServer}")
  private String authServer;
  @Value("${authentication.postLogoutUrl}")
  private String postLogoutUrl;
  @Value("${authentication.authServerLogoutUrl}")
  private String authServerLogoutUrl;
  
  public String getAuthServer()
  {
    return this.authServer;
  }
  
  public String getPostLogoutUrl()
  {
    return this.postLogoutUrl;
  }
  
  public String getAuthServerLogoutUrl()
  {
    return this.authServerLogoutUrl;
  }
}
