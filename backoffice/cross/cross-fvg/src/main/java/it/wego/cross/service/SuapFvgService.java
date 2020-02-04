/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import org.springframework.stereotype.Service;

/**
 *
 * @author Gabriele
 */
@Service
public interface SuapFvgService {

    public String getXmlFromTemplate(byte[] xmlToTransform) throws Exception;

}
