package fr.icam.elki.clustering;

import javax.servlet.http.HttpServletRequest;

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
	
	public void setLength(String length) throws Exception {
		if (length == null) {
			throw new Exception("missing parameter 'length'");
		} else {
			this.length = Integer.valueOf(length).intValue();
		}
	}
	
	public void setDelta(String delta) throws Exception {
		if (delta == null) {
			throw new Exception("missing parameter 'delta'");
		} else {
			this.delta = Double.valueOf(delta).doubleValue();
		}
	}

	public void setLimit(String limit) throws Exception {
		if (limit == null) {
			throw new Exception("missing parameter 'limit'");
		} else {
			this.limit = Integer.valueOf(limit).intValue();
		}
	}	

	@Override
	protected boolean setUp(HttpServletRequest request) throws Exception {
		this.setLength(request.getParameter("length"));
		this.setDelta(request.getParameter("delta"));
		this.setLimit(request.getParameter("limit"));
		return true;
	}
	
	@Override
	protected Clustering<EMModel> doProcess(Database database) throws Exception {
		RandomlyGeneratedInitialMeans init = new RandomlyGeneratedInitialMeans(RandomFactory.DEFAULT);
		MultivariateGaussianModelFactory<NumberVector> fact = new MultivariateGaussianModelFactory<NumberVector>(init);
		EM<NumberVector, EMModel> em = new EM<NumberVector, EMModel>(length, delta, fact, limit, false);
	    return em.run(database);
	}

}
