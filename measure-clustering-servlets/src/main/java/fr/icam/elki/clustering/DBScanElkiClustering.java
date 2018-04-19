package fr.icam.elki.clustering;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.lmu.ifi.dbs.elki.algorithm.clustering.DBSCAN;
import de.lmu.ifi.dbs.elki.data.Clustering;
import de.lmu.ifi.dbs.elki.data.NumberVector;
import de.lmu.ifi.dbs.elki.data.model.Model;
import de.lmu.ifi.dbs.elki.database.Database;

public class DBScanElkiClustering extends ElkiDistanceClustering<Model> {

	private static final long serialVersionUID = 20180305121500L;

	private Map<Long, Double> epsilons;
	
	private Map<Long, Integer> sizes;
	
	public void setEpsilon(Long id, String epsilon) throws ServletException {
		if (epsilon == null) {
			throw new ServletException("missing parameter 'epsilon'");
		} else {
			try {
				this.epsilons.put(id, Double.valueOf(epsilon));
			} catch (Throwable t) {
				throw new ServletException(t);
			}
		}
	}

	public void setSize(Long id, String size) throws ServletException {
		if (size == null) {
			throw new ServletException("missing parameter 'size'");
		} else {
			try {
				this.sizes.put(id, Integer.valueOf(size));
			} catch (Throwable t) {
				throw new ServletException(t);
			}
		}
	}
	
	@Override
	public void init() throws ServletException {
		super.init();
		this.epsilons = new HashMap<Long, Double>(128);
		this.sizes = new HashMap<Long, Integer>(128);
		this.setEpsilon(0L, this.getInitParameter("epsilon"));
		this.setSize(0L, this.getInitParameter("size"));
	}

	@Override
	public final void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Long id = this.getResource(request);
		Map<String, Object> parameters = new HashMap<String, Object>(3);
		parameters.put("distance", this.getDistance(id));
		parameters.put("epsilon", epsilons.get(id));
		parameters.put("size", sizes.get(id));
		this.getMapper().toJson(parameters, response.getWriter());
	}
	
	@Override
	protected boolean setUp(Long id, HttpServletRequest request) throws ServletException {
		super.setUp(id, request);
		this.setEpsilon(id, request.getParameter("epsilon"));
		this.setSize(id, request.getParameter("size"));
		return true;
	}
	
	@Override
	protected Clustering<Model> doProcess(Long id, Database database) throws ServletException {
		DBSCAN<NumberVector> dbscan = new DBSCAN<NumberVector>(distances.get(id), epsilons.get(id), sizes.get(id));
		return dbscan.run(database);
	}

}
