package com.krakedev.inventarios.servicios;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.krakedev.inventarios.bdd.CategoriaProductoBDD;
import com.krakedev.inventarios.entidades.CategoriaProducto;
import com.krakedev.inventarios.excepciones.KrakeDevException;

@Path("categorias")
public class ServiciosCategoriaProducto {
	
	//SERVICIO PARA INSERTAR O CREAR UNA CATEGORIA EN LA TABLA - catgegorias
	@Path("crear")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response crear(CategoriaProducto categoria) {
		System.out.println("INSERTANDO CATEGORIA >>>>");
		CategoriaProductoBDD categoriaBDD=new CategoriaProductoBDD();
		
		try {
			categoriaBDD.crear(categoria);
			return Response.ok().build(); // devuelve un estatus 200
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.serverError().build(); // si algo falla devuelve 500
		}
	}
	
	//SERVICIO PARA  ACTUALIZAR O MODIFICAR UNA CATEGORIA PARA PRODCUTOS EN LA BDD - TABLA categorias
	@Path("actualizar")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response actualizar(CategoriaProducto categoria) {
		System.out.println("INSERTANDO CATEGORIA >>>>");
		CategoriaProductoBDD categoriaBDD=new CategoriaProductoBDD();
		
		try {
			categoriaBDD.actualizar(categoria);
			return Response.ok().build(); // devuelve un estatus 200
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.serverError().build(); // si algo falla devuelve 500
		}
	}
	
}
