package com.krakedev.inventarios.bdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.krakedev.inventarios.entidades.TipoDocumento;
import com.krakedev.inventarios.excepciones.KrakeDevException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class TipoDocumentoBDD {
	public ArrayList<TipoDocumento> recuperar() throws KrakeDevException{
		ArrayList<TipoDocumento> tiposDocumentos=new ArrayList<TipoDocumento>();
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try {
			con=ConexionBDD.conectar();
			String consultaSQL="SELECT codigo_tipo_doc, descripcion\r\n"
					+ "	FROM public.tipo_documento;";
			ps=con.prepareStatement(consultaSQL);
			rs=ps.executeQuery();
			
			while(rs.next()){
				
				String codigoTipoDocumento=rs.getString("codigo_tipo_doc");
				String descripcion=rs.getString("descripcion");
				

				TipoDocumento tipoDocumento=new TipoDocumento(codigoTipoDocumento, descripcion);
				
				tiposDocumentos.add(tipoDocumento);		
			}
			
			
		} catch (KrakeDevException e) {
			throw e;
		} catch (SQLException e) {
			throw new KrakeDevException("ERROR AL REALIZAR LA CONSULTA SQL mostrarLibros "+e);
		}finally {
			try {
				if (con != null) {
				    con.close();
				}
			} catch (SQLException e) {
				throw new KrakeDevException("ERROR AL REALZIAR CIERRE DE BDD"+e);
			}
		}
		return tiposDocumentos;
	}
}
