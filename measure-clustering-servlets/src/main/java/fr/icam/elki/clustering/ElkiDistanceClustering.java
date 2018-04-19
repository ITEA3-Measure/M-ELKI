package fr.icam.elki.clustering;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import de.lmu.ifi.dbs.elki.data.NumberVector;
import de.lmu.ifi.dbs.elki.data.model.Model;
import de.lmu.ifi.dbs.elki.distance.distancefunction.NumberVectorDistanceFunction;
import fr.icam.elki.distances.Distance;

public abstract class ElkiDistanceClustering<M extends Model> extends ElkiClustering<M> {

	private static final long serialVersionUID = 20180305140000L;
	
	private Map<String, NumberVectorDistanceFunction<NumberVector>> availableDistances;

	protected Map<Long, NumberVectorDistanceFunction<NumberVector>> distances;
	
	protected final void setDistance(Long id, String name) throws ServletException {
		if (name == null) {
			throw new ServletException("missing parameter 'distance' for id=" + id);
		} else {
			NumberVectorDistanceFunction<NumberVector> distance = availableDistances.get(name);
			if (distance == null) {
				throw new ServletException("unknown parameter 'distance' value: '" + name + "'");
			} else {
				this.distances.put(id, distance);
			}
		}
	}
	
	protected final Distance getDistance(Long id) throws ServletException {
		if (distances.get(id) == null) {
			throw new ServletException("undefined distance for id=" + id);
		} else {
			for (String name : availableDistances.keySet()) {
				NumberVectorDistanceFunction<NumberVector> distance = availableDistances.get(name);
				String className = distance.getClass().getCanonicalName();
				String distanceClassName = this.distances.get(id).getClass().getCanonicalName();
				if (className.compareTo(distanceClassName) == 0) {
					return new Distance(name, name.replace("-", " "));
				}
			}
			throw new ServletException("unknown distance class name '" + distances.getClass().getSimpleName() + "' for id=" + 0);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void init() throws ServletException {
		super.init();
		this.distances = new HashMap<Long, NumberVectorDistanceFunction<NumberVector>>(128);
		availableDistances = (Map<String, NumberVectorDistanceFunction<NumberVector>>) this.getServletContext().getAttribute("distances");
		this.setDistance(0L, this.getInitParameter("distance"));
	}

	@Override
	protected boolean setUp(Long id, HttpServletRequest request) throws ServletException {
		this.setDistance(id, request.getParameter("distance"));
		return true;
	}
	
}
