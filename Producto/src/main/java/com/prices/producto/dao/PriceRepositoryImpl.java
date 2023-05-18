package com.prices.producto.dao;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.prices.producto.dto.BusquedaPriceDTO;
import com.prices.producto.dto.PriceDTO;

@Repository
public class PriceRepositoryImpl implements PriceRepository {
	
	@PersistenceContext
    private EntityManager em;
	
	@Override
	public List<PriceDTO> consultarProducto(String fechaAplicacion, Integer idProducto, Integer idBrand){
		
		StringBuilder jpql = new StringBuilder();
		List<PriceDTO> listaProducto = new ArrayList<>();
		jpql.append("SELECT PRODUCT_ID, BRAND_ID, PRICE_LIST, START_DATE, END_DATE, PRICE ");
		jpql.append("FROM PRICES ");
		jpql.append("WHERE 1=1 ");
		if(idProducto > 0) {
		jpql.append("AND PRODUCT_ID = :idProducto ");
		}
		if(idBrand > 0) {
		jpql.append("AND BRAND_ID = :idBrand ");
		}
		if(fechaAplicacion != null && !fechaAplicacion.equals("")) {
		jpql.append("AND parsedatetime(:fechaAplicacion, 'yyyy-MM-dd-HH.mm.ss')  BETWEEN START_DATE AND END_DATE ");
		}
		jpql.append("AND PRIORITY IN ( ");
		jpql.append("SELECT MAX(PRIORITY) FROM PRICES WHERE 1 = 1 ");
		if(idProducto > 0) {
		jpql.append(" AND PRODUCT_ID =:idProducto ");
		}
		if(idBrand > 0) {
		jpql.append("AND BRAND_ID = :idBrand "); 
		}
		if(fechaAplicacion != null && !fechaAplicacion.equals("")) {
		jpql.append("AND parsedatetime(:fechaAplicacion, 'yyyy-MM-dd-HH.mm.ss')  BETWEEN START_DATE AND END_DATE) ");
		}
		Query query = em.createNativeQuery(jpql.toString());
		if(idProducto > 0) {
		query.setParameter("idProducto",idProducto);
		}
		if(idBrand > 0) {
		query.setParameter("idBrand",idBrand);
		}
		if(fechaAplicacion != null && !fechaAplicacion.equals("")) {
		query.setParameter("fechaAplicacion",fechaAplicacion);
		}
		
		List<Object[]> lista = query.getResultList();
		
		for(Object[] o : lista){
			PriceDTO p = new PriceDTO();
			p.setIdProduct(o[0] != null ? Integer.parseInt(o[0].toString()) : null);
			p.setIdBrand(o[1] != null ? Integer.parseInt(o[1].toString()) : null);
			p.setPriceList(o[2] != null ? Integer.parseInt(o[2].toString()) : null);
			p.setStartDate(o[3] != null ? o[3].toString() : null);
			p.setEndDate(o[4] != null ? o[4].toString() : null);
			p.setPrice(o[5] != null ? new BigDecimal(o[5].toString()): null);
				
			listaProducto.add(p);
			
		}
		return listaProducto;
	}
	
	private static void convertirFecha (BusquedaPriceDTO busqueda) {
		//String string = "Oct 19, 2017 9:15:12 PM"; --tipo 1
		//String string2 = "2016-09-23 19:10:22";--tipo 2
		//String string3 = "12-09-2014 01:10 PM";--tipo 3
		DateFormat outFormat = new SimpleDateFormat( "yyyy-MM-dd-HH.mm.ss");
		Date date = null;
		

		try
    	{
			
			switch (busqueda.getTipo()) { 
		    case 1:
		    	DateFormat inFormat = new SimpleDateFormat( "MMM dd, yyyy hh:mm:ss aa");
		    	 date = inFormat.parse(busqueda.getFecha());
		     break;
		    case 2:
		    	DateFormat inFormat2 = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss");
		    	 date = inFormat2.parse(busqueda.getFecha());
		     break;
		      case 3 :
		    	DateFormat inFormat3 = new SimpleDateFormat( "dd-MM-yyyy hh:mm aa");
		    	 date = inFormat3.parse(busqueda.getFecha());
		     break;
		    default:
		    	DateFormat inFormat4 = new SimpleDateFormat( "dd-MM-yyyy HH:mm:ss");
		    	 date = inFormat4.parse(busqueda.getFecha());
		  }
			
    	   
    	   
    	}
    	catch ( ParseException e )
    	{
    	        e.printStackTrace();
    	}
		if( date != null ) {
			busqueda.setFechaFormateada(outFormat.format(date));
		}
		
	}
	
	@Override
	public List<PriceDTO> buscarProductosFechaFormato(BusquedaPriceDTO busqueda){
		
		convertirFecha(busqueda);
		
		StringBuilder jpql = new StringBuilder();
		List<PriceDTO> listaProducto = new ArrayList<>();
		jpql.append("SELECT PRODUCT_ID, BRAND_ID, PRICE_LIST, START_DATE, END_DATE, PRICE ");
		jpql.append("FROM PRICES ");
		jpql.append("WHERE 1 = 1 ");
		if(busqueda.getIdProduct() > 0) {
			jpql.append("AND PRODUCT_ID = :idProducto ");
		}
		if(busqueda.getIdBrand() > 0) {
			jpql.append("AND BRAND_ID = :idBrand ");
		}
		if(busqueda.getFechaFormateada() != null && !busqueda.getFechaFormateada().equals("")) {
			jpql.append("AND parsedatetime(:fechaAplicacion, 'yyyy-MM-dd-HH.mm.ss')  BETWEEN START_DATE AND END_DATE  ");
		}
		jpql.append("AND PRIORITY IN ( ");
		jpql.append("SELECT MAX(PRIORITY) FROM PRICES WHERE 1 = 1 ");
		if(busqueda.getIdProduct() > 0) {
		jpql.append(" AND PRODUCT_ID =:idProducto ");
		}
		if(busqueda.getIdBrand() > 0) {
		jpql.append("AND BRAND_ID = :idBrand "); 
		}
		if(busqueda.getFechaFormateada() != null && !busqueda.getFechaFormateada().equals("")) {
		jpql.append("AND parsedatetime(:fechaAplicacion, 'yyyy-MM-dd-HH.mm.ss')  BETWEEN START_DATE AND END_DATE) ");
		}
		Query query = em.createNativeQuery(jpql.toString());
		
		if(busqueda.getIdProduct() > 0) {
			query.setParameter("idProducto",busqueda.getIdProduct());
		}
		if(busqueda.getIdBrand() > 0) {
			query.setParameter("idBrand",busqueda.getIdBrand());
		}
		if(busqueda.getFechaFormateada() != null && !busqueda.getFechaFormateada().equals("")) {
			query.setParameter("fechaAplicacion",busqueda.getFechaFormateada());		
			}
		
		List<Object[]> lista = query.getResultList();
		
		for(Object[] o : lista){
			PriceDTO p = new PriceDTO();
			p.setIdProduct(o[0] != null ? Integer.parseInt(o[0].toString()) : null);
			p.setIdBrand(o[1] != null ? Integer.parseInt(o[1].toString()) : null);
			p.setPriceList(o[2] != null ? Integer.parseInt(o[2].toString()) : null);
			p.setStartDate(o[3] != null ? o[3].toString() : null);
			p.setEndDate(o[4] != null ? o[4].toString() : null);
			p.setPrice(o[5] != null ? new BigDecimal(o[5].toString()): null);
				
			listaProducto.add(p);
			
		}
		return listaProducto;
	}

}
