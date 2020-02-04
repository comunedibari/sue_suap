/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import org.springframework.stereotype.Service;

/**
 *
 * @author Gabriele
 */
@Service
public interface AuthenticationService {

    public String getAuthServer();
    public String getPostLogoutUrl();
    public String getAuthServerLogoutUrl();
}
