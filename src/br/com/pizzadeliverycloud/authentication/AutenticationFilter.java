package br.com.pizzadeliverycloud.authentication;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class AutenticationFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req	= (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		
		if (user == null) {
			resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
		} else {
			chain.doFilter(request, response);
		}
		
	}

	@Override
	public void destroy() {}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {}

}
