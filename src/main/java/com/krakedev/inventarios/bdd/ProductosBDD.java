package com.krakedev.inventarios.bdd;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.krakedev.inventarios.entidades.CategoriaProducto;
import com.krakedev.inventarios.entidades.Producto;
import com.krakedev.inventarios.entidades.UnidadDeMedida;
import com.krakedev.inventarios.excepciones.KrakeDevException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class ProductosBDD {
	
	//METODO PARA RECUPERAR PRODUCTOS MEDIANTE UNA SUBCADENA EJEMP: INGRESO CARACTER al me retorna todos los PRODUCTOS con esas similitudes.
	public ArrayList<Producto> buscar(String subCadena) throws KrakeDevException{
		ArrayList<Producto> productos=new ArrayList<Producto>();
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try {
			con=ConexionBDD.conectar();
			String consultaSQL="select prod.codigo_prod, \r\n"
					+ "	   prod.nombre as nombre_producto,\r\n"
					+ "	   udm.nombre as nombre_UDM,\r\n"
					+ "	   udm.descripcion as descripcion_udm,\r\n"
					+ "	   cast(prod.precio_venta as decimal(6,2)),\r\n"
					+ "	   prod.tiene_iva,\r\n"
					+ "	   cast(prod.coste as decimal(5,4)),\r\n"
					+ "	   prod.categoria,\r\n"
					+ "	   cat.nombre as nombre_categoria,\r\n"
					+ "	   prod.stock\r\n"
					+ "from productos as prod, unidades_medida as udm, categorias as cat\r\n"
					+ "where prod.udm = udm.nombre\r\n"
					+ "and prod.categoria = cat.codigo_cat\r\n"
					+ "and upper(prod.nombre) like ?;";
			ps=con.prepareStatement(consultaSQL);
			
			ps.setString(1, "%"+subCadena.toUpperCase()+"%");	
			rs=ps.executeQuery();
			
			while(rs.next()){
				int codigoProducto=rs.getInt("codigo_prod");
				String nombreProducto=rs.getString("nombre_producto");
				BigDecimal precioVenta=rs.getBigDecimal("precio_venta");
				boolean tieneIva=rs.getBoolean("tiene_iva");
				BigDecimal coste=rs.getBigDecimal("coste");
				int stock = rs.getInt("stock");
									
				
				String nombreUDM=rs.getString("nombre_UDM"); //es el codigo o clave primaria de udm
				String descripcionUDM=rs.getString("descripcion_udm");
				
				UnidadDeMedida udm=new UnidadDeMedida(nombreUDM, descripcionUDM, null);
								
				int codigoCategoria = rs.getInt("categoria");
				String nombreCategoria = rs.getString("nombre_categoria");
				
				CategoriaProducto categoriaProducto=new CategoriaProducto(codigoCategoria, nombreCategoria, null);
				
				Producto producto= new Producto();
				producto.setCodigo(codigoProducto);
				producto.setNombre(nombreProducto);
				producto.setUnidadMedida(udm);
				producto.setPrecioVenta(precioVenta);
				producto.setTieneIva(tieneIva);
				producto.setCoste(coste);
				producto.setCategoria(categoriaProducto);
				producto.setStock(stock);
				
				productos.add(producto);
				
			}
						
		} catch (KrakeDevException e) {
			throw e;
		} catch (SQLException e) {
			throw new KrakeDevException("ERROR AL REALIZAR LA CONSULTA SQL RECUPERAR PRODUCTOS "+e);
		}finally {
			try {
				if (con != null) {
				    con.close();
				}
			} catch (SQLException e) {
				throw new KrakeDevException("ERROR AL REALZIAR CIERRE DE BDD"+e);
			}
		}
		return productos;
	}
	
	
	//METODO PARA CREAR NUEVO PRODUCTO EN LA BASE DE DATOS
		public void crear(Producto producto) throws KrakeDevException{
			Connection con=null;
			PreparedStatement ps=null;
			
			try {
				con=ConexionBDD.conectar();
				String consultaSQL="INSERT INTO public.productos(\r\n"
						+ "	nombre, udm, precio_venta, tiene_iva, coste, categoria, stock)\r\n"
						+ "	VALUES (?, ?, ?, ?, ?, ?, ?);";
				ps=con.prepareStatement(consultaSQL);
				
				ps.setString(1, producto.getNombre());
				ps.setString(2,producto.getUnidadMedida().getNombre());
				ps.setBigDecimal(3, producto.getPrecioVenta());
				ps.setBoolean(4, producto.isTieneIva());
				ps.setBigDecimal(5, producto.getCoste());
				ps.setInt(6, producto.getCategoria().getCodigo());
				ps.setInt(7, producto.getStock());
				
				ps.executeUpdate();					
							
			} catch (KrakeDevException e) {
				throw e;
			} catch (SQLException e) {
				throw new KrakeDevException("ERROR AL REALIZAR LA CONSULTA SQL CREAR PRODUCTOS "+e);
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
		
		//METODO PARA MODIFICAR O ACTUALIZAR TODOS LOS CAMPOS
		public void actualizar(Producto producto) throws KrakeDevException{
			Connection con=null;
			PreparedStatement ps=null;
			
			try {
				con=ConexionBDD.conectar();
				String consultaSQL="UPDATE public.productos\r\n"
						+ "	SET nombre=?, udm=?, precio_venta=?, tiene_iva=?, coste=?, categoria=?, stock=?"
						+ "	WHERE codigo_prod=?;";
				ps=con.prepareStatement(consultaSQL);
				
				ps.setString(1, producto.getNombre());
				ps.setString(2,producto.getUnidadMedida().getNombre());
				ps.setBigDecimal(3, producto.getPrecioVenta());
				ps.setBoolean(4, producto.isTieneIva());
				ps.setBigDecimal(5, producto.getCoste());
				ps.setInt(6, producto.getCategoria().getCodigo());
				ps.setInt(7, producto.getStock());
				ps.setInt(8, producto.getCodigo());
				
				ps.executeUpdate();	
				
				System.out.println("Prouducto actualizado "+ps);
							
			} catch (KrakeDevException e) {
				throw e;
			} catch (SQLException e) {
				throw new KrakeDevException("ERROR AL REALIZAR LA CONSULTA SQL ACTUALIZAR PRODUCTO "+e);
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
