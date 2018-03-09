package fr.icam.elki.clustering;

import javax.servlet.http.HttpServletRequest;

import de.lmu.ifi.dbs.elki.algorithm.clustering.hierarchical.SLINK;
import de.lmu.ifi.dbs.elki.algorithm.clustering.hierarchical.extraction.SimplifiedHierarchyExtraction;
import de.lmu.ifi.dbs.elki.data.Clustering;
import de.lmu.ifi.dbs.elki.data.NumberVector;
import de.lmu.ifi.dbs.elki.data.model.DendrogramModel;
import de.lmu.ifi.dbs.elki.database.Database;
import de.lmu.ifi.dbs.elki.distance.distancefunction.NumberVectorDistanceFunction;

public class SLinkElkiClustering extends ElkiClustering<DendrogramModel> {

	private static final long serialVersionUID = 20180305130000L;

	private int size;

	public void setSize(String size) throws Exception {
		if (size == null) {
			throw new Exception("missing parameter 'size'");
		} else {
			this.size = Integer.valueOf(size).intValue();
		}
	}

	@Override
	protected boolean setUp(HttpServletRequest request) throws Exception {
		this.setSize(request.getParameter("size"));
		return true;
	}
	
	@Override
	protected Clustering<DendrogramModel> doProcess(Database database) throws Exception {
		NumberVectorDistanceFunction<NumberVector> dist = this.getDistance();
		SLINK<NumberVector> slink = new SLINK<NumberVector>(dist);
		SimplifiedHierarchyExtraction e = new SimplifiedHierarchyExtraction(slink, size);
	    return e.run(database);
	}

}
