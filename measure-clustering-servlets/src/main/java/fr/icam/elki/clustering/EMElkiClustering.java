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
	
	private Map<Long, Integer> lengths;
	
	private Map<Long, Double> deltas;
	
	private Map<Long, Integer> limits;
	
	public void setLength(Long id, String length) throws ServletException {
		if (length == null) {
			throw new ServletException("missing parameter 'length'");
		} else {
			try {
				this.lengths.put(id, Integer.valueOf(length));
			} catch (Throwable t) {
				throw new ServletException(t);
			}
		}
	}
	
	public void setDelta(Long id, String delta) throws ServletException {
		if (delta == null) {
			throw new ServletException("missing parameter 'delta'");
		} else {
			try {
				this.deltas.put(id, Double.valueOf(delta));
			} catch (Throwable t) {
				throw new ServletException(t);
			}
		}
	}

	public void setLimit(Long id, String limit) throws ServletException {
		if (limit == null) {
			throw new ServletException("missing parameter 'limit'");
		} else {
			try {
				this.limits.put(id, Integer.valueOf(limit));
			} catch (Throwable t) {
				throw new ServletException(t);
			}
		}
	}
	
	@Override
	public void init() throws ServletException {
		super.init();
		this.lengths = new HashMap<Long, Integer>(128);
		this.deltas = new HashMap<Long, Double>(128);
		this.limits = new HashMap<Long, Integer>(128);
		this.setLength(0L, this.getInitParameter("length"));
		this.setDelta(0L, this.getInitParameter("delta"));
		this.setLimit(0L, this.getInitParameter("limit"));
	}

	@Override
	public final void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Long id = this.getResource(request);
		Map<String, Object> parameters = new HashMap<String, Object>(3);
		parameters.put("length", lengths.get(id));
		parameters.put("delta", deltas.get(id));
		parameters.put("limit", limits.get(id));
		this.getMapper().toJson(parameters, response.getWriter());
	}

	@Override
	protected boolean setUp(Long id, HttpServletRequest request) throws ServletException {
		this.setLength(id, request.getParameter("length"));
		this.setDelta(id, request.getParameter("delta"));
		this.setLimit(id, request.getParameter("limit"));
		return true;
	}
	
	@Override
	protected Clustering<EMModel> doProcess(Long id, Database database) throws ServletException {
		RandomlyGeneratedInitialMeans init = new RandomlyGeneratedInitialMeans(RandomFactory.DEFAULT);
		MultivariateGaussianModelFactory<NumberVector> fact = new MultivariateGaussianModelFactory<NumberVector>(init);
		EM<NumberVector, EMModel> em = new EM<NumberVector, EMModel>(lengths.get(id), deltas.get(id), fact, limits.get(id), false);
	    return em.run(database);
	}

}
