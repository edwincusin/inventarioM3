package com.krakedev.inventarios.servicios;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.krakedev.inventarios.bdd.TipoDocumentoBDD;
import com.krakedev.inventarios.entidades.TipoDocumento;
import com.krakedev.inventarios.excepciones.KrakeDevException;

@Path("tiposdocumento")
public class ServiciosTiposDocumentos {

	// SERVICIO PARA RECUPERAR TODOS LOS TIPOS DE DOCUMENTOS
		@Path("recuperar")
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		public Response recuperar(){
			System.out.println("CONSULTANDO TIPO DOCUMENTOS>>>>");
			TipoDocumentoBDD tipoDoc= new TipoDocumentoBDD();
			ArrayList<TipoDocumento> tiposDocumentos =null;
			
			try {
				tiposDocumentos = tipoDoc.recuperar();
				return Response.ok(tiposDocumentos).build();  //devuelve un estatus 200 
				
				
			} catch (KrakeDevException e) {
				e.printStackTrace();
				return Response.serverError().build();  // si algo falla devuelve 500
			}
		}
}
