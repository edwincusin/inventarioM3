package com.krakedev.inventarios.entidades;

public class TipoDocumento {
	private String codigoTipoDocumento;
	private String descripcion;
	
	//CONSTRUCTOR SIN PARAMETROS
	public TipoDocumento() {
	}
	//CONSTRUCTOR CON PARAMETROS	
	public TipoDocumento(String codigoTipoDocumento, String descripcion) {
		super();
		this.codigoTipoDocumento = codigoTipoDocumento;
		this.descripcion = descripcion;
	}
	
	//METODOS GET Y SET
	public String getDescripcion() {
		return descripcion;
	}
	public String getCodigoTipoDocumento() {
		return codigoTipoDocumento;
	}
	public void setCodigoTipoDocumento(String codigoTipoDocumento) {
		this.codigoTipoDocumento = codigoTipoDocumento;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	//METODO TO STRING PARA MODIFICAR
	@Override
	public String toString() {
		return "TipoDocumento [codigoTipoDocumento=" + codigoTipoDocumento + ", descripcion=" + descripcion + "]";
	}
	
	
	
	
}
