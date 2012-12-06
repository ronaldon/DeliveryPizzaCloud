package br.com.pizzadeliverycloud.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Pedido {

	@Id  
    @GeneratedValue(strategy=GenerationType.IDENTITY)  
    private Long key;

	private Double valor;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<ItemPedido> itemPedidoList;

	@ManyToOne(cascade = CascadeType.PERSIST)
	private Cliente cliente;
		
	@Transient
	private List<Pizza> pizzaList;

	public void addPizza(Pizza pizza) {
		if (itemPedidoList == null){
			itemPedidoList = new ArrayList<ItemPedido>();
			pizzaList = new ArrayList<Pizza>();
		}
		
		ItemPedido pd = new ItemPedido(pizza);
		pd.setPedido(this);
		
		itemPedidoList.add(pd);
		pizzaList.add(pizza);
		
		if (this.valor == null) {
			valor = 0D;
		}	
		valor+=pizza.getPreco();
	}
	
	public Double getValor() {
		valor = new BigDecimal(valor).setScale(2, RoundingMode.HALF_UP).doubleValue();
		
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public List<ItemPedido> getItemPedidoList() {
		return itemPedidoList;
	}

	public void setItemPedidoList(List<ItemPedido> itemList) {
		this.itemPedidoList = itemList;
	}

	public List<Pizza> getPizzaList() {
		return pizzaList;
	}

	public void setPizzaList(List<Pizza> pizzaList) {
		this.pizzaList = pizzaList;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}
