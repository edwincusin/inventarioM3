package com.krakedev.inventarios.entidades;

public class EstadoPedido {
	private String codigo;
	private String descripcion;
	
	//COSNTRUCTOR VACIO 
	public EstadoPedido() {
	}

	//COSNTRUCTOR CON PARAMETROS 
	public EstadoPedido(String codigo, String descripcion) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
	}
	
	//METODOS GET Y SET
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	//METODO TOSTRING 
	@Override
	public String toString() {
		return "EstadoPedido [codigo=" + codigo + ", descripcion=" + descripcion + "]";
	}	
	
}
