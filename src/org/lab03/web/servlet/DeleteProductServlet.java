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

import org.lab03.web.beans.UserAccount;
import org.lab03.web.utils.DBUtils;
import org.lab03.web.utils.MyUtils;

@WebServlet(urlPatterns = { "/deleteProduct" })
public class DeleteProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    public DeleteProductServlet() {
        super();
    }
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	// Set extended title Content-Security-Policy and X-XSS-Protection
//    	response.setHeader("Content-Security-Policy", "script-src 'self';");
//    	response.setHeader("X-XSS-Protection", "1: mode=block"); 
    	
    	HttpSession session = request.getSession();
        
        // Check User has logged on
        UserAccount loginedUser = MyUtils.getLoginedUser(session);
 
        // Not logged in
        if (loginedUser == null) {
            // Redirect to login page.
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        // Store info to the request attribute before forwarding.
        request.setAttribute("user", loginedUser);
    	
    	Connection conn = MyUtils.getStoredConnection(request);
 
        String code = (String) request.getParameter("code");
 
        String errorString = null;
 
        try {
            DBUtils.deleteProduct(conn, code);
        } catch (SQLException e) {
            e.printStackTrace();
            errorString = e.getMessage();
        } 
         
        // If has an error, redirecte to the error page.
        if (errorString != null) {
            // Store the information in the request attribute, before forward to views.
            request.setAttribute("errorString", errorString);
            // 
            RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/views/deleteProductErrorView.jsp");
            dispatcher.forward(request, response);
        }
        // If everything nice.
        // Redirect to the product listing page.        
        else {
            response.sendRedirect(request.getContextPath() + "/productList");
        }
 
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	// Set extended title Content-Security-Policy and X-XSS-Protection
//    	response.setHeader("Content-Security-Policy", "script-src 'self';");
//    	response.setHeader("X-XSS-Protection", "1: mode=block"); 
    	
        doGet(request, response);
    }
 
}