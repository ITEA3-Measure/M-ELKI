package fr.icam.elki.clustering;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import de.lmu.ifi.dbs.elki.data.NumberVector;
import de.lmu.ifi.dbs.elki.data.model.Model;
import de.lmu.ifi.dbs.elki.distance.distancefunction.NumberVectorDistanceFunction;
import fr.icam.elki.distances.Distance;

public abstract class ElkiDistanceClustering<M extends Model> extends ElkiClustering<M> {

	private static final long serialVersionUID = 20180305140000L;
	
	private Map<String, NumberVectorDistanceFunction<NumberVector>> distances;

	protected NumberVectorDistanceFunction<NumberVector> distance;
	
	protected final void setDistance(String name) throws ServletException {
		if (name == null) {
			throw new ServletException("missing parameter 'distance'");
		} else {
			NumberVectorDistanceFunction<NumberVector> distance = distances.get(name);
			if (distance == null) {
				throw new ServletException("unknown parameter 'distance' value: '" + name + "'");
			} else {
				this.distance = distance;
			}
		}
	}
	
	protected final Distance getDistance() throws ServletException {
		if (distance == null) {
			throw new ServletException("undefined distance");
		} else {
			for (String name : distances.keySet()) {
				NumberVectorDistanceFunction<NumberVector> distance = distances.get(name);
				String className = distance.getClass().getCanonicalName();
				String distanceClassName = this.distance.getClass().getCanonicalName();
				if (className.compareTo(distanceClassName) == 0) {
					return new Distance(name, name.replace("-", " "));
				}
			}
			throw new ServletException("unknown distance class name '" + distance.getClass().getSimpleName() + "'");
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void init() throws ServletException {
		super.init();
		distances = (Map<String, NumberVectorDistanceFunction<NumberVector>>) this.getServletContext().getAttribute("distances");
		this.setDistance(this.getInitParameter("distance"));
	}

	@Override
	protected boolean setUp(HttpServletRequest request) throws ServletException {
		this.setDistance(this.getInitParameter("distance"));
		return true;
	}
	
}
