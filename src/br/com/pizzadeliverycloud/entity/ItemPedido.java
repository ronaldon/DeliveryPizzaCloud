package br.com.pizzadeliverycloud.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.google.appengine.api.datastore.Key;

@Entity
public class ItemPedido {

	@Id  
    @GeneratedValue(strategy=GenerationType.IDENTITY)  
    private Key key;

	private Long pizzaId;
	
	@ManyToOne
	private Pedido pedido;
	
	ItemPedido(Pizza pizza) {
		this.pizzaId = pizza.getKey();
	}

	public Long getPizzaId() {
		return pizzaId;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

}
