package fr.icam.elki.processing;

import java.util.Map;

import de.lmu.ifi.dbs.elki.algorithm.clustering.hierarchical.SLINK;
import de.lmu.ifi.dbs.elki.algorithm.clustering.hierarchical.extraction.SimplifiedHierarchyExtraction;
import de.lmu.ifi.dbs.elki.data.Clustering;
import de.lmu.ifi.dbs.elki.data.NumberVector;
import de.lmu.ifi.dbs.elki.data.model.DendrogramModel;
import de.lmu.ifi.dbs.elki.database.Database;
import de.lmu.ifi.dbs.elki.distance.distancefunction.NumberVectorDistanceFunction;
import fr.icam.elki.configurations.SLinkElkiConfiguration;

public class SLinkElkiProcessor implements ElkiProcessor<DendrogramModel> {

	private SLinkElkiConfiguration configuration;
	
	private Map<String, NumberVectorDistanceFunction<NumberVector>> distances;
	
	public SLinkElkiProcessor(Map<String, NumberVectorDistanceFunction<NumberVector>> distances, SLinkElkiConfiguration configuration) {
		this.configuration = configuration;
		this.distances = distances;
	}
	
	@Override
	public Clustering<DendrogramModel> doProcess(Database database) throws Exception {
		SLINK<NumberVector> slink = new SLINK<NumberVector>(distances.get(configuration.getDistance().getId()));
		SimplifiedHierarchyExtraction e = new SimplifiedHierarchyExtraction(slink, configuration.getSize());
	    return e.run(database);
	}

}
