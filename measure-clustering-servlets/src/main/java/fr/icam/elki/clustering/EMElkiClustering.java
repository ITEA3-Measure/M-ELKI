package fr.icam.elki.clustering;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.lmu.ifi.dbs.elki.algorithm.clustering.em.EM;
import de.lmu.ifi.dbs.elki.algorithm.clustering.em.MultivariateGaussianModelFactory;
import de.lmu.ifi.dbs.elki.algorithm.clustering.kmeans.initialization.RandomlyGeneratedInitialMeans;
import de.lmu.ifi.dbs.elki.data.Clustering;
import de.lmu.ifi.dbs.elki.data.NumberVector;
import de.lmu.ifi.dbs.elki.data.model.EMModel;
import de.lmu.ifi.dbs.elki.database.Database;
import de.lmu.ifi.dbs.elki.math.random.RandomFactory;

public class EMElkiClustering extends ElkiClustering<EMModel> {

	private static final long serialVersionUID = 20180305123000L;
	
	private int length;
	
	private double delta;
	
	private int limit;
	
	public void setLength(String length) throws ServletException {
		if (length == null) {
			throw new ServletException("missing parameter 'length'");
		} else {
			try {
				this.length = Integer.valueOf(length).intValue();
			} catch (Throwable t) {
				throw new ServletException(t);
			}
		}
	}
	
	public void setDelta(String delta) throws ServletException {
		if (delta == null) {
			throw new ServletException("missing parameter 'delta'");
		} else {
			try {
				this.delta = Double.valueOf(delta).intValue();
			} catch (Throwable t) {
				throw new ServletException(t);
			}
		}
	}

	public void setLimit(String limit) throws ServletException {
		if (limit == null) {
			throw new ServletException("missing parameter 'limit'");
		} else {
			try {
				this.limit = Integer.valueOf(limit).intValue();
			} catch (Throwable t) {
				throw new ServletException(t);
			}
		}
	}
	
	@Override
	public void init() throws ServletException {
		super.init();
		this.setLength(this.getInitParameter("length"));
		this.setDelta(this.getInitParameter("delta"));
		this.setLimit(this.getInitParameter("limit"));
	}

	@Override
	public final void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Map<String, Object> parameters = new HashMap<String, Object>(3);
		parameters.put("length", length);
		parameters.put("delta", delta);
		parameters.put("limit", limit);
		this.getMapper().toJson(parameters, response.getWriter());
	}

	@Override
	protected boolean setUp(HttpServletRequest request) throws ServletException {
		this.setLength(request.getParameter("length"));
		this.setDelta(request.getParameter("delta"));
		this.setLimit(request.getParameter("limit"));
		return true;
	}
	
	@Override
	protected Clustering<EMModel> doProcess(Database database) throws ServletException {
		RandomlyGeneratedInitialMeans init = new RandomlyGeneratedInitialMeans(RandomFactory.DEFAULT);
		MultivariateGaussianModelFactory<NumberVector> fact = new MultivariateGaussianModelFactory<NumberVector>(init);
		EM<NumberVector, EMModel> em = new EM<NumberVector, EMModel>(length, delta, fact, limit, false);
	    return em.run(database);
	}

}
