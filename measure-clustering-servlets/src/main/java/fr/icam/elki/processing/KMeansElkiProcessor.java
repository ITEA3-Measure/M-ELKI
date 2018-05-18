package fr.icam.elki.processing;

import java.util.Map;

import de.lmu.ifi.dbs.elki.algorithm.clustering.kmeans.KMeansLloyd;
import de.lmu.ifi.dbs.elki.algorithm.clustering.kmeans.initialization.RandomlyGeneratedInitialMeans;
import de.lmu.ifi.dbs.elki.data.Clustering;
import de.lmu.ifi.dbs.elki.data.NumberVector;
import de.lmu.ifi.dbs.elki.data.model.KMeansModel;
import de.lmu.ifi.dbs.elki.database.Database;
import de.lmu.ifi.dbs.elki.distance.distancefunction.NumberVectorDistanceFunction;
import de.lmu.ifi.dbs.elki.math.random.RandomFactory;
import fr.icam.elki.configurations.KMeansElkiConfiguration;

public class KMeansElkiProcessor implements ElkiProcessor<KMeansModel> {

	private KMeansElkiConfiguration configuration;
	
	private Map<String, NumberVectorDistanceFunction<NumberVector>> distances;
	
	public KMeansElkiProcessor(Map<String, NumberVectorDistanceFunction<NumberVector>> distances, KMeansElkiConfiguration configuration) {
		this.configuration = configuration;
		this.distances = distances;
	}
	
	@Override
	public Clustering<KMeansModel> doProcess(Database database) throws Exception {
		RandomlyGeneratedInitialMeans init = new RandomlyGeneratedInitialMeans(RandomFactory.DEFAULT);
	    KMeansLloyd<NumberVector> km = new KMeansLloyd<NumberVector>(distances.get(configuration.getDistance().getId()), configuration.getLength(), configuration.getLimit(), init);
	    return km.run(database);
	}

}
