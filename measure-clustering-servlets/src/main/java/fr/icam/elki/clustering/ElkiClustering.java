package fr.icam.elki.clustering;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import de.lmu.ifi.dbs.elki.data.model.Model;
import fr.icam.elki.configurations.ElkiConfiguration;

public abstract class ElkiClustering<M extends Model> extends HttpServlet {

	private static final long serialVersionUID = 20180305100000L;
		
	private Map<Long, ElkiConfiguration> configurations;
	
	protected ElkiConfiguration getConfiguration(Long id) {
		return configurations.get(id);
	}
	
	private Gson mapper;
	
	protected Gson getMapper() {
		return mapper;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void init() throws ServletException {
		super.init();
		mapper = new Gson();
		configurations = (Map<Long, ElkiConfiguration>) this.getServletContext().getAttribute("configurations");
	}

	protected abstract boolean setUp(Long id, HttpServletRequest request) throws ServletException;
	
	@Override
	public final void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		try {
			Long id = this.getResource(request);
			boolean done = this.setUp(id, request);
			response.getWriter().write(done ? "true" : "false");
		} catch (Exception e) {
			response.sendError(520, e.getMessage());
		}
	}
	
	protected Long getResource(HttpServletRequest request) throws ServletException {
		return (Long) request.getAttribute("id");
	}

}
