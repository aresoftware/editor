package com.app.pdf;


import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Properties;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;

public class HTMLtoPDF {
  private static HTMLtoPDF instance;
  
  public static String PATH_WIZARD_FILES = "PATH_WIZARD_FILES";
  
  public static String XSL_PARA_CARTAS = "xhtml2fo.xsl";
  
  public String PATHTEMP = "C:\\opt\\IBM\\Websphere\\AppServer\\wizard\\temp\\";
  
  public String PATHWIZARD = "C:\\opt\\IBM\\Websphere\\AppServer\\wizard\\";
  
  public String LINKTEMP = "C:\\\\opt\\\\IBM\\\\WebSphere\\\\AppServer\\\\wizard\\\\temp\\\\";
  
  public static HTMLtoPDF getInstance() {
    if (instance == null)
      instance = new HTMLtoPDF(); 
    return instance;
  }
  
  public void convert(File xmlfile, File xsltfile, File pdffile) throws Exception {
    File baseDir = new File(".");
    File outDir = new File(baseDir, "out");
    outDir.mkdirs();
    FopFactory fopFactory = FopFactory.newInstance(pdffile.toURI());
    FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
    OutputStream out = new FileOutputStream(pdffile);
    out = new BufferedOutputStream(out);
    Result res = null;
    try {
      Fop fop = fopFactory.newFop("application/pdf", foUserAgent, out);
      TransformerFactory factory = TransformerFactory.newInstance();
      Transformer transformer = factory.newTransformer(new StreamSource(xsltfile));
      transformer.setParameter("versionParam", "2.0");
      Source src = new StreamSource(xmlfile);
      res = new SAXResult(fop.getDefaultHandler());
      transformer.transform(src, res);
    } finally {
      out.close();
    } 
  }
  
  public byte[] convertLetterHTMLtoPDF(HtmlToPdfInDTO value) throws Exception {
    try {
      File imageFirma = null;
      if (value.getFirmaImage() != null) {
        String newPath = crearImagenFirma(imageFirma, value.getIdUsuario(), value.getFirmaImage());
        String tagFirma = "<img src=\"" + newPath + "\" border=\"0\" />";
        value.setXhtmlSource(value.getXhtmlSource().replaceAll("ccbfirma", tagFirma));
      } 
      File destinoxhtml = new File(this.PATHTEMP);
      File xhtml = File.createTempFile(value.getNumTramite(), ".xhtml", destinoxhtml);
      FileWriter fileWriter = new FileWriter(xhtml);
      BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
      PrintWriter printWriter = new PrintWriter(bufferedWriter);
      printWriter.write(formatXHTML(value.getXhtmlSource()));
      printWriter.flush();
      printWriter.close();
      String origenxhtml2fo = String.valueOf(this.PATHWIZARD) + XSL_PARA_CARTAS;
      File xslt = new File(origenxhtml2fo);
      File pdf = File.createTempFile(value.getNumTramite(), ".pdf", destinoxhtml);
      convert(xhtml, xslt, pdf);
      byte[] sourceFile = new byte[(int)pdf.length()];
      InputStream in = new FileInputStream(pdf);
      in.read(sourceFile, 0, (int)pdf.length());
      in.close();
      byte[] PDFEXIT = HandlePDF.getInstance().hideBars(sourceFile);
      xhtml.delete();
      pdf.delete();
      if (imageFirma != null)
        imageFirma.delete(); 
      return PDFEXIT;
    } catch (Exception e5) {
      e5.printStackTrace();
      throw new Exception(e5);
    } 
  }
  
  private static String formatXHTML(String text) throws Exception {
    try {
      String header = "<?xml version=\"1.0\" encoding=\"iso-8859-1\"?><html><head><title>CARTA SIREP2</title></head><body>";
      String footerHTML = "</body></html>";
      Properties defaultProps = new Properties();
      FileInputStream in = new FileInputStream("C:\\opt\\IBM\\Websphere\\AppServer\\wizard\\caracteres_especiales.properties");
      defaultProps.load(in);
      in.close();
      Enumeration<Object> listaEnum = defaultProps.keys();
      System.out.println("TEXTO INICIAL=" + text);
      while (listaEnum.hasMoreElements()) {
        String key = (String)listaEnum.nextElement();
        text = text.replaceAll(key, defaultProps.getProperty(key));
      } 
      text = text.replaceAll("size=\"1\"", "size=\"8\"");
      text = text.replaceAll("size=\"2\"", "size=\"10\"");
      text = text.replaceAll("size=\"3\"", "size=\"12\"");
      text = text.replaceAll("size=\"4\"", "size=\"14\"");
      text = text.replaceAll("size=\"5\"", "size=\"18\"");
      text = text.replaceAll("size=\"6\"", "size=\"24\"");
      text = text.replaceAll("size=\"7\"", "size=\"36\"");
      String textoFinal = String.valueOf(header) + text + footerHTML;
      System.out.println("TEXTO FINAL=" + textoFinal);
      return textoFinal;
    } catch (Exception e) {
      throw e;
    } 
  }
  
  private String crearImagenFirma(File firma, String idUsuario, byte[] firmaImage) throws Exception {
    try {
      if (firmaImage != null) {
        String path = this.PATHWIZARD;
        String tempdir = String.valueOf(path) + File.separator + "temp";
        String tempdir2 = this.LINKTEMP;
        File destino = new File(tempdir);
        if (!destino.exists())
          destino.mkdirs(); 
        String pathFileFirma = destino.getPath();
        firma = File.createTempFile(idUsuario, ".jpg", destino);
        FileOutputStream archivo = new FileOutputStream(firma);
        archivo.write(firmaImage);
        archivo.close();
        String exitPath = String.valueOf(tempdir2) + firma.getName();
        return exitPath;
      } 
      System.out.println("PASA 2 ELSE");
      return null;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }
}

