package com.krakedev.inventarios.entidades;

import java.sql.Timestamp;

public class HistorialStock {
	private int codigoHistorial;
	private Timestamp fechaHora;
	private Producto producto;
	private int cantidad;
	
	//CONSTRUCTOR SIN PARAMETROS
	public HistorialStock() {
		
	}
	//CONSTRUCTOR CON PARAMETROS 
	public HistorialStock(int codigoHistorial, Timestamp fechaHora, Producto producto, int cantidad) {
		super();
		this.codigoHistorial = codigoHistorial;
		this.fechaHora = fechaHora;
		this.producto = producto;
		this.cantidad = cantidad;
	}
	//METODOS GET Y SET 
	public int getCodigoHistorial() {
		return codigoHistorial;
	}
	public void setCodigoHistorial(int codigoHistorial) {
		this.codigoHistorial = codigoHistorial;
	}
	public Timestamp getFechaHora() {
		return fechaHora;
	}
	public void setFechaHora(Timestamp fechaHora) {
		this.fechaHora = fechaHora;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	//METODO TOS STRING
	@Override
	public String toString() {
		return "HistorialStock [codigoHistorial=" + codigoHistorial + ", fechaHora=" + fechaHora + ", producto="
				+ producto + ", cantidad=" + cantidad + "]";
	}
	
	
	
}
