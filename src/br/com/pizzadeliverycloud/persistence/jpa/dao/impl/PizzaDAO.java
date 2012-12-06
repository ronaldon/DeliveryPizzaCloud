package br.com.pizzadeliverycloud.persistence.jpa.dao.impl;

import br.com.pizzadeliverycloud.entity.Pizza;
import br.com.pizzadeliverycloud.persistence.EMFactory;
import br.com.pizzadeliverycloud.persistence.jpa.dao.AbstractDao;

public class PizzaDAO extends AbstractDao<Long, Pizza> {

	public PizzaDAO() {
		super(EMFactory.getInstance(), Pizza.class);
	}

}
