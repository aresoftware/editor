package com.app.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * http://localhost:8080/AppEditor/initeditor?template=PLANTILLA DE TEXTO XHTML&parameters= PARAMETER EXAMPLE
 */
public class HttpServletEditor  extends HttpServlet {

	private static final long serialVersionUID = 1L;
	 
    @Override
    public void init(final ServletConfig config) throws ServletException {
        super.init(config);
        getServletContext().log("init() called");
    }
    

    
    @Override
    protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        getServletContext().log("service() called");

        /**TODO: read templates and parameters*/
        String template = (String)request.getParameter("template");
        System.out.println("service:"+template);
        String parametersJSON = (String)request.getParameter("parameters");
        
        /**merge templates and parameters*/
        template = processTemplate(template,parametersJSON);
        
        request.getSession().setAttribute("template", template);
        response.sendRedirect("/AppEditor/index.jsf");
    }
 
    private String processTemplate(String template,String parametersJSON) {
    	
    	template = template + parametersJSON;
    	
    	return template;
    }
    
    @Override
    public void destroy() {
        getServletContext().log("destroy() called");
    }



}
