package fr.icam.elki.clustering;

import javax.servlet.http.HttpServletRequest;

import de.lmu.ifi.dbs.elki.algorithm.clustering.DBSCAN;
import de.lmu.ifi.dbs.elki.data.Clustering;
import de.lmu.ifi.dbs.elki.data.NumberVector;
import de.lmu.ifi.dbs.elki.data.model.Model;
import de.lmu.ifi.dbs.elki.database.Database;
import de.lmu.ifi.dbs.elki.distance.distancefunction.NumberVectorDistanceFunction;

public class DBScanElkiClustering extends ElkiClustering<Model> {

	private static final long serialVersionUID = 20180305121500L;

	private double epsilon;
	
	private int size;
	
	public void setEpsilon(String epsilon) throws Exception {
		if (epsilon == null) {
			throw new Exception("missing parameter 'epsilon'");
		} else {
			this.epsilon = Double.valueOf(epsilon).doubleValue();
		}
	}

	public void setSize(String size) throws Exception {
		if (size == null) {
			throw new Exception("missing parameter 'size'");
		} else {
			this.size = Integer.valueOf(size).intValue();
		}
	}

	@Override
	protected boolean setUp(HttpServletRequest request) throws Exception {
		this.setEpsilon(request.getParameter("epsilon"));
		this.setSize(request.getParameter("size"));
		return true;
	}
	
	@Override
	protected Clustering<Model> doProcess(Database database) throws Exception {
		NumberVectorDistanceFunction<NumberVector> d = this.getDistance();
		DBSCAN<NumberVector> dbscan = new DBSCAN<NumberVector>(d, epsilon, size);
		return dbscan.run(database);
	}

}
