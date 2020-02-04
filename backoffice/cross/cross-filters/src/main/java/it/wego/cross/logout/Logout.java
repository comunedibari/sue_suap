/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.logout;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author piergiorgio
 */
public class Logout extends HttpServlet {

    private static String SIRAC_SSO = "SIRAC-SSO";
    private static String SIRAC = "SIRAC";
    private static String CAS = "CAS";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String tipoAutenticazione = getServletConfig().getInitParameter("tipoAutenticazione");
        String urlLogoutSiracSSO = getServletConfig().getInitParameter("urlLogoutSiracSSO");
        String urlPostLogout = getServletConfig().getInitParameter("urlPostLogout");
        if (SIRAC_SSO.equals(tipoAutenticazione)) {
            response.sendRedirect(urlLogoutSiracSSO + "?returnURL=" + urlPostLogout);
        }
        if (SIRAC.equals(tipoAutenticazione)) {
            session.invalidate();
            RequestDispatcher rd = request.getRequestDispatcher(urlPostLogout);
            rd.forward(request, response);
        }
        if (CAS.equals(tipoAutenticazione)) {
            // TODO - completare
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
