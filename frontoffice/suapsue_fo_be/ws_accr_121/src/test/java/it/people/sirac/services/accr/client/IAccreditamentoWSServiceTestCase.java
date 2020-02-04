/**
 * Copyright (c) 2011, Regione Emilia-Romagna, Italy
 *  
 * Licensed under the EUPL, Version 1.1 or - as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the
 * Licence.
 * 
 * For convenience a plain text copy of the English version
 * of the Licence can be found in the file LICENCE.txt in
 * the top-level directory of this software distribution.
 * 
 * You may obtain a copy of the Licence in any of 22 European
 * Languages at:
 * 
 * http://joinup.ec.europa.eu/software/page/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * 
 * This product includes software developed by Yale University
 * 
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
package it.people.sirac.services.accr.client;

public class IAccreditamentoWSServiceTestCase extends junit.framework.TestCase {
    public IAccreditamentoWSServiceTestCase(java.lang.String name) {
        super(name);
    }

    public void testIAccreditamentoWSWSDL() throws Exception {
        javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
        java.net.URL url = new java.net.URL(new it.people.sirac.services.accr.client.IAccreditamentoWSServiceLocator().getIAccreditamentoWSAddress() + "?WSDL");
        javax.xml.rpc.Service service = serviceFactory.createService(url, new it.people.sirac.services.accr.client.IAccreditamentoWSServiceLocator().getServiceName());
        assertTrue(service != null);
    }

    public void test1IAccreditamentoWSCreaProfiloLocale() throws Exception {
        it.people.sirac.services.accr.client.IAccreditamentoWSStub binding;
        try {
            binding = (it.people.sirac.services.accr.client.IAccreditamentoWSStub)
                          new it.people.sirac.services.accr.client.IAccreditamentoWSServiceLocator().getIAccreditamentoWS();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        it.people.sirac.accr.beans.ProfiloLocale value = null;
        value = binding.creaProfiloLocale(new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String());
        // TBD - validate results
    }

    public void test2IAccreditamentoWSAccreditaIntermediario() throws Exception {
        it.people.sirac.services.accr.client.IAccreditamentoWSStub binding;
        try {
            binding = (it.people.sirac.services.accr.client.IAccreditamentoWSStub)
                          new it.people.sirac.services.accr.client.IAccreditamentoWSServiceLocator().getIAccreditamentoWS();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        binding.accreditaIntermediario(new java.lang.String(), new java.lang.String(), new java.lang.String(), new it.people.sirac.accr.beans.ProfiloAccreditamento());
        // TBD - validate results
    }

    public void test3IAccreditamentoWSCreaDelega() throws Exception {
        it.people.sirac.services.accr.client.IAccreditamentoWSStub binding;
        try {
            binding = (it.people.sirac.services.accr.client.IAccreditamentoWSStub)
                          new it.people.sirac.services.accr.client.IAccreditamentoWSServiceLocator().getIAccreditamentoWS();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        binding.creaDelega(new it.people.sirac.accr.beans.Delega());
        // TBD - validate results
    }

    public void test4IAccreditamentoWSEsisteProfiloLocale() throws Exception {
        it.people.sirac.services.accr.client.IAccreditamentoWSStub binding;
        try {
            binding = (it.people.sirac.services.accr.client.IAccreditamentoWSStub)
                          new it.people.sirac.services.accr.client.IAccreditamentoWSServiceLocator().getIAccreditamentoWS();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        boolean value = false;
        value = binding.esisteProfiloLocale(new java.lang.String(), new java.lang.String());
        // TBD - validate results
    }

    public void test5IAccreditamentoWSGetAccreditamenti() throws Exception {
        it.people.sirac.services.accr.client.IAccreditamentoWSStub binding;
        try {
            binding = (it.people.sirac.services.accr.client.IAccreditamentoWSStub)
                          new it.people.sirac.services.accr.client.IAccreditamentoWSServiceLocator().getIAccreditamentoWS();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        it.people.sirac.accr.beans.Accreditamento[] value = null;
        value = binding.getAccreditamenti(new java.lang.String(), new java.lang.String());
        // TBD - validate results
    }

    public void test6IAccreditamentoWSGetDeleghe() throws Exception {
        it.people.sirac.services.accr.client.IAccreditamentoWSStub binding;
        try {
            binding = (it.people.sirac.services.accr.client.IAccreditamentoWSStub)
                          new it.people.sirac.services.accr.client.IAccreditamentoWSServiceLocator().getIAccreditamentoWS();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        it.people.sirac.accr.beans.Delega[] value = null;
        value = binding.getDeleghe(new java.lang.String(), new java.lang.String(), 0);
        // TBD - validate results
    }

    public void test7IAccreditamentoWSEliminaDelega() throws Exception {
        it.people.sirac.services.accr.client.IAccreditamentoWSStub binding;
        try {
            binding = (it.people.sirac.services.accr.client.IAccreditamentoWSStub)
                          new it.people.sirac.services.accr.client.IAccreditamentoWSServiceLocator().getIAccreditamentoWS();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        binding.eliminaDelega(new it.people.sirac.accr.beans.Delega());
        // TBD - validate results
    }

    public void test8IAccreditamentoWSEsisteQualifica() throws Exception {
        it.people.sirac.services.accr.client.IAccreditamentoWSStub binding;
        try {
            binding = (it.people.sirac.services.accr.client.IAccreditamentoWSStub)
                          new it.people.sirac.services.accr.client.IAccreditamentoWSServiceLocator().getIAccreditamentoWS();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        boolean value = false;
        value = binding.esisteQualifica(new java.lang.String(), new java.lang.String(), new java.lang.String[0]);
        // TBD - validate results
    }

    public void test9IAccreditamentoWSGetAccreditamentoById() throws Exception {
        it.people.sirac.services.accr.client.IAccreditamentoWSStub binding;
        try {
            binding = (it.people.sirac.services.accr.client.IAccreditamentoWSStub)
                          new it.people.sirac.services.accr.client.IAccreditamentoWSServiceLocator().getIAccreditamentoWS();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        it.people.sirac.accr.beans.Accreditamento value = null;
        value = binding.getAccreditamentoById(0, new java.lang.String(), new java.lang.String());
        // TBD - validate results
    }

    public void test10IAccreditamentoWSGetAccreditamentoByCodiceIntermediario() throws Exception {
        it.people.sirac.services.accr.client.IAccreditamentoWSStub binding;
        try {
            binding = (it.people.sirac.services.accr.client.IAccreditamentoWSStub)
                          new it.people.sirac.services.accr.client.IAccreditamentoWSServiceLocator().getIAccreditamentoWS();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        it.people.sirac.accr.beans.Accreditamento value = null;
        value = binding.getAccreditamentoByCodiceIntermediario(new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String());
        // TBD - validate results
    }

    public void test11IAccreditamentoWSGetProfiloLocale() throws Exception {
        it.people.sirac.services.accr.client.IAccreditamentoWSStub binding;
        try {
            binding = (it.people.sirac.services.accr.client.IAccreditamentoWSStub)
                          new it.people.sirac.services.accr.client.IAccreditamentoWSServiceLocator().getIAccreditamentoWS();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        it.people.sirac.accr.beans.ProfiloLocale value = null;
        value = binding.getProfiloLocale(new java.lang.String(), new java.lang.String());
        // TBD - validate results
    }

    public void test12IAccreditamentoWSGetQualifiche() throws Exception {
        it.people.sirac.services.accr.client.IAccreditamentoWSStub binding;
        try {
            binding = (it.people.sirac.services.accr.client.IAccreditamentoWSStub)
                          new it.people.sirac.services.accr.client.IAccreditamentoWSServiceLocator().getIAccreditamentoWS();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        it.people.sirac.accr.beans.Qualifica[] value = null;
        value = binding.getQualifiche();
        // TBD - validate results
    }

    public void test13IAccreditamentoWSGetQualificheAbilitate() throws Exception {
        it.people.sirac.services.accr.client.IAccreditamentoWSStub binding;
        try {
            binding = (it.people.sirac.services.accr.client.IAccreditamentoWSStub)
                          new it.people.sirac.services.accr.client.IAccreditamentoWSServiceLocator().getIAccreditamentoWS();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        it.people.sirac.accr.beans.Qualifica[] value = null;
        value = binding.getQualificheAbilitate(new java.lang.String(), new java.lang.String());
        // TBD - validate results
    }

    public void test14IAccreditamentoWSGetQualificheAccreditabili() throws Exception {
        it.people.sirac.services.accr.client.IAccreditamentoWSStub binding;
        try {
            binding = (it.people.sirac.services.accr.client.IAccreditamentoWSStub)
                          new it.people.sirac.services.accr.client.IAccreditamentoWSServiceLocator().getIAccreditamentoWS();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        it.people.sirac.accr.beans.Qualifica[] value = null;
        value = binding.getQualificheAccreditabili(new java.lang.String(), new java.lang.String());
        // TBD - validate results
    }

    public void test15IAccreditamentoWSGetQualificaById() throws Exception {
        it.people.sirac.services.accr.client.IAccreditamentoWSStub binding;
        try {
            binding = (it.people.sirac.services.accr.client.IAccreditamentoWSStub)
                          new it.people.sirac.services.accr.client.IAccreditamentoWSServiceLocator().getIAccreditamentoWS();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        it.people.sirac.accr.beans.Qualifica value = null;
        value = binding.getQualificaById(new java.lang.String());
        // TBD - validate results
    }

    public void test16IAccreditamentoWSGetQualifiche2Persona() throws Exception {
        it.people.sirac.services.accr.client.IAccreditamentoWSStub binding;
        try {
            binding = (it.people.sirac.services.accr.client.IAccreditamentoWSStub)
                          new it.people.sirac.services.accr.client.IAccreditamentoWSServiceLocator().getIAccreditamentoWS();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        it.people.sirac.accr.beans.Qualifica2Persona[] value = null;
        value = binding.getQualifiche2Persona(new java.lang.String());
        // TBD - validate results
    }

    public void test17IAccreditamentoWSCanCreateDelega() throws Exception {
        it.people.sirac.services.accr.client.IAccreditamentoWSStub binding;
        try {
            binding = (it.people.sirac.services.accr.client.IAccreditamentoWSStub)
                          new it.people.sirac.services.accr.client.IAccreditamentoWSServiceLocator().getIAccreditamentoWS();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        boolean value = false;
        value = binding.canCreateDelega(new java.lang.String(), new java.lang.String(), new java.lang.String());
        // TBD - validate results
    }

    public void test18IAccreditamentoWSGetAutoCertTemplate() throws Exception {
        it.people.sirac.services.accr.client.IAccreditamentoWSStub binding;
        try {
            binding = (it.people.sirac.services.accr.client.IAccreditamentoWSStub)
                          new it.people.sirac.services.accr.client.IAccreditamentoWSServiceLocator().getIAccreditamentoWS();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        java.lang.String value = null;
        value = binding.getAutoCertTemplate(new java.lang.String());
        // TBD - validate results
    }

    public void test19IAccreditamentoWSGetDelegaTemplate() throws Exception {
        it.people.sirac.services.accr.client.IAccreditamentoWSStub binding;
        try {
            binding = (it.people.sirac.services.accr.client.IAccreditamentoWSStub)
                          new it.people.sirac.services.accr.client.IAccreditamentoWSServiceLocator().getIAccreditamentoWS();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        java.lang.String value = null;
        value = binding.getDelegaTemplate(new java.lang.String());
        // TBD - validate results
    }

    public void test20IAccreditamentoWSGetQualifica2Persona() throws Exception {
        it.people.sirac.services.accr.client.IAccreditamentoWSStub binding;
        try {
            binding = (it.people.sirac.services.accr.client.IAccreditamentoWSStub)
                          new it.people.sirac.services.accr.client.IAccreditamentoWSServiceLocator().getIAccreditamentoWS();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        it.people.sirac.accr.beans.Qualifica2Persona value = null;
        value = binding.getQualifica2Persona(new java.lang.String());
        // TBD - validate results
    }

}
