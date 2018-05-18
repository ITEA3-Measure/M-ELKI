package fr.icam.elki.configurations;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ElkiIdentifierFilter implements Filter {

	private Map<Long, ElkiConfiguration> configurations;
	
	@SuppressWarnings("unchecked")
	@Override
	public void init(FilterConfig config) throws ServletException {
		configurations = (Map<Long, ElkiConfiguration>) config.getServletContext().getAttribute("configurations");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String pid = request.getParameter("id");
		if (pid == null || pid.isEmpty()) {
			throw new ServletException("missing parameter 'id'");
		} else {
			try { 
				Long id = Long.valueOf(pid);
				if (configurations.containsKey(id)) {
					request.setAttribute("id", id);
				} else {
					throw new ServletException("unknown analysis ID: " + id);		
				}
			} catch (Throwable t) { 
				throw new ServletException(t); 
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() { }
	
}
