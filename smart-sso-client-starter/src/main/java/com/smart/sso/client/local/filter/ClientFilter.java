package com.smart.sso.client.local.filter;


import com.smart.sso.client.local.listener.LogoutListener;
import com.smart.sso.client.local.session.SessionMappingStorage;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter基类
 * 
 * @author Joe
 */
public abstract class ClientFilter extends ParamFilter implements Filter {
	
	private SessionMappingStorage sessionMappingStorage;
    
	public abstract boolean isAccessAllowed(HttpServletRequest request, HttpServletResponse response)
			throws IOException;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException {
	}

	@Override
	public void destroy() {
	}
	
	protected SessionMappingStorage getSessionMappingStorage() {
		if (sessionMappingStorage == null) {
            sessionMappingStorage = LogoutListener.getSessionMappingStorage();
        }
		return sessionMappingStorage;
	}
}