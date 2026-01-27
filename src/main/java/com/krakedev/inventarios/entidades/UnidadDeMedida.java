package com.krakedev.inventarios.entidades;

public class UnidadDeMedida {
	private String nombre;
	private String descripcion;
	private CategoriaUDM categoriaUDM;
	//CONSTRUCTOR VACIO 
	public UnidadDeMedida() {
	}
	//CONSTRUCTOR CON PARAMETROS 
	public UnidadDeMedida(String nombre, String descripcion, CategoriaUDM categoriaUDM) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.categoriaUDM = categoriaUDM;
	}
	//METODOS GET Y SET 
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public CategoriaUDM getCategoriaUDM() {
		return categoriaUDM;
	}
	public void setCategoriaUDM(CategoriaUDM categoriaUDM) {
		this.categoriaUDM = categoriaUDM;
	}
	//METODO TO STRING 
	@Override
	public String toString() {
		return "UnidadDeMedida [nombre=" + nombre + ", descripcion=" + descripcion + ", categoriaUDM=" + categoriaUDM
				+ "]";
	}
	
	
	
}
