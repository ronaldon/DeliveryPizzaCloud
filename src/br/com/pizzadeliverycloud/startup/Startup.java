package br.com.pizzadeliverycloud.startup;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import br.com.pizzadeliverycloud.entity.Pizza;
import br.com.pizzadeliverycloud.persistence.jpa.dao.impl.PizzaDAO;

public class Startup implements ServletContextListener {

	/** default serial version UID */
	private static final long serialVersionUID = 1L;

	private static final String PIZZA_LIST = "pizzaList";

	private static PizzaDAO pizzaDAO = new PizzaDAO();

	@Override
	public void contextDestroyed(ServletContextEvent context) {
		List<Pizza> pizzaList = pizzaDAO.findAll();
		
		for (Pizza pizza : pizzaList) {
			pizzaDAO.remove(pizza);
		}
		
		context.getServletContext().removeAttribute(PIZZA_LIST);
	}

	@Override
	public void contextInitialized(ServletContextEvent context) {
		List<Pizza> pizzaList = pizzaDAO.findAll();
		ServletContext servletContext = context.getServletContext();
		
		if (pizzaList == null || pizzaList.size() == 0) {
			
			Pizza pizza = null;
			String paramValue = (String) servletContext.getInitParameter(PIZZA_LIST);
			
			String[] pizzaArray = paramValue.split(";");
			
			for (String pizzaStr : pizzaArray) {
				String[] attrs = pizzaStr.split("[|]");
				
				pizza = new Pizza();
				pizza.setNome(attrs[0].trim());
				pizza.setDescricao(attrs[1].trim());
				pizza.setPreco(Double.parseDouble(attrs[2].trim()));
				
				pizzaDAO.persist(pizza);
			}		
		}
		
		servletContext.setAttribute(PIZZA_LIST, pizzaDAO.findAll());
	}
}
