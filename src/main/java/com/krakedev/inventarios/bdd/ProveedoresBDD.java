package com.krakedev.inventarios.bdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.krakedev.inventarios.entidades.Proveedor;
import com.krakedev.inventarios.excepciones.KrakeDevException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class ProveedoresBDD {
	
	public ArrayList<Proveedor> buscar(String subCadena) throws KrakeDevException{
		ArrayList<Proveedor> proveedores=new ArrayList<Proveedor>();
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try {
			con=ConexionBDD.conectar();
			String consultaSQL="SELECT identificador, tipo_documento, nombre, telefono, correo, direccion"
					+ "	FROM public.proveedor"
					+ "	WHERE upper(nombre) like ?;";
			ps=con.prepareStatement(consultaSQL);
			
			ps.setString(1, "%"+subCadena.toUpperCase()+"%");	
			rs=ps.executeQuery();
			
			while(rs.next()){
				
				String identificador=rs.getString("identificador");
				String tipoDocumento=rs.getString("tipo_documento");
				String nombre=rs.getString("nombre");
				String telefono=rs.getString("telefono");
				String correo=rs.getString("correo");
				String direccion=rs.getString("direccion");

				Proveedor proveedor=new Proveedor(identificador, tipoDocumento, nombre, telefono, correo, direccion);
				
				proveedores.add(proveedor);		
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
		return proveedores;
	}
}
