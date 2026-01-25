package com.krakedev.inventarios.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.krakedev.inventarios.excepciones.KrakeDevException;

public class ConexionBDD {

		
	//METODO PARA OBTENER CONEXION CON EL DATA SOURCE - USANDO TOMCAT
	public static Connection conectar() throws KrakeDevException {
		Context ctx = null;
		DataSource ds = null;
		Connection con = null;
		try {
			ctx = new InitialContext();
			// JDNI--> ES PARA PODER ELEMENTOS DENTRO DEL SERVIDOR
			ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/ConexionInventario");
			con = ds.getConnection();
		} catch (NamingException | SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("ERROR DE CONEXION");
		}
		return con;
	}
	

}
