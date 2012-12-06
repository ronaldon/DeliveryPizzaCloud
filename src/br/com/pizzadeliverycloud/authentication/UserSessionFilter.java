package br.com.pizzadeliverycloud.authentication;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.pizzadeliverycloud.entity.Pedido;
import br.com.pizzadeliverycloud.persistence.jpa.dao.impl.PedidoDAO;

public class UserSessionFilter  implements Filter {

	/** default serial version UID */
	private static final long serialVersionUID = 1L;
	
	public static final String USER_KEY = "user";

	private static PedidoDAO pedidoDAO = new PedidoDAO();
	
	@Override
	public void doFilter(ServletRequest request, 
		ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req	= (HttpServletRequest) request;
		HttpSession session = req.getSession();
		Cookie[] cookies = req.getCookies();
		
		if (session.getAttribute(USER_KEY) == null && cookies != null) {
			for (Cookie cookie : cookies) {
				
				if (USER_KEY.equals(cookie.getName())) {
					Pedido pedido = pedidoDAO.findById(Long.parseLong(cookie.getValue()));
					
					if (pedido != null) {
						session.setAttribute(USER_KEY, pedido.getCliente());
					}
					
					break;
				}
			}
		}
	
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {}


	@Override
	public void destroy() {}

}
