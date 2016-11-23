package com.shtyka.web.filters;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
public class FilterClient implements Filter {
    public FilterClient() {}
	public void destroy() {}
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
        String requestURI = req.getRequestURI();
        String pattern = "ServletRestaurant";
		if(requestURI.startsWith(pattern)){
			Integer clientId = Integer.parseInt(requestURI.substring(pattern.length()));
			req.getSession().setAttribute("clientId", clientId);
			req.getRequestDispatcher("/client").forward(request, response);
		}else
			chain.doFilter(request, response);
	}
	public void init(FilterConfig fConfig) throws ServletException {}
}
