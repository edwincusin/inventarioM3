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

import com.krakedev.inventarios.bdd.ProveedoresBDD;
import com.krakedev.inventarios.entidades.Proveedor;
import com.krakedev.inventarios.excepciones.KrakeDevException;

@Path("proveedores")
public class ServiciosProveedores {

	// SERVICIO PARA RECUPERAR PROVEEDOR
	@Path("buscar/{subCadena}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscar(@PathParam("subCadena") String SubCadena){
		System.out.println("CONSULTANDO PROVEEDORES>>>>");
		ProveedoresBDD provBDD= new ProveedoresBDD();
		ArrayList<Proveedor> proveedores =null;
		
		try {
			proveedores = provBDD.buscar(SubCadena);
			return Response.ok(proveedores).build();  //devuelve un estatus 200 
			
			
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.serverError().build();  // si algo falla devuelve 500
		}
	}
	
	//SERVICIO PARA CREAR PROVEEDOR
	@Path("crear")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response crear(Proveedor proveedor){
		System.out.println("INSERTANDO PROVEEDORES>>>>");
		ProveedoresBDD provBDD= new ProveedoresBDD();
		
		try {
			provBDD.crear(proveedor);
			return Response.ok().build();  //devuelve un estatus 200 
			
			
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.serverError().build();  // si algo falla devuelve 500
		}
	}
	
	// SERVICIO PARA RECUPERAR PROVEEDOR MEDIANTE IDENTIFICADOR
	@Path("recuperar/{identificador}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response recuperar(@PathParam("identificador") String identificador){
		System.out.println("CONSULTANDO PROVEEDOR>>>>");
		ProveedoresBDD provBDD= new ProveedoresBDD();
		Proveedor proveedor =null;
		
		try {
			proveedor = provBDD.recuperarProveedor(identificador);
			if(proveedor==null) {
				return Response.ok("No existe proveedor con este identificador "+identificador).build();  //devuelve un estatus 200 
			}else {
				return Response.ok(proveedor).build();  //devuelve un estatus 200 
			}	
			
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.serverError().build();  // si algo falla devuelve 500
		}
	}
	
	
}
