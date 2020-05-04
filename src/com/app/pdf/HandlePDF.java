package com.app.pdf;


import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfEncryptor;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

public class HandlePDF {
  private static HandlePDF instance;
  
  public static HandlePDF getInstance() {
    if (instance == null)
      instance = new HandlePDF(); 
    return instance;
  }
  
  public byte[] hideBars(byte[] inputFile) throws Exception {
    try {
      PdfReader reader = new PdfReader(inputFile);
      int n = reader.getNumberOfPages();
      Rectangle psize = reader.getPageSize(1);
      float width = psize.getHeight();
      float height = psize.getWidth();
      Document document = new Document(new Rectangle(width, height));
      ByteArrayOutputStream stream = new ByteArrayOutputStream();
      PdfCopy writer = new PdfCopy(document, stream);
      writer.setViewerPreferences(768);
      document.open();
      int i = 0;
      while (i < n) {
        document.newPage();
        i++;
        PdfImportedPage page1 = writer.getImportedPage(reader, i);
        writer.addPage(page1);
      } 
      document.close();
      return stream.toByteArray();
    } catch (Exception de) {
      de.printStackTrace();
      throw new Exception(de);
    } 
  }
  
  public void notAllowPrint(String inputFile, String outFile) throws Exception {
    try {
      PdfReader reader = new PdfReader(inputFile);
      PdfEncryptor.encrypt(reader, new FileOutputStream(outFile), null, null, 1792, false);
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception(e);
    } 
  }
}
