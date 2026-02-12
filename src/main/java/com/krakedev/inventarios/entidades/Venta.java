package com.krakedev.inventarios.entidades;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Venta {
	private int codigoVenta;
	private  Timestamp fechaHora;
	private BigDecimal totalSinIVA;
	private BigDecimal IVA;
	private BigDecimal total;
	private ArrayList<DetalleVenta> detalles;
	
	//CONSTRUCTOR SIN PARAMETROS
	public Venta() {
	}
	
	//CONSTRUCTOR CON PARAMETROS
	public Venta(int codigoVenta, Timestamp fechaHora, BigDecimal totalSinIVA, BigDecimal iVA, BigDecimal total) {
		super();
		this.codigoVenta = codigoVenta;
		this.fechaHora = fechaHora;
		this.totalSinIVA = totalSinIVA;
		IVA = iVA;
		this.total = total;
	}
	
	public Venta(int codigoVenta, Timestamp fechaHora, BigDecimal totalSinIVA, BigDecimal iVA, BigDecimal total,
			ArrayList<DetalleVenta> detalles) {
		super();
		this.codigoVenta = codigoVenta;
		this.fechaHora = fechaHora;
		this.totalSinIVA = totalSinIVA;
		IVA = iVA;
		this.total = total;
		this.detalles = detalles;
	}

	//METODOS GET Y SET
	public int getCodigoVenta() {
		return codigoVenta;
	}

	public void setCodigoVenta(int codigoVenta) {
		this.codigoVenta = codigoVenta;
	}

	public Timestamp getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Timestamp fechaHora) {
		this.fechaHora = fechaHora;
	}

	public BigDecimal getTotalSinIVA() {
		return totalSinIVA;
	}

	public void setTotalSinIVA(BigDecimal totalSinIVA) {
		this.totalSinIVA = totalSinIVA;
	}

	public BigDecimal getIVA() {
		return IVA;
	}

	public void setIVA(BigDecimal iVA) {
		IVA = iVA;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	public ArrayList<DetalleVenta> getDetalles() {
		return detalles;
	}

	public void setDetalles(ArrayList<DetalleVenta> detalles) {
		this.detalles = detalles;
	}
	//METODO TO STRING
	@Override
	public String toString() {
		return "Venta [codigoVenta=" + codigoVenta + ", fechaHora=" + fechaHora + ", totalSinIVA=" + totalSinIVA
				+ ", IVA=" + IVA + ", total=" + total + ", detalles=" + detalles + "]";
	}
	
	
	
	
}
