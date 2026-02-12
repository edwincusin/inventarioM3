package com.krakedev.inventarios.excepciones;

public class KrakeDevException  extends Exception{

	private static final long serialVersionUID = 1L; // PIDE ECLIPE QUE LO AÑADAMOS PARA QUE NO SALGA ERROR
	public KrakeDevException(String mensaje) {
		super(mensaje);
	}
	
}
