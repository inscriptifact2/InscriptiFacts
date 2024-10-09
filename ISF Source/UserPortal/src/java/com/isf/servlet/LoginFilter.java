/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isf.servlet;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author raok1
 */
public class LoginFilter implements Filter {

    public void init(FilterConfig fc) throws ServletException {
        
    }

    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) throws IOException, ServletException {
       HttpServletRequest request = (HttpServletRequest) sr;
       HttpServletResponse    response   = (HttpServletResponse)sr1;
       String uri = request.getRequestURI();
       if( uri.contains("images/") || 
               uri.contains("css/")||
               uri.contains("getNameSuggestions.js") || 
               uri.contains("checkUniqueUid.jsp") || 
               uri.contains("index.jsp") ||
               uri.contains("userRegbk.jsp")||
               uri.contains("forgotbk.jsp")|| 
               uri.contains("forgotpassword.jsp") || 
               uri.contains("userRegistration.jsp") ||
               uri.contains("loginbk.jsp")||
               uri.contains("getSuggestedNames.jsp")                ){
           fc.doFilter(sr,sr1);
       } else {
           HttpSession session = (HttpSession)request.getSession(false);
           if(session.getAttribute("loggedUser") == null)
               response.sendRedirect("index.jsp");
           else
               fc.doFilter(sr,sr1);
       }
           
    }

    public void destroy() {
        
    }
    
}
