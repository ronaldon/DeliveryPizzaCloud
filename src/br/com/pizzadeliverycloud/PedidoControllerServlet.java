package br.com.pizzadeliverycloud;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.pizzadeliverycloud.authentication.UserSessionFilter;
import br.com.pizzadeliverycloud.entity.Cliente;
import br.com.pizzadeliverycloud.entity.Pedido;
import br.com.pizzadeliverycloud.entity.Pizza;
import br.com.pizzadeliverycloud.persistence.jpa.dao.impl.PedidoDAO;
import br.com.pizzadeliverycloud.persistence.jpa.dao.impl.PizzaDAO;
import br.com.pizzadeliverycloud.utils.MailSender;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class PedidoControllerServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;	
	private static PedidoDAO pedidoDAO = new PedidoDAO();
	private static PizzaDAO pizzaDAO = new PizzaDAO();
	private static UserService userService = UserServiceFactory.getUserService();
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		processRequest(req, resp);
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {		
		processRequest(req, resp);
	}
	
	private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Pedido pedido = createPedido(req);
		
		String resultMessage = createResponseMessage(pedido);
		
		String email = userService.getCurrentUser().getEmail();
		
		MailSender.sendMail(email, "Pizzaria Delivery Cloud", resultMessage);
		
		createUserCookie(req, resp, pedido);
		
		writeResponse(resp, resultMessage);
	}

	private Pedido createPedido(HttpServletRequest req) {
		Pedido pedido = new Pedido();
		
		String[] pizzaArrayId = req.getParameterValues("pizza");
		
		for (String idStr : pizzaArrayId) {
			pedido.addPizza(pizzaDAO.findById(Long.parseLong(idStr)));
		}	
		
		pedido.setCliente(getCliente(req));
		
		pedidoDAO.create(pedido);
		return pedido;
	}

	private void createUserCookie(HttpServletRequest req, HttpServletResponse resp, Pedido pedido) {
		Cookie cookie = new Cookie(UserSessionFilter.USER_KEY, ""+pedido.getKey());
		cookie.setMaxAge(-1);
		resp.addCookie(cookie);
		
		req.getSession().removeAttribute(UserSessionFilter.USER_KEY);
	}

	private String createResponseMessage(Pedido pedido) {
		String result = "";
		
		if (pedido != null) {
			
			result+="Pedido realizado com sucesso!<br>";
			List<Pizza> pizzaList = pedido.getPizzaList();
			result+="<br>Pizza:";
			for (Pizza pizza : pizzaList) {
				result+="<br>" + pizza.getNome();
			}
			
			result+="<br><br>Valor total de: R$ " + pedido.getValor();
			result+="<br>Seu pedido não será entregue...rs";
		}
		
		return result;
	}
	
	private void writeResponse(HttpServletResponse resp, String message) throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println(message);
	}
	
	private Cliente getCliente(HttpServletRequest request) {
		
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		Cliente c = new Cliente();
		
		c.setNome(request.getParameter("nome"));
		c.setSobrenome(request.getParameter("sobrenome"));
		c.setEndereco(request.getParameter("endereco"));
		c.setBairro(request.getParameter("bairro"));
		c.setCidade(request.getParameter("cidade"));
		c.setTelefone(request.getParameter("ddd")+request.getParameter("numero"));
		c.setEmail(user.getEmail());
		c.setUsername(user.getNickname());
		
		return c;
	}	    
}
