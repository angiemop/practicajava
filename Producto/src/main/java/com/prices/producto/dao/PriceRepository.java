package com.prices.producto.dao;

import java.util.List;

import com.prices.producto.dto.BusquedaPriceDTO;
import com.prices.producto.dto.PriceDTO;

public interface PriceRepository {
	
	List<PriceDTO> consultarProducto(String fechaAplicacion, Integer idProducto, Integer idBrand);
	List<PriceDTO> buscarProductosFechaFormato(BusquedaPriceDTO busqueda);
	
	/*
	 * 
	 * Acepte como parámetros de entrada: fecha de aplicación, identificador de producto, identificador de cadena.
Devuelva como datos de salida: identificador de producto, identificador de cadena, tarifa a aplicar, fechas de aplicación y precio final a aplicar.
	 */

}
