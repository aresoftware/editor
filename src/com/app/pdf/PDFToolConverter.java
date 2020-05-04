package com.app.pdf;



public class PDFToolConverter {
  public byte[] convertLetterHTMLtoPDF(HtmlToPdfInDTO value) throws Exception {
    try {
      System.out.println("ENTRO WS");
      return HTMLtoPDF.getInstance().convertLetterHTMLtoPDF(value);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } 
  }
  
  public String pruebaConexion() {
    return "MENSAJE DE PRUEBA";
  }
}

