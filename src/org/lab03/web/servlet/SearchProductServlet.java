package org.lab03.web.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.lab03.web.beans.Product;
import org.lab03.web.utils.DBUtils;
import org.lab03.web.utils.MyUtils;

@WebServlet(urlPatterns = { "/search"})
public class SearchProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    public SearchProductServlet() {
        super();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	// Set extended title Content-Security-Policy and X-XSS-Protection
    	response.setHeader("Content-Security-Policy", "script-src 'self';");
    	response.setHeader("X-XSS-Protection", "1: mode=block"); 
    	
    	// Set attribute HTTPOnly
    	HttpSession session = request.getSession();
    	String jsessionId = session.getId();
    	String cookie = "JSESSIONID=" + jsessionId + "; Path=/; HttpOnly";
    	response.setHeader("Set-Cookie", cookie);
    	
    	Connection conn = MyUtils.getStoredConnection(request);
        String code = (String) request.getParameter("code");
        String errorString = null;
        Product product = null;
        if (code != null)
        {
        	try {
            	product = DBUtils.findProduct(conn, code);
        	} catch (SQLException e) {
        		e.printStackTrace();
        		errorString = e.getMessage();
        	}
        } 
         
        // If has an error, redirect to the error page.
        //if (errorString != null) {
            // Store the information in the request attribute, before forward to views.
            request.setAttribute("errorString", errorString);
            request.setAttribute("searchResult", product);
            
            // 
            RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/views/searchView.jsp");
            dispatcher.forward(request, response);
        //}
        // If everything nice.
        // Redirect to the product listing page.        
        //else {
           // response.sendRedirect(request.getContextPath() + "/search");
        //}
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	// Set extended title Content-Security-Policy and X-XSS-Protection
    	response.setHeader("Content-Security-Policy", "script-src 'self';");
    	response.setHeader("X-XSS-Protection", "1: mode=block"); 
    	
    	// Set attribute HTTPOnly
    	HttpSession session = request.getSession();
    	String jsessionId = session.getId();
    	String cookie = "JSESSIONID=" + jsessionId + "; Path=/; HttpOnly";
    	response.setHeader("Set-Cookie", cookie);
    	
        doGet(request, response);
    }
}
