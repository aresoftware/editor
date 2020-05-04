package com.app;


import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.app.pdf.HtmlToPdfInDTO;
import com.app.pdf.PDFToolConverter;
import com.app.servlet.DataServletDownload;

@ManagedBean(name = "editor")
public class AppEditor {

	private String value = "This editor is provided by PrimeFaces";

	public AppEditor(){
	      HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	      value=(String)session.getAttribute("template");
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String inicializar() {
		/**Restablecer a plantillas*/
		value = "hola mundo";
		return "";
	}

	public String guardar() {
		/**Restablecer a plantillas*/
		value = "hola mundo";
		return "";
	}
	
	public String vistaprevia() {
		/**devolver pdf*/
		PDFToolConverter pdfTools = new PDFToolConverter();
		HtmlToPdfInDTO htmldto = new HtmlToPdfInDTO();
		try {
			htmldto.setXhtmlSource(value);
			byte[] pdf = pdfTools.convertLetterHTMLtoPDF(htmldto);
			DataServletDownload data = new DataServletDownload();
			data.setData(pdf);
			data.setFileName("XXXXXXX.pdf");
			data.setRedirect("/AppEditor/download");
			HttpServletResponse session = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			session.sendRedirect("/AppEditor/download");
		} catch (Exception e) {
			e.printStackTrace();
		}
		value = "hola mundo";
		return "";
	}
	
	
}