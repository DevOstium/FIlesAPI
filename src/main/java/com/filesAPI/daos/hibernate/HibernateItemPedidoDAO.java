package com.filesAPI.daos.hibernate;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.filesAPI.daos.dao.ItemPedidoDAO;
import com.filesAPI.daos.models.Entrega;
import com.filesAPI.daos.models.ItemPedido;


public class HibernateItemPedidoDAO extends HibernateGenericDAO<ItemPedido, Long> implements ItemPedidoDAO, Serializable {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public List<ItemPedido> buscarItensPendentes() {
		Criteria criteria = getSession().createCriteria(ItemPedido.class);
	    
	    criteria.createAlias("pedido", "p").add(Restrictions.eq("p.entrega", Entrega.PENDENTE));
	    
	    return criteria.list();
	}
	
}