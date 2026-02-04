package com.krakedev.inventarios.servicios;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.krakedev.inventarios.bdd.ProductosBDD;
import com.krakedev.inventarios.entidades.Producto;
import com.krakedev.inventarios.excepciones.KrakeDevException;

@Path("productos")
public class ServiciosProductos {

	// SERVICIO PARA RECUPERAR PRODCUTOS POR SUBCADENA
	@Path("buscar/{subCadena}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscar(@PathParam("subCadena") String SubCadena) {
		System.out.println("CONSULTANDO PRODUCTOS>>>>");
		ProductosBDD prodBDD = new ProductosBDD();
		ArrayList<Producto> productos = null;

		try {
			productos = prodBDD.buscar(SubCadena);
			return Response.ok(productos).build(); // devuelve un estatus 200

		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.serverError().build(); // si algo falla devuelve 500
		}
	}
	
	// SERVICIO PARA CREAR PRODUCTO
	@Path("crear")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response crear(Producto producto) {
		System.out.println("CREANDO PRODUCTO >>>>");
		ProductosBDD prodBDD = new ProductosBDD();

		try {
			prodBDD.crear(producto);
			return Response.ok().build(); // devuelve un estatus 200

		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.serverError().build(); // si algo falla devuelve 500
		}
	}

}
