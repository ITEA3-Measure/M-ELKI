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
	
	public Integer getLength(Long id) {
		return this.getConfiguration(id).getEm().getLength();
	}
	
	public void setLength(Long id, String length) throws ServletException {
		if (length == null) {
			throw new ServletException("missing parameter 'length'");
		} else {
			try {
				Integer value = Integer.valueOf(length);
				this.getConfiguration(id).getEm().setLength(value);
			} catch (Throwable t) {
				throw new ServletException(t);
			}
		}
	}
	
	public Double getDelta(Long id) {
		return this.getConfiguration(id).getEm().getDelta();
	}
	
	public void setDelta(Long id, String delta) throws ServletException {
		if (delta == null) {
			throw new ServletException("missing parameter 'delta'");
		} else {
			try {
				Double value = Double.valueOf(delta);
				this.getConfiguration(id).getEm().setDelta(value);
			} catch (Throwable t) {
				throw new ServletException(t);
			}
		}
	}
	
	public Integer getLimit(Long id) {
		return this.getConfiguration(id).getEm().getLimit();
	}

	public void setLimit(Long id, String limit) throws ServletException {
		if (limit == null) {
			throw new ServletException("missing parameter 'limit'");
		} else {
			try {
				Integer value = Integer.valueOf(limit);
				this.getConfiguration(id).getEm().setLimit(value);
			} catch (Throwable t) {
				throw new ServletException(t);
			}
		}
	}
	
	@Override
	public void init() throws ServletException {
		super.init();
		this.setLength(0L, this.getInitParameter("length"));
		this.setDelta(0L, this.getInitParameter("delta"));
		this.setLimit(0L, this.getInitParameter("limit"));
	}

	@Override
	public final void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Long id = this.getResource(request);
		Map<String, Object> parameters = new HashMap<String, Object>(3);
		parameters.put("length", this.getLength(id));
		parameters.put("delta", this.getDelta(id));
		parameters.put("limit", this.getLimit(id));
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
		EM<NumberVector, EMModel> em = new EM<NumberVector, EMModel>(this.getLength(id), this.getDelta(id), fact, this.getLimit(id), false);
	    return em.run(database);
	}

}
