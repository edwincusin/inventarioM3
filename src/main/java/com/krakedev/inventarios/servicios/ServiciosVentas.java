package com.krakedev.inventarios.servicios;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.krakedev.inventarios.bdd.VentasBDD;
import com.krakedev.inventarios.entidades.Venta;
import com.krakedev.inventarios.excepciones.KrakeDevException;
@Path("ventas")
public class ServiciosVentas {
	//SERVICIO PARA INSERTAR CABECERA VENTA Y SU DETALLES EN ARRAY
	@Path("guardar")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registrarVenta(Venta venta) {
		System.out.println("REGISTRANDO PEDIDO >>>>");
		VentasBDD ventasBDD= new VentasBDD();

		try {
			ventasBDD.registrarVenta(venta);
			return Response.ok().build(); // devuelve un estatus 200

		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.serverError().build(); // si algo falla devuelve 500
		}
	}
}
