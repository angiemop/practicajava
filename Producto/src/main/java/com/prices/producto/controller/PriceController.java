package com.prices.producto.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.prices.producto.dto.BusquedaPriceDTO;
import com.prices.producto.dto.PriceDTO;
import com.prices.producto.dto.Response;
import com.prices.producto.service.PriceService;

@RestController
@RequestMapping(value="/api/producto/price")
public class PriceController {
	
	@Autowired
	private PriceService priceService;
	
	@RequestMapping(value = "/{fecha}/{product}/{brand}", method = RequestMethod.GET)
	public ResponseEntity<?> buscarProducto(@PathVariable("fecha") final String fecha, @PathVariable("product") final Integer product, @PathVariable("brand") final Integer brand ) {
		Instant start = Instant.now();
		Response<Map<String, Object>> response = new Response<>();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			List<PriceDTO> result = priceService.consultarProducto(fecha,product, brand);
			Map<String, Object> res = new HashMap<>();
			res.put("productos", result);
			response.setResponse(res);
			response.setProcessTime(Duration.between(start, Instant.now()).toMillis());
			return new ResponseEntity<>(response, headers, HttpStatus.OK);
		} catch (Exception e) {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			response.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
			response.setProcessTime(Duration.between(start, Instant.now()).toMillis());
			return new ResponseEntity<>(response, headers, HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/busqueda/", method = RequestMethod.POST)
	public ResponseEntity<?> buscarListadoProductos(@RequestBody BusquedaPriceDTO busquedaPrice) {
		Instant start = Instant.now();
		Response<Map<String, Object>> response = new Response<>();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			List<PriceDTO> result = priceService.buscarProductosFechaFormato(busquedaPrice);
			Map<String, Object> res = new HashMap<>();
			res.put("productos", result);
			response.setResponse(res);
			response.setProcessTime(Duration.between(start, Instant.now()).toMillis());
			return new ResponseEntity<>(response, headers, HttpStatus.OK);
		} catch (Exception e) {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			response.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
			response.setProcessTime(Duration.between(start, Instant.now()).toMillis());
			return new ResponseEntity<>(response, headers, HttpStatus.BAD_REQUEST);
		}
	}

}
