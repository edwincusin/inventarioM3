package com.krakedev.inventarios.bdd;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import com.krakedev.inventarios.entidades.DetallePedido;
import com.krakedev.inventarios.entidades.Pedido;
import com.krakedev.inventarios.excepciones.KrakeDevException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class PedidosBDD {

	//METODO PARA INSERTAR PEDIDO
		public void insertar(Pedido pedido) throws KrakeDevException{
			Connection con=null;
			PreparedStatement ps=null;
			PreparedStatement psDet=null;
			ResultSet rsClave=null;
			int codigoCabecera=0;
			
			//CODIGO PAA TOMAR LA FECHA DEL SISTEMA O FECHA ACTUAL
			Date fechaActual =new Date();
			java.sql.Date fechaSQL=new java.sql.Date(fechaActual.getTime());
			
			//FIN CODIGO FECHA
			
			try {
				con=ConexionBDD.conectar();
				String consultaSQL="insert into cabecera_pedido(proveedor, fecha, estado)\r\n"
						+ " values	(?,?,?)";
				ps=con.prepareStatement(consultaSQL,Statement.RETURN_GENERATED_KEYS);//capturo retorno del codigo como llave primaria o lo que gernero como llave primaria
				
				ps.setString(1, pedido.getProveedor().getIdentificador());
				ps.setDate(2, fechaSQL);
				ps.setString(3, "S"); // por defecto va S por que va como SOLICITADO
								
				ps.executeUpdate();
				
				//recupero lo que capturo en el retorno - codigo geenrado de la cabecera
				rsClave=ps.getGeneratedKeys();
				if(rsClave.next()) {
					codigoCabecera=rsClave.getInt(1);
				}
				System.out.println("CODIGO GENERADO>>>>>> "+codigoCabecera);
				
				ArrayList<DetallePedido> detallesPedidos=pedido.getDetalles();
				DetallePedido det;
				for(int i =0;i<detallesPedidos.size();i++) {
					det=detallesPedidos.get(i);
					String consultaSQL2="insert into detalle_pedido (cabecera_pedido, producto, cantidad_solicitada, cantidad_recibida, subtotal)\r\n"
							+ " values 	(?,?,?,?,?);";
					psDet=con.prepareStatement(consultaSQL2);
					
					psDet.setInt(1, codigoCabecera);
					psDet.setInt(2, det.getProducto().getCodigo());
					psDet.setInt(3, det.getCantidadSolicitada());
					psDet.setInt(4, 0); // por defecto va 0 al realizar el pedido
					
					//para calcular el subtotal
					BigDecimal pv=det.getProducto().getPrecioVenta();
					BigDecimal cantidad=new BigDecimal(det.getCantidadSolicitada());
					BigDecimal subTotal=pv.multiply(cantidad);
					
					psDet.setBigDecimal(5, subTotal);
					
					psDet.executeUpdate();
					
				}
				
							
			} catch (KrakeDevException e) {
				throw e;
			} catch (SQLException e) {
				throw new KrakeDevException("ERROR AL REALIZAR LA CONSULTA SQL INSTERTAR CABECERA PEDIDO"+e);
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
