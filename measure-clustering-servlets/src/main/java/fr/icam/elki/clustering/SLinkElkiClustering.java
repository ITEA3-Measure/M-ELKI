package fr.icam.elki.clustering;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.lmu.ifi.dbs.elki.data.model.DendrogramModel;
import fr.icam.elki.distances.Distance;

public class SLinkElkiClustering extends ElkiDistanceClustering<DendrogramModel> {

	private static final long serialVersionUID = 20180305130000L;

	public Integer getSize(Long id) {
		return this.getConfiguration(id).getSlink().getSize();
	}
	
	public void setSize(Long id, String size) throws ServletException {
		if (size == null) {
			throw new ServletException("missing parameter 'size'");
		} else {
			try {
				Integer value = Integer.valueOf(size);
				this.getConfiguration(id).getSlink().setSize(value);
			} catch (Throwable t) {
				throw new ServletException(t);
			}
		}
	}

	@Override
	protected void setDistance(Long id, Distance distance) throws ServletException {
		this.getConfiguration(id).getSlink().setDistance(distance);
	}

	@Override
	protected Distance getDistance(Long id) throws ServletException {
		return this.getConfiguration(id).getSlink().getDistance();
	}
	
	@Override
	public void init() throws ServletException {
		super.init();
		this.setSize(0L, this.getInitParameter("size"));
	}

	@Override
	public final void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Long id = this.getResource(request);
		Map<String, Object> parameters = new HashMap<String, Object>(2);
		parameters.put("distance", this.getDistance(id));
		parameters.put("size", this.getSize(id));
		this.getMapper().toJson(parameters, response.getWriter());
	}

	@Override
	protected boolean setUp(Long id, HttpServletRequest request) throws ServletException {
		super.setUp(id, request);
		this.setSize(id, request.getParameter("size"));
		return true;
	}

}
