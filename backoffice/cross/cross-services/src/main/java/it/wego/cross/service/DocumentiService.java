/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author CS
 */
@Service
public interface DocumentiService {

    public void InserisciDocumentoo() throws Exception;
    public void InserisciDocumento() throws Exception;
}