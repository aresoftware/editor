package com.app.servlet;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

public class LoadVariableForTemplate {

	String template = "";
	
	public LoadVariableForTemplate(String template,Hashtable<String, String> parameters) throws Exception{
		Enumeration<String> e = parameters.keys();
		String key;
		while( e.hasMoreElements() ){
			key = e.nextElement();
			compilarVariables(template,key,parameters.get(key));
		}
	}


	private String compilarVariables(String plantilla, String parametro, String value) throws Exception {
		String plantillaFinal = "";
		String variable = "";
		boolean termine = false;
		int i = 0;
		for (i = 0; i < plantilla.length() - 1; i++) {
			if (!((plantilla.length() - 8) <= i)) {
				if (plantilla.substring(i, i + 8).equals("&lt;&lt;")) {
					i = i + 8;
					for (; i < plantilla.length() - 8 && !termine; i++) {
						if (!((plantilla.length() - 8) <= i)) {
							if (plantilla.substring(i, i + 8).equals("&gt;&gt;")) {
								i = i + 8;
								break;
							}
							if (!termine) {
								variable = variable + new String(plantilla.charAt(i) + "");
							}
						}
					}
					plantillaFinal = plantillaFinal + getValueVariable(value);
					variable = "";
				}
				plantillaFinal = plantillaFinal + plantilla.substring(i, i + 1);
			}
		}
		return plantillaFinal + plantilla.substring(i - 7, plantilla.length());
	}

	public String getValueVariable(String value) throws Exception {
		String salida = value;
		if (salida == null || salida.trim().trim().equals("")) {
			if (!value.equals("nomFirma") && !value.equals("cargoFirma") && !value.equals("ccbFirma")) {
				salida = "<u><em><font color=\"#ff0000\">" + value + "</font></em></u>";
			} else {
				salida = value;
			}
		}
		if (salida.equals("null")) {
			salida = "";
		}
		return salida;
	}

	private String pintarValorVariable(Collection lista, int tipo) throws Exception {
		String valor = "", element = null;
		if (lista != null)
			for (Iterator<String> iter = lista.iterator(); iter.hasNext();) {
				element = (String) iter.next();
				switch (tipo) {
				case 0: {
					/** LISTA LAS VARIABLES CON ESPACIO INTERMEDIO */
					valor = valor + "<div>" + element + "</div></br>";
				}
					break;
				case 1: {
					/** TIPO TEXTO */
					valor = valor + element;
				}
					break;
				case 2: {
					/**
					 * El tipo 2 significa que valor devuelve un arreglo pero que para mostrarlo
					 * debe coger cada registro y mostrarlo separado por comas.
					 */
					valor = valor + element + ", ";
				}
					break;
				case 3: {
					/**
					 * El tipo 3 significa que si el valor de la variable existe, se le muestra al
					 * usuario lo que el sistema pudo calcular, sin embargo se permite modificar
					 * dicha salida.
					 */
					valor = valor + element;
				}
					break;
				case 5: {
					/** LISTA LAS VARIABLES SIN ESPACIO INTERMEDIO */
					valor = valor + "<div>" + element + "</div>";
				}
					break;
				}
			}
		if (tipo == 0 && valor != null && valor.length() > 5) {
			valor = valor.substring(0, valor.length() - 5);
		}
		if (tipo == 2 && valor != null && valor.length() > 2) {
			valor = valor.substring(0, valor.length() - 2);
		}
		if (tipo == 5 && valor != null && valor.length() > 0) {
			/**
			 * Este replaceAll permite que las variables conserven sus espacios. Solo aplica
			 * para la variable <<registro>>. Puede ocasionar que el texto se salga del
			 * editor
			 */
			valor = valor.replaceAll(" ", "&nbsp;");
		}

		return valor;

	}
}

class BeanVariable {
	private String key;

	private String Value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return Value;
	}

	public void setValue(String value) {
		Value = value;
	}
}
