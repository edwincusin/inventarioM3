
--=============================================
--VALIDAR EXISTENCIA TABLAS ANTES DE CREAR----
--=============================================

drop table if exists historial_stock;

drop table if exists detalle_ventas;
drop table if exists cabecera_ventas;

drop table if exists detalle_pedido;
drop table if exists cabecera_pedido;
drop table if exists estado;

drop table if exists proveedor;
drop table if exists tipo_documento;

drop table if exists productos;
drop table if exists categorias;
drop table if exists unidades_medida;
drop table if exists categoria_unidad_medida;

--=============================================
--------------CREACION DE TABLAS---------------
--=============================================

--CATEGORIAS: obtiene las categorias para ser usado en productos.
create table categorias(
	codigo_cat serial not null,
	nombre varchar(100) not null,
	categoria_padre int,

	constraint categorias_pk primary key (codigo_cat),
	constraint categorias_fk 
	foreign key (categoria_padre)
	references categorias(codigo_cat)
);

--CATEGORIA_UNIDAD_MEDIDA: son las categorias de medidas para ser usado en la tabla unidades de medidas con su derivados.
create table categoria_unidad_medida(
	codigo_cat_udm char(1) not null,
	nombre varchar(100) not null,

	constraint categoria_unidad_medida_pk primary key (codigo_cat_udm)
);

--UNIDADES DE MEDIDAS: estas unidades de medida seran utilizadas desde la tabla producto, dependiendo cantidad y categoria del producto.
create table unidades_medida(
	nombre varchar(2) not null,
	descripcion varchar(100) not null,
	categoria_udm char(1) not null,

	constraint unidades_medida_pk primary key (nombre),
	constraint categoria_unidad_medida_fk
	foreign key (categoria_udm)
	references categoria_unidad_medida(codigo_cat_udm)
);

--PRODUCTO: almacena los productos, con sus categorias, y unidad de medida
create table productos(
	codigo_prod serial not null, 
	nombre varchar(100) not null,
	udm varchar(2) not null,
	precio_venta money not null,
	tiene_iva boolean not null,
	coste money not null,
	categoria int not null,
	stock int not null,

	constraint productos_pk primary key (codigo_prod),
	constraint unidades_medida_fk foreign key (udm) references unidades_medida(nombre),
	constraint categorias_fk foreign key (categoria) references categorias(codigo_cat)
);

--TIPO DOCUMENTO: almacena el codigo del tipo de documento el cual va se utilizado en tabla proveedor como identificador
create table tipo_documento(
	codigo_tipo_doc char(1) not null,
	descripcion varchar(50) not null,

	constraint tipo_documento_pk primary key (codigo_tipo_doc)
);

--PROVEEDOR: almacena la informacion de los proveedores- depende de la tabla tipo documento 
create table proveedor(
	identificador varchar(20) not null,
	tipo_documento char(1) not null,
	nombre varchar(100) not null,
	telefono varchar(10) not null,
	correo varchar(100) not null,
	direccion varchar(100) not null,

	constraint proveedor_pk primary key (identificador),
	constraint tipo_documento_fk foreign key (tipo_documento) references tipo_documento(codigo_tipo_doc)
);

--ESTADO: almacena el codigo si el producto fue recibido o solicitado, y se va ha utilizar en la tabla cabcera pedido
create table estado(
	codigo_estado char(1) not null,
	descripcion varchar(30) not null,

	constraint estado_pk primary key (codigo_estado)
);

--CABECERA PEDIDO: lamacena la cabecera de una pedido
create table cabecera_pedido(
	numero serial not null,
	proveedor varchar(20) not null,
	fecha date not null,
	estado char(1) not null,

	constraint cabecera_pedido_pk primary key (numero),
	constraint proveedor_fk foreign key (proveedor) references proveedor(identificador),
	constraint estado_fk foreign key (estado) references estado(codigo_estado)
);

--DETALLE PEDIDO: almacena informaciuon de los productos solicitados y recibidos depende de la tabla cabecera pedidos
create table detalle_pedido(
	codigo_pedido serial not null,
	cabecera_pedido int not null,
	producto int not null,
	cantidad_solicitada int not null,
	subtotal money not null,
	cantidad_recibida int not null,

	constraint detalle_pedido_pk primary key (codigo_pedido),
	constraint cabecera_pedido_fk foreign key (cabecera_pedido) references cabecera_pedido(numero),
	constraint producto_fk foreign key (producto) references productos(codigo_prod)
);

--CABECERA VENTAS: almacena la cabecera del detalle de los productos vendidos. es una tabla principal.
create table cabecera_ventas(
	codigo_cabecera_venta serial not null,
	fecha TIMESTAMPTZ not null, --fecha mas hora de zona horaria
	total_sin_iva money not null,
	iva money not null,
	total money not null,

	constraint cabecera_ventas_pk primary key (codigo_cabecera_venta)
);

-- DETALLE VENTAS: almacena informacion de los productos vendidos depende de la tabla cabecera ventas
create table detalle_ventas(
	codigo_detalle serial not null, 
	cabecera_ventas int not null,
	producto int not null,
	cantidad int not null,
	precio_venta money not null,
	subtotal money  not null,
	total_con_iva money not null,

	constraint detalle_ventas_pk primary key (codigo_detalle),
	constraint cabecera_ventas_fk  foreign key (cabecera_ventas) references cabecera_ventas(codigo_cabecera_venta),
	constraint  productos_fk foreign key (producto) references productos(codigo_prod)
);

--HISTORIAL STOCK: almacena un registro de cada movimiento del producto, es decir si aumneta con el pedido o se resta  con la venta
create table historial_stock (
	codigo_historial serial not null,
	fecha TIMESTAMPTZ not null, --fecha mas hora de zona horaria
	referencia varchar(50) not null,
	producto int not null,
	cantidad int not null,

	constraint historial_stock_pk primary key (codigo_historial),
	constraint productos_fk foreign key (producto) references productos(codigo_prod)
);


--=============================================
-----------------INSERT TABLAS-----------------
--=============================================
--CATEGORIAS:
insert into categorias(nombre,categoria_padre)
values('Materia Prima',null),
		('Proteina',1),
		('Salsas',1),
		('Punto Venta',null),
		('Bebidas',4),
		('Con alcohol',5),
		('Sin Alcohol',5);

--CATEGORIA UNIDAD MEDIDA
insert into categoria_unidad_medida(codigo_cat_udm,nombre)
values ('U','Unidades'),
		('V','Volumen'),
		('P','Peso'),
		('L','Longitud');

--UNIDADES DE MEDIDA
insert into unidades_medida(nombre,descripcion,categoria_udm)
values ('ml','mililitros','V'),
		('l','litros','V'),
		('u','unidades','U'),
		('d','decenas','U'),
		('g','gramos','P'),
		('kg','kilogramos','P'),
		('lb','libras','P');

--PRODUCTOS
insert into productos(nombre, udm, precio_venta, tiene_iva, coste, categoria, stock)
values ('Coca cola pequeña', 'u', 0.5804, true, 0.3729, 5, 105),
		('Salsa tomate', 'kg', 0.95, true, 0.8736, 3, 0),
		('Mostaza', 'kg', 0.95, true, 0.89, 3, 0),
		('Fuze te', 'u', 0.80, true, 0.7, 7, 49);

--TIPO DOCUMENTO 
insert into tipo_documento(codigo_tipo_doc, descripcion)
values	('C', 'Cedula'),
		('R','Ruc');

--PROVEEDOR
insert into proveedor(identificador, tipo_documento, nombre, telefono, correo, direccion)
values ('1753081056', 'C', 'Edwin Cusin', '0961918927', 'egca@cusin.com', 'Las Casas'),
		('1753081056001', 'R', 'Snack SA', '0961918928', 'empresa@cusin.com', 'Pifo-Los Valles');

--ESTADO 
insert into estado(codigo_estado, descripcion)
values ('S','Solicitado'),
		('R','Recibido');

--CABECERA PEDIDO
insert into cabecera_pedido(proveedor, fecha, estado)
values	('1753081056','30/11/2025','R'),
		('1753081056','20/12/2025','R');

--DETALLE PEDIDO
insert into detalle_pedido (cabecera_pedido, producto, cantidad_solicitada, subtotal, cantidad_recibida)
values 	(1, 1, 100, 37.27, 100),
		(1, 4, 50, 11.8, 50),
		(2, 1, 10, 3.72, 10);

--CABECERA VENTAS 
insert into cabecera_ventas(fecha, total_sin_iva, iva, total)
values ('20/11/2023 13:00:00', 3.26, 0.39, 3.65);

--DETALLE VENTAS
insert into detalle_ventas(cabecera_ventas, producto, cantidad, precio_venta, subtotal, total_con_iva)
values (1,1,5,0.58,2.9,3.25),
		(1,4,1,0.36,0.36,0.4);

--HISTORIAL STOCK 
insert into historial_stock(fecha, referencia, producto, cantidad)
values 	('20/11/2025 19:59:00', 'PEDIDO1', 1, 100),
		('20/11/2025 19:59:00', 'PEDIDO1', 4, 50),
		('20/11/2025 20:00:00', 'PEDIDO2', 1, 10),
		('21/11/2025 20:00:00', 'VENTA1', 1, -5),
		('21/11/2025 20:00:00', 'VENTA1', 4, -1);
		

--=============================================
----------------------SELECT-------------------
--=============================================
select * from categorias; 
select * from categoria_unidad_medida;
select * from unidades_medida;
select * from productos;
select * from tipo_documento;
select * from proveedor;
select * from estado;
select * from cabecera_pedido;
select * from detalle_pedido;
select * from cabecera_ventas;
select * from detalle_ventas;
select * from historial_stock;


