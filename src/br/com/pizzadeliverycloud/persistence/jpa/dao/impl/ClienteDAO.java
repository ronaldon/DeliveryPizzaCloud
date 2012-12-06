package br.com.pizzadeliverycloud.persistence.jpa.dao.impl;

import com.google.appengine.api.datastore.Key;

import br.com.pizzadeliverycloud.entity.Cliente;
import br.com.pizzadeliverycloud.persistence.EMFactory;
import br.com.pizzadeliverycloud.persistence.jpa.dao.AbstractDao;

public class ClienteDAO extends AbstractDao<Key, Cliente> {

	public ClienteDAO() {
		super(EMFactory.getInstance(), Cliente.class);
	}

}
