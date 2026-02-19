package com.krakedev.inventarios.bdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.krakedev.inventarios.entidades.CategoriaProducto;
import com.krakedev.inventarios.excepciones.KrakeDevException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class CategoriaProductoBDD {

	//METODO PARA CREAR O INSERTAR UNA CATEGORIA PARA PRODCUTOS EN LA BDD - TABLA categorias
		public void crear(CategoriaProducto categoriaProducto) throws KrakeDevException{
			Connection con=null;
			PreparedStatement ps=null;
			
			try {
				con=ConexionBDD.conectar();
				String consultaSQL="INSERT INTO public.categorias(\r\n"
						+ "	nombre, categoria_padre)\r\n"
						+ "	VALUES (?, ?);";
				ps=con.prepareStatement(consultaSQL);
				ps.setString(1, categoriaProducto.getNombre());
				
				//CONTROLAMOS EL NULLPOINTER 
				if(categoriaProducto.getCategoriaPadre()!=null) {
					ps.setInt(2,categoriaProducto.getCategoriaPadre().getCodigo());
				}else {
					ps.setNull(2, java.sql.Types.INTEGER);//AGREGAMOS NULL EN EL TIPO DE UN NUMERO ENTERO
				}
					
				ps.executeUpdate();					
							
			} catch (KrakeDevException e) {
				throw e;
			} catch (SQLException e) {
				throw new KrakeDevException("ERROR AL REALIZAR LA CONSULTA SQL CREAR||INSERTAT CATEGORIA"+e);
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
		
		
		//METODO PARA ACTUALIZAR O MODIFICAR UNA CATEGORIA PARA PRODCUTOS EN LA BDD - TABLA categorias
		public void actualizar(CategoriaProducto categoriaProducto) throws KrakeDevException{
			Connection con=null;
			PreparedStatement ps=null;
			
			try {
				con=ConexionBDD.conectar();
				String consultaSQL="UPDATE public.categorias\r\n"
						+ "	SET nombre=?, categoria_padre=?\r\n"
						+ "	WHERE codigo_cat=?;";
				ps=con.prepareStatement(consultaSQL);
				ps.setString(1, categoriaProducto.getNombre());
				
				//CONTROLAMOS EL NULLPOINTER 
				if(categoriaProducto.getCategoriaPadre()!=null) {
					ps.setInt(2,categoriaProducto.getCategoriaPadre().getCodigo());
				}else {
					ps.setNull(2, java.sql.Types.INTEGER);//AGREGAMOS NULL EN EL TIPO DE UN NUMERO ENTERO
				}
				
				ps.setInt(3, categoriaProducto.getCodigo());
					
				ps.executeUpdate();					
							
			} catch (KrakeDevException e) {
				throw e;
			} catch (SQLException e) {
				throw new KrakeDevException("ERROR AL REALIZAR LA CONSULTA SQL MODIFICAR O ACTUALIZAR||MODIFICAR CATEGORIA"+e);
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
