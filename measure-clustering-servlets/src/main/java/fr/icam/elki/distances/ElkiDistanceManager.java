package fr.icam.elki.distances;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import de.lmu.ifi.dbs.elki.data.NumberVector;
import de.lmu.ifi.dbs.elki.distance.distancefunction.ArcCosineDistanceFunction;
import de.lmu.ifi.dbs.elki.distance.distancefunction.BrayCurtisDistanceFunction;
import de.lmu.ifi.dbs.elki.distance.distancefunction.CanberraDistanceFunction;
import de.lmu.ifi.dbs.elki.distance.distancefunction.ClarkDistanceFunction;
import de.lmu.ifi.dbs.elki.distance.distancefunction.CosineDistanceFunction;
import de.lmu.ifi.dbs.elki.distance.distancefunction.Kulczynski1DistanceFunction;
import de.lmu.ifi.dbs.elki.distance.distancefunction.LorentzianDistanceFunction;
import de.lmu.ifi.dbs.elki.distance.distancefunction.NumberVectorDistanceFunction;
import de.lmu.ifi.dbs.elki.distance.distancefunction.minkowski.EuclideanDistanceFunction;
import de.lmu.ifi.dbs.elki.distance.distancefunction.minkowski.ManhattanDistanceFunction;
import de.lmu.ifi.dbs.elki.distance.distancefunction.minkowski.MaximumDistanceFunction;
import de.lmu.ifi.dbs.elki.distance.distancefunction.minkowski.MinimumDistanceFunction;
import de.lmu.ifi.dbs.elki.distance.distancefunction.minkowski.SquaredEuclideanDistanceFunction;

public class ElkiDistanceManager implements ServletContextListener {

	private Map<String, NumberVectorDistanceFunction<NumberVector>> distances;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		distances = new HashMap<String, NumberVectorDistanceFunction<NumberVector>>(3);
		distances.put("euclidean", EuclideanDistanceFunction.STATIC);
		distances.put("manhattan", ManhattanDistanceFunction.STATIC);
		distances.put("minimum", MinimumDistanceFunction.STATIC);
		distances.put("maximum", MaximumDistanceFunction.STATIC);
		distances.put("squared-euclidean", SquaredEuclideanDistanceFunction.STATIC);
		distances.put("arc-cosine", ArcCosineDistanceFunction.STATIC);
		distances.put("cosine", CosineDistanceFunction.STATIC);
		distances.put("bray-curtis", BrayCurtisDistanceFunction.STATIC_CONTINUOUS);
		distances.put("canberra", CanberraDistanceFunction.STATIC);
		distances.put("clark", ClarkDistanceFunction.STATIC);
		distances.put("kulczynski1", Kulczynski1DistanceFunction.STATIC);
		distances.put("lorentzian", LorentzianDistanceFunction.STATIC);
		sce.getServletContext().setAttribute("distances", distances);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		distances.clear();
	}

}
