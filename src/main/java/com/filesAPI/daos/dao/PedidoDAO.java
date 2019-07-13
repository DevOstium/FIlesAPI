package com.filesAPI.daos.dao;

import java.util.List;

import com.filesAPI.daos.models.Pedido;
import com.filesAPI.daos.models.ValorTotalVendaDoDia;


public interface PedidoDAO extends GenericDAO<Pedido, Long> {

	public List<ValorTotalVendaDoDia> buscarValorTotalVendaDoDia();
	
}