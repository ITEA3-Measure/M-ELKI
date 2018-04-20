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
import fr.icam.elki.distances.Distance;

public class DBScanElkiClustering extends ElkiDistanceClustering<Model> {

	private static final long serialVersionUID = 20180305121500L;
	
	public Double getEpsilon(Long id) {
		return this.getConfiguration(id).getDbscan().getEpsilon();
	}
	
	public void setEpsilon(Long id, String epsilon) throws ServletException {
		if (epsilon == null) {
			throw new ServletException("missing parameter 'epsilon'");
		} else {
			try {
				Double value = Double.valueOf(epsilon);
				this.getConfiguration(id).getDbscan().setEpsilon(value);
			} catch (Throwable t) {
				throw new ServletException(t);
			}
		}
	}
	
	public Integer getSize(Long id) {
		return this.getConfiguration(id).getDbscan().getSize();
	}

	public void setSize(Long id, String size) throws ServletException {
		if (size == null) {
			throw new ServletException("missing parameter 'size'");
		} else {
			try {
				Integer value = Integer.valueOf(size);
				this.getConfiguration(id).getDbscan().setSize(value);
			} catch (Throwable t) {
				throw new ServletException(t);
			}
		}
	}

	@Override
	protected void setDistance(Long id, Distance distance) throws ServletException {
		this.getConfiguration(id).getDbscan().setDistance(distance);
	}

	@Override
	protected Distance getDistance(Long id) throws ServletException {
		return this.getConfiguration(id).getDbscan().getDistance();
	}
	
	@Override
	public void init() throws ServletException {
		super.init();
		this.setEpsilon(0L, this.getInitParameter("epsilon"));
		this.setSize(0L, this.getInitParameter("size"));
	}

	@Override
	public final void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Long id = this.getResource(request);
		Map<String, Object> parameters = new HashMap<String, Object>(3);
		parameters.put("distance", this.getDistance(id));
		parameters.put("epsilon", this.getEpsilon(id));
		parameters.put("size", this.getSize(id));
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
		DBSCAN<NumberVector> dbscan = new DBSCAN<NumberVector>(this.getInstanceOf(this.getDistance(id)), this.getEpsilon(id), this.getSize(id));
		return dbscan.run(database);
	}

}
