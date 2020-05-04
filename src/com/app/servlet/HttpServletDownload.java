package com.app.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class HttpServletDownload extends HttpServlet {
	protected abstract DataServletDownload getData();

	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DataServletDownload data = null;
		data = getData();
		if (data != null && data.getRedirect() != null && !data.getRedirect().equals("")) {
			response.sendRedirect(data.getRedirect());
			return;
		}
		if (data == null || data.getData() == null) {
			String mensaje = "ERROR DESCONOCIDO";
			if (data != null && data.getMensajeError() != null)
				mensaje = data.getMensajeError();
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<head>");
			out.println("<title>SIREP 2 WEB - Error de generacion de descarga</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<center>");
			out.println("<b>" + mensaje + "</b>");
			out.println("</center>");
			out.println("</body>");
			out.println("</html>");
			out.close();
		} else {
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename=" + data.getFileName());
			response.setContentLength(data.getData().length);
			ServletOutputStream servletOutputStream = response.getOutputStream();
			servletOutputStream.write(data.getData());
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		super.doGet(request, response);
	}
}
