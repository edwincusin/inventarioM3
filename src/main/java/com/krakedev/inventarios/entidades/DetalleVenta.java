package com.krakedev.inventarios.entidades;

import java.math.BigDecimal;

public class DetalleVenta {
	private int codigoDetalle;
	private Venta venta;
	private Producto producto; 
	private int cantidad;
	private BigDecimal precioVenta;
	private BigDecimal subtotal;
	private BigDecimal totalConIVA;
	//CONSTRUCTOR VACIO
	public DetalleVenta() {
	}
	//CONSTRUCTOR CON PARAMETROS
	public DetalleVenta(int codigoDetalle, Venta venta, Producto producto, int cantidad, BigDecimal precioVenta,
			BigDecimal subtotal, BigDecimal totalConIVA) {
		super();
		this.codigoDetalle = codigoDetalle;
		this.venta = venta;
		this.producto = producto;
		this.cantidad = cantidad;
		this.precioVenta = precioVenta;
		this.subtotal = subtotal;
		this.totalConIVA = totalConIVA;
	}
	// METODOS GET Y SET
	public int getCodigoDetalle() {
		return codigoDetalle;
	}
	public void setCodigoDetalle(int codigoDetalle) {
		this.codigoDetalle = codigoDetalle;
	}
	public Venta getVenta() {
		return venta;
	}
	public void setVenta(Venta venta) {
		this.venta = venta;
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
	public BigDecimal getPrecioVenta() {
		return precioVenta;
	}
	public void setPrecioVenta(BigDecimal precioVenta) {
		this.precioVenta = precioVenta;
	}
	public BigDecimal getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}
	public BigDecimal getTotalConIVA() {
		return totalConIVA;
	}
	public void setTotalConIVA(BigDecimal totalConIVA) {
		this.totalConIVA = totalConIVA;
	}
	
	//METODO TO STRING
	@Override
	public String toString() {
		return "DetalleVenta [codigoDetalle=" + codigoDetalle + ", venta=" + venta + ", producto=" + producto
				+ ", cantidad=" + cantidad + ", precioVenta=" + precioVenta + ", subtotal=" + subtotal
				+ ", totalConIVA=" + totalConIVA + "]";
	}
	
}
