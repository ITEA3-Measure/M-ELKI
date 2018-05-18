package fr.icam.elki.configurations;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.icam.elki.configurations.ElkiAlgorithm;

public abstract class ElkiSelection extends HttpServlet {

	private static final long serialVersionUID = 201804231420001L;
	
	private Map<Long, ElkiConfiguration> configurations;
	
	protected ElkiConfiguration getConfiguration(Long id) {
		return configurations.get(id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void init() throws ServletException {
		super.init();
		configurations = (Map<Long, ElkiConfiguration>) this.getServletContext().getAttribute("configurations");
	}

	protected abstract ElkiAlgorithm getAlgorithm();
	
	private boolean isSelected(Long id, HttpServletRequest request) throws ServletException {
		ElkiConfiguration configuration = this.getConfiguration(id);
		return configuration.isSelected(this.getAlgorithm());
	}

	private void doSelect(Long id) throws ServletException {
		ElkiConfiguration configuration = this.getConfiguration(id);
		configuration.doSelect(this.getAlgorithm());
	}

	private Long getResource(HttpServletRequest request) throws ServletException {
		return (Long) request.getAttribute("id");
	}
	
	@Override
	public final void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		try {
			Long id = this.getResource(request);
			boolean done = this.isSelected(id, request);
			response.getWriter().write(done ? "true" : "false");
		} catch (Exception e) {
			response.sendError(520, e.getMessage());
		}
	}

	@Override
	public final void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		try {
			Long id = this.getResource(request);
			this.doSelect(id);
			response.getWriter().write("true");
		} catch (Exception e) {
			response.sendError(520, e.getMessage());
		}
	}

}
