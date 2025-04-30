package com.ucb.ejemplo;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HolaMundoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Aplicación Hola Mundo</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>¡Hola Mundo desde el Pipeline de Jenkins!</h1>");
            out.println("<p>Esta es una aplicación simple para demostrar CI/CD con Jenkins.</p>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}