package com.krakedev.inventarios.bdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.krakedev.inventarios.entidades.Proveedor;
import com.krakedev.inventarios.entidades.TipoDocumento;
import com.krakedev.inventarios.excepciones.KrakeDevException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class ProveedoresBDD {
	
	//METODO PARA RECUPERAR PROVEEDORES MEDIANTE UNA SUBCADENA EJEMP: INGRESO CARACTER al me retorna todos los proveedores con esas similitudes.
	public ArrayList<Proveedor> buscar(String subCadena) throws KrakeDevException{
		ArrayList<Proveedor> proveedores=new ArrayList<Proveedor>();
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try {
			con=ConexionBDD.conectar();
			String consultaSQL="SELECT identificador, tipo_documento, tipo_documento.descripcion AS descripcion_tipo_doc, nombre, telefono, correo, direccion"
					+ " FROM proveedor, tipo_documento"
					+ " WHERE proveedor.tipo_documento = tipo_documento.codigo_tipo_doc"
					+ " AND upper(nombre) like ?";
			ps=con.prepareStatement(consultaSQL);
			
			ps.setString(1, "%"+subCadena.toUpperCase()+"%");	
			rs=ps.executeQuery();
			
			while(rs.next()){
				
				String identificador=rs.getString("identificador");
				String codigoTipoDocumento=rs.getString("tipo_documento");
				String descripcionTipoDocumento=rs.getString("descripcion_tipo_doc");
				String nombre=rs.getString("nombre");
				String telefono=rs.getString("telefono");
				String correo=rs.getString("correo");
				String direccion=rs.getString("direccion");

				TipoDocumento td= new TipoDocumento(codigoTipoDocumento, descripcionTipoDocumento);
				Proveedor proveedor=new Proveedor(identificador, td, nombre, telefono, correo, direccion);
				
				proveedores.add(proveedor);		
			}
						
		} catch (KrakeDevException e) {
			throw e;
		} catch (SQLException e) {
			throw new KrakeDevException("ERROR AL REALIZAR LA CONSULTA SQL RECUPERAR PROVEEDORES "+e);
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
	
	//METODO PARA CREAR Y GUARDAR UN NUEVO PROVEEDOR
		public void crear(Proveedor proveedor) throws KrakeDevException{
			Connection con=null;
			PreparedStatement ps=null;
			//ResultSet rs=null;
			
			try {
				con=ConexionBDD.conectar();
				String consultaSQL="INSERT INTO public.proveedor(\r\n"
						+ "	identificador, tipo_documento, nombre, telefono, correo, direccion)\r\n"
						+ "	VALUES (?, ?, ?, ?, ?, ?);";
				ps=con.prepareStatement(consultaSQL);
				
				ps.setString(1,proveedor.getIdentificador());
				ps.setString(2, proveedor.getTipoDocumento().getCodigoTipoDocumento());
				ps.setString(3, proveedor.getNombre());
				ps.setString(4, proveedor.getTelefono());
				ps.setString(5, proveedor.getCorreo());
				ps.setString(6, proveedor.getDireccion());
				
				ps.executeUpdate();				
							
			} catch (KrakeDevException e) {
				throw e;
			} catch (SQLException e) {
				throw new KrakeDevException("ERROR AL REALIZAR LA CONSULTA SQL GUARDAR NUEVO PROVEEDOR "+e);
			}finally {
				try {
					if (con != null) {
					    con.close();
					}
				} catch (SQLException e) {
					throw new KrakeDevException("ERROR AL REALZIAR CIERRE DE BDD"+e);
				}
			}
		}
}
