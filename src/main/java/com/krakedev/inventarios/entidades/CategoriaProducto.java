package com.krakedev.inventarios.entidades;

public class CategoriaProducto {
	private int codigo;
	private String nombre;
	private CategoriaProducto categoriaPadre;
	
	//CONSTRUCTOR VACIO
	public CategoriaProducto() {	}
	//CONSTRUCTOR CON PASO DE PARAMETROS
	public CategoriaProducto(int codigo, String nombre, CategoriaProducto categoriaPadre) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.categoriaPadre = categoriaPadre;
	}
	//METODO GET Y SET 
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public CategoriaProducto getCategoriaPadre() {
		return categoriaPadre;
	}
	public void setCategoriaPadre(CategoriaProducto categoriaPadre) {
		this.categoriaPadre = categoriaPadre;
	}
	//METODO TO STRING
	@Override
	public String toString() {
		return "CategoriaProducto [codigo=" + codigo + ", nombre=" + nombre + ", categoriaPadre=" + categoriaPadre
				+ "]";
	} 
	
	
}
