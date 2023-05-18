package com.prices.producto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prices.producto.dao.PriceRepository;
import com.prices.producto.dto.BusquedaPriceDTO;
import com.prices.producto.dto.PriceDTO;

@Service
public class PriceServiceImpl implements PriceService{
	
	@Autowired
	private PriceRepository priceRepository;
	
	@Override
	public List<PriceDTO> consultarProducto(String fechaAplicacion, Integer idProducto, Integer idBrand){
		return priceRepository.consultarProducto(fechaAplicacion, idProducto, idBrand);
	}
	
	@Override
	public List<PriceDTO> buscarProductosFechaFormato( BusquedaPriceDTO busqueda){
		return priceRepository.buscarProductosFechaFormato(busqueda);
	}

}
