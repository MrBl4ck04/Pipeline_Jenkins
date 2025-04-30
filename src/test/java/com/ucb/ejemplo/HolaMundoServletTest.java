package com.ucb.ejemplo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class HolaMundoServletTest {

    private HolaMundoServlet servlet;
    
    @Mock
    private HttpServletRequest request;
    
    @Mock
    private HttpServletResponse response;
    
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        servlet = new HolaMundoServlet();
    }
    
    @Test
    public void testDoGet() throws Exception {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        
        Mockito.when(response.getWriter()).thenReturn(printWriter);
        
        servlet.doGet(request, response);
        
        String result = stringWriter.toString();
        assertTrue("La respuesta debe contener '¡Hola Mundo'", result.contains("¡Hola Mundo"));
        assertTrue("La respuesta debe ser HTML", result.contains("<!DOCTYPE html>"));
        
        Mockito.verify(response).setContentType("text/html;charset=UTF-8");
    }
}