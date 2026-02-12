package com.krakedev.inventarios.bdd;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.krakedev.inventarios.entidades.DetalleVenta;
import com.krakedev.inventarios.entidades.Venta;
import com.krakedev.inventarios.excepciones.KrakeDevException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class VentasBDD {
	
	//METODO PARA INSERTAR VENTA INCLUYENDO SUS DETALLES DE VENTA ES DECIR PRODUCTOS
	public void registrarVenta(Venta venta) throws KrakeDevException{
		Connection con=null;
		PreparedStatement ps=null; // PARA CONSULTAS DE CABECERA VENTAS
		PreparedStatement psDet=null; // para consultas de DETALLE VENTAS
		ResultSet rsClave=null; // PARA GUARDAR LA PK DE LA CABECERA VENTA
		int codigoCabecera=0; // PARA RECUPERAR LA PK DE LA CABECERA CAPTURADA CON Statement.RETURN_GENERATED_KEYS
		
		//CODIGO PAA TOMAR LA FECHA DEL SISTEMA O FECHA ACTUAL
		Date fechaActual =new Date();//obtiene la fecha y hora actual del sistema
		Timestamp fechaHora=new Timestamp(fechaActual.getTime()); // solo guarda la fecha con datos de horas y minutos mas segundos
		
		//FIN CODIGO FECHA
		
		try {
			con=ConexionBDD.conectar();
			String consultaSQL="INSERT INTO public.cabecera_ventas(\r\n"
					+ "	fecha, total_sin_iva, iva, total)\r\n"
					+ "	VALUES (?, ?, ?, ?);";
			
			ps=con.prepareStatement(consultaSQL,Statement.RETURN_GENERATED_KEYS);//capturo retorno del codigocABECERA como llave primaria o lo que gernero como llave primaria
			
			ps.setTimestamp(1,fechaHora);
			ps.setInt(2, 0); // POR DEFECTO SE CREA CON VALOR 0	
			ps.setInt(3, 0); // POR DEFECTO SE CREA CON VALOR 0	
			ps.setInt(4, 0); // POR DEFECTO SE CREA CON VALOR 0	
			
			ps.executeUpdate();

			//----------------------------------------------------------------------------
			rsClave=ps.getGeneratedKeys(); //recupero lo que capturo en el retorno - codigo geenrado de la cabeceravENTA
			if(rsClave.next()) {
				codigoCabecera=rsClave.getInt(1);
			}
			System.out.println("CODIGO GENERADO CEBECERA VENTA>>>>>> "+codigoCabecera);
			
			ArrayList<DetalleVenta> detallesVenta=venta.getDetalles();
			DetalleVenta detalle;
			PreparedStatement psHist=null;
			BigDecimal porcentajeIVA=new BigDecimal("0.12"); // convierto el string en bigdecimal 	
			//variables para guardar datos para actucalizar cabecera
			BigDecimal sumaTotalSinIva=BigDecimal.valueOf(0.0);
			BigDecimal totalVenta=BigDecimal.valueOf(0.0);
			BigDecimal sumaSoloIVA=BigDecimal.ZERO; // otra forma de inicializar en 0
			
			for(int i =0;i<detallesVenta.size();i++) {
				detalle=detallesVenta.get(i); //CAPTURO DETALLE SEGUN EL INDEX RECORRIDO
				String consultaSQL2Det="INSERT INTO public.detalle_ventas(\r\n"
						+ "	cabecera_ventas, producto, cantidad, precio_venta, subtotal, total_con_iva)\r\n"
						+ "	VALUES (?, ?, ?, ?, ?, ?);";
				psDet=con.prepareStatement(consultaSQL2Det);
				
				psDet.setInt(1, codigoCabecera);
				psDet.setInt(2, detalle.getProducto().getCodigo());
				psDet.setInt(3, detalle.getCantidad());
				psDet.setBigDecimal(4, detalle.getProducto().getPrecioVenta());
				
				//para calcular el subtotal con iva y sin iva
				BigDecimal pv=detalle.getProducto().getPrecioVenta();
				BigDecimal cantidad=new BigDecimal(detalle.getCantidad());							
				BigDecimal subTotalSinIVA=pv.multiply(cantidad); // SUBTOTAL SIN IVA
				psDet.setBigDecimal(5,subTotalSinIVA);
				
				BigDecimal subTotalConIVA=subTotalSinIVA.add(subTotalSinIVA.multiply(porcentajeIVA)); // SUBTOTAL CON IVA
				BigDecimal totalDetalle=  BigDecimal.valueOf(0.0);//inicializo variable total en 0, si existe error sera 0
				
				sumaTotalSinIva=sumaTotalSinIva.add(subTotalSinIVA); // operacion para acumular la suma del subtotal sin iva-- > valor que enviamos a la cabecera
				
				//validacion de si producto tiene iva
				if(detalle.getProducto().isTieneIva()==true) {//validamos si producto tiene iva
					totalDetalle=subTotalConIVA;
					sumaSoloIVA=sumaSoloIVA.add(subTotalSinIVA.multiply(porcentajeIVA)); // operacion para acumular solo la suma del IVA --> para enviar a la cabecera
				}else {
					totalDetalle=subTotalSinIVA;
				}				
				psDet.setBigDecimal(6,totalDetalle); //envia el total sea el calculo con iva  o sin iva
				totalVenta=totalVenta.add(totalDetalle);//operacion para acumular la suma del total general --> valor para enviar a la cabeceras
				psDet.executeUpdate();				
				
				System.out.println("DETALLE VENTA REGISTRADO >>>>>>"+i+" "+detalle);
				
				
				//CODIGO PARA INSERTAR EN LA TABLA HISTORIAL			
				
				String consultaSQL3Hist="INSERT INTO public.historial_stock(fecha, referencia, producto, cantidad)"
						+ "	VALUES (?, ?, ?, ?);";
				psHist=con.prepareStatement(consultaSQL3Hist);
				
				psHist.setTimestamp(1, fechaHora);
				psHist.setString(2, "VENTA "+codigoCabecera);
				psHist.setInt(3, detalle.getProducto().getCodigo());
				psHist.setInt(4, -detalle.getCantidad());
				psHist.executeUpdate();
											
				System.out.println("se agrega un pedido al historial: "+psHist);
			}
			
			//-------------ACTUALIZAR LA CABECERA CON LOS DATOS OBTENIDOS EN LOS CALCULOS
			PreparedStatement psActualizarCabecera=null;
			String actualizarCabeceraSQL="UPDATE public.cabecera_ventas\r\n"
					+ "	SET  total_sin_iva=?, iva=?, total=?\r\n"
					+ "	WHERE codigo_cabecera_venta=?;";
			psActualizarCabecera=con.prepareStatement(actualizarCabeceraSQL);
			psActualizarCabecera.setBigDecimal(1, sumaTotalSinIva);
			psActualizarCabecera.setBigDecimal(2, sumaSoloIVA);
			psActualizarCabecera.setBigDecimal(3, totalVenta);
			psActualizarCabecera.setInt(4, codigoCabecera);
			
			psActualizarCabecera.executeUpdate();
			System.out.println("CABECERA ACTUALIZADA CON LOS CALCULOS>>>>>> "+codigoCabecera);
			
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
