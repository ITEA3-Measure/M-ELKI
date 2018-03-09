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

	private double epsilon;
	
	private int size;
	
	public void setEpsilon(String epsilon) throws ServletException {
		if (epsilon == null) {
			throw new ServletException("missing parameter 'epsilon'");
		} else {
			try {
				this.epsilon = Double.valueOf(epsilon).doubleValue();
			} catch (Throwable t) {
				throw new ServletException(t);
			}
		}
	}

	public void setSize(String size) throws ServletException {
		if (size == null) {
			throw new ServletException("missing parameter 'size'");
		} else {
			try {
				this.size = Integer.valueOf(size).intValue();
			} catch (Throwable t) {
				throw new ServletException(t);
			}
		}
	}
	
	@Override
	public void init() throws ServletException {
		super.init();
		this.setEpsilon(this.getInitParameter("epsilon"));
		this.setSize(this.getInitParameter("size"));
	}

	@Override
	public final void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Map<String, Object> parameters = new HashMap<String, Object>(3);
		parameters.put("distance", this.getDistance());
		parameters.put("epsilon", epsilon);
		parameters.put("size", size);
		this.getMapper().toJson(parameters, response.getWriter());
	}
	
	@Override
	protected boolean setUp(HttpServletRequest request) throws ServletException {
		super.setUp(request);
		this.setEpsilon(request.getParameter("epsilon"));
		this.setSize(request.getParameter("size"));
		return true;
	}
	
	@Override
	protected Clustering<Model> doProcess(Database database) throws ServletException {
		DBSCAN<NumberVector> dbscan = new DBSCAN<NumberVector>(distance, epsilon, size);
		return dbscan.run(database);
	}

}
