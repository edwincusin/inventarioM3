package com.krakedev.inventarios.servicios;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.krakedev.inventarios.bdd.PedidosBDD;
import com.krakedev.inventarios.entidades.Pedido;
import com.krakedev.inventarios.excepciones.KrakeDevException;

@Path("pedidos")
public class ServiciosPedidos {
	
	//SERVICIO PARA INSERTAR CABECERA PEDIDO Y SU DETALLES EN ARRAY
	@Path("registrar")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registrar(Pedido pedido) {
		System.out.println("REGISTRANDO PEDIDO >>>>");
		PedidosBDD pedidosBDD= new PedidosBDD();

		try {
			pedidosBDD.insertar(pedido);
			return Response.ok().build(); // devuelve un estatus 200

		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.serverError().build(); // si algo falla devuelve 500
		}
	}
	
	//SERVICIO PARA MODIFICAR EL ESTADO DE SOLICITUD DE PEDIDO / ADEMAS  CUANTO RECIBICIO Y MODIFICAR LOS SUBTOTALES
	@Path("recibir")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response recibir(Pedido pedido) {
		System.out.println("REGISTRANDO PEDIDO >>>>");
		PedidosBDD pedidosBDD= new PedidosBDD();

		try {
			pedidosBDD.actualizarRecibido(pedido);
			return Response.ok().build(); // devuelve un estatus 200

		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.serverError().build(); // si algo falla devuelve 500
		}
	}
	
}
