package com.app.pdf;


import java.io.Serializable;

public class HtmlToPdfInDTO implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private String numTramite;
  
  private String xhtmlSource;
  
  private byte[] firmaImage;
  
  private String fullPathFirmaImage;
  
  private String idUsuario;
  
  public String getFullPathFirmaImage() {
    return this.fullPathFirmaImage;
  }
  
  public void setFullPathFirmaImage(String fullPathFirmaImage) {
    this.fullPathFirmaImage = fullPathFirmaImage;
  }
  
  public String getIdUsuario() {
    return this.idUsuario;
  }
  
  public void setIdUsuario(String idUsuario) {
    this.idUsuario = idUsuario;
  }
  
  public byte[] getFirmaImage() {
    return this.firmaImage;
  }
  
  public void setFirmaImage(byte[] firmaImage) {
    this.firmaImage = firmaImage;
  }
  
  public String getNumTramite() {
    return this.numTramite;
  }
  
  public void setNumTramite(String numTramite) {
    this.numTramite = numTramite;
  }
  
  public String getXhtmlSource() {
    return this.xhtmlSource;
  }
  
  public void setXhtmlSource(String xhtmlSource) {
    this.xhtmlSource = xhtmlSource;
  }
}
