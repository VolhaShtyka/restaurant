package com.shtyka.web.filters;


import com.shtyka.web.command.ClientCommand;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class PaginationFilter implements Filter {
    private static final Logger log = Logger.getLogger(PaginationFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        final String pageSize = httpRequest.getParameter("recordsPerPage");
        final String pageNum = httpRequest.getParameter("currentPage");

        if(StringUtils.isNotEmpty(pageSize) && StringUtils.isNumeric(pageSize)
                && StringUtils.isNotEmpty(pageNum) && StringUtils.isNumeric(pageNum)){
            if(log.isDebugEnabled()){
                log.debug("Debug");
            }
            ClientCommand.currentPage = Integer.parseInt(pageNum);
            ClientCommand.recordsPerPage = Integer.parseInt(pageSize);
            chain.doFilter(request, response);

        }

    }
    @Override
    public void destroy() {

    }
}
