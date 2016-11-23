package com.shtyka.web.filters;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
//@WebFilter(dispatcherTypes = { DispatcherType.REQUEST, DispatcherType.FORWARD }, urlPatterns = { "/client/*" })
public class FilterAdmin implements Filter {
	public FilterAdmin() {}
	public void destroy() {}
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String requestURI = req.getRequestURI();
		String pattern = "/ServletRestaurant/admin";
		if (requestURI.startsWith(pattern)) {
			String doAction = requestURI.substring(pattern.length());
			req.setAttribute("com/shtyka/web/command", doAction);
			req.getRequestDispatcher("/admin").forward(request, response);
		} else{
			chain.doFilter(request, response);
		}
	}
	public void init(FilterConfig fConfig) throws ServletException {}
}