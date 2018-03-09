package fr.icam.elki.distances;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import de.lmu.ifi.dbs.elki.data.NumberVector;
import de.lmu.ifi.dbs.elki.distance.distancefunction.NumberVectorDistanceFunction;
import de.lmu.ifi.dbs.elki.distance.distancefunction.minkowski.MaximumDistanceFunction;
import de.lmu.ifi.dbs.elki.distance.distancefunction.minkowski.MinimumDistanceFunction;
import de.lmu.ifi.dbs.elki.distance.distancefunction.minkowski.SquaredEuclideanDistanceFunction;

public class ElkiDistanceManager implements ServletContextListener {

	private Map<String, NumberVectorDistanceFunction<NumberVector>> distances;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		distances = new HashMap<String, NumberVectorDistanceFunction<NumberVector>>(3);
		distances.put("squared-euclidean", SquaredEuclideanDistanceFunction.STATIC);
		distances.put("minimum", MinimumDistanceFunction.STATIC);
		distances.put("maximum", MaximumDistanceFunction.STATIC);
		sce.getServletContext().setAttribute("distances", distances);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		distances.clear();
	}

}
