package com.krakedev.inventarios.entidades;

public class CategoriaUDM {
	private String codigoCategoriaUDM;
	private String nombre;
	
	//CONSTRUCTOR VACIO
	public CategoriaUDM() {	}
	
	//CONSTRUCTOR CON PARAMETROS
	public CategoriaUDM(String codigoCategoriaUDM, String nombre) {
		super();
		this.codigoCategoriaUDM = codigoCategoriaUDM;
		this.nombre = nombre;
	}
	
	//METODO GET Y SET 
	public String getCodigoCategoriaUDM() {
		return codigoCategoriaUDM;
	}
	
	public void setCodigoCategoriaUDM(String codigoCategoriaUDM) {
		this.codigoCategoriaUDM = codigoCategoriaUDM;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	//METODO TO STRING
	@Override
	public String toString() {
		return "CategoriaUDM [codigoCategoriaUDM=" + codigoCategoriaUDM + ", nombre=" + nombre + "]";
	}	
}
