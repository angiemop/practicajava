package com.prices.producto.service;

import java.util.List;

import com.prices.producto.dto.BusquedaPriceDTO;
import com.prices.producto.dto.PriceDTO;

public interface PriceService {

	List<PriceDTO> consultarProducto(String fechaAplicacion, Integer idProducto, Integer idBrand);
	List<PriceDTO> buscarProductosFechaFormato( BusquedaPriceDTO busqueda);
}
