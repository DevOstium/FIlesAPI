package com.filesAPI.daos.dao;

import java.util.List;

import com.filesAPI.daos.models.ItemPedido;

public interface ItemPedidoDAO extends GenericDAO<ItemPedido, Long> {

	public List<ItemPedido> buscarItensPendentes();
	
}