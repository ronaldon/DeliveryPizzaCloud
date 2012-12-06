package br.com.pizzadeliverycloud.persistence.jpa.dao.impl;

import java.util.ArrayList;
import java.util.List;

import br.com.pizzadeliverycloud.entity.Pedido;
import br.com.pizzadeliverycloud.entity.Pizza;
import br.com.pizzadeliverycloud.entity.ItemPedido;
import br.com.pizzadeliverycloud.persistence.EMFactory;
import br.com.pizzadeliverycloud.persistence.jpa.dao.AbstractDao;

public class PedidoDAO extends AbstractDao<Long, Pedido> {

	private PizzaDAO pizzaDAO = new PizzaDAO();
	
	public PedidoDAO() {
		super(EMFactory.getInstance(), Pedido.class);
	}

	@Override
	public Pedido findById(Long id) {
		Pedido p = super.findById(id);
	
		if (p != null) {
			List<ItemPedido> pizzaPedidoList = p.getItemPedidoList();
			List<Pizza> pizzaList = new ArrayList<Pizza>();
			
			for (ItemPedido pd : pizzaPedidoList) {
				pizzaList.add(pizzaDAO.findById(pd.getPizzaId()));
			}
			
			p.setPizzaList(pizzaList);
		}
		
		return p;
	}
}
