package fr.icam.elki.clustering;

import javax.servlet.http.HttpServletRequest;

import de.lmu.ifi.dbs.elki.algorithm.clustering.kmeans.KMeansLloyd;
import de.lmu.ifi.dbs.elki.algorithm.clustering.kmeans.initialization.RandomlyGeneratedInitialMeans;
import de.lmu.ifi.dbs.elki.data.Clustering;
import de.lmu.ifi.dbs.elki.data.NumberVector;
import de.lmu.ifi.dbs.elki.data.model.KMeansModel;
import de.lmu.ifi.dbs.elki.database.Database;
import de.lmu.ifi.dbs.elki.distance.distancefunction.NumberVectorDistanceFunction;
import de.lmu.ifi.dbs.elki.math.random.RandomFactory;

public class KMeansElkiClustering extends ElkiClustering<KMeansModel> {

	private static final long serialVersionUID = 20180305120000L;

	private int length;
	
	private int limit;
	
	public void setLength(String length) throws Exception {
		if (length == null) {
			throw new Exception("missing parameter 'length'");
		} else {
			this.length = Integer.valueOf(length).intValue();
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
		this.setLimit(request.getParameter("limit"));
		return true;
	}
	
	@Override
	protected Clustering<KMeansModel> doProcess(Database database) throws Exception {
		NumberVectorDistanceFunction<NumberVector> dist = this.getDistance();
	    RandomlyGeneratedInitialMeans init = new RandomlyGeneratedInitialMeans(RandomFactory.DEFAULT);
	    KMeansLloyd<NumberVector> km = new KMeansLloyd<NumberVector>(dist, length, limit, init);
	    return km.run(database);
	}
	
}
