package fr.icam.elki.processing;

import java.util.Map;

import de.lmu.ifi.dbs.elki.algorithm.clustering.DBSCAN;
import de.lmu.ifi.dbs.elki.data.Clustering;
import de.lmu.ifi.dbs.elki.data.NumberVector;
import de.lmu.ifi.dbs.elki.data.model.Model;
import de.lmu.ifi.dbs.elki.database.Database;
import de.lmu.ifi.dbs.elki.distance.distancefunction.NumberVectorDistanceFunction;
import fr.icam.elki.configurations.DBScanElkiConfiguration;

public class DBScanElkiProcessor implements ElkiProcessor<Model> {

	private DBScanElkiConfiguration configuration;
	
	private Map<String, NumberVectorDistanceFunction<NumberVector>> distances;
	
	public DBScanElkiProcessor(Map<String, NumberVectorDistanceFunction<NumberVector>> distances, DBScanElkiConfiguration configuration) {
		this.configuration = configuration;
		this.distances = distances;
	}
	
	@Override
	public Clustering<Model> doProcess(Database database) throws Exception {
		DBSCAN<NumberVector> dbscan = new DBSCAN<NumberVector>(distances.get(configuration.getDistance().getId()), configuration.getEpsilon(), configuration.getSize());
		return dbscan.run(database);
	}

}
