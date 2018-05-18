package fr.icam.elki.processing;

import de.lmu.ifi.dbs.elki.algorithm.clustering.em.EM;
import de.lmu.ifi.dbs.elki.algorithm.clustering.em.MultivariateGaussianModelFactory;
import de.lmu.ifi.dbs.elki.algorithm.clustering.kmeans.initialization.RandomlyGeneratedInitialMeans;
import de.lmu.ifi.dbs.elki.data.Clustering;
import de.lmu.ifi.dbs.elki.data.NumberVector;
import de.lmu.ifi.dbs.elki.data.model.EMModel;
import de.lmu.ifi.dbs.elki.database.Database;
import de.lmu.ifi.dbs.elki.math.random.RandomFactory;
import fr.icam.elki.configurations.EMElkiConfiguration;

public class EMElkiProcessor implements ElkiProcessor<EMModel> {

	private EMElkiConfiguration configuration;
	
	public EMElkiProcessor(EMElkiConfiguration configuration) {
		this.configuration = configuration;
	}
	
	@Override
	public Clustering<EMModel> doProcess(Database database) throws Exception {
		RandomlyGeneratedInitialMeans init = new RandomlyGeneratedInitialMeans(RandomFactory.DEFAULT);
		MultivariateGaussianModelFactory<NumberVector> fact = new MultivariateGaussianModelFactory<NumberVector>(init);
		EM<NumberVector, EMModel> em = new EM<NumberVector, EMModel>(configuration.getLength(), configuration.getDelta(), fact, configuration.getLimit(), false);
	    return em.run(database);
	}

}
