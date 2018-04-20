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
	
	protected abstract void setDistance(Long id, Distance distance) throws ServletException;
	
	protected abstract Distance getDistance(Long id) throws ServletException;
	
	protected final Distance getDistance(String name) throws ServletException {
		NumberVectorDistanceFunction<NumberVector> distance = distances.get(name);
		if (distance == null) {
			throw new ServletException("unknown distance class name '" + name + "'");
		} else {
			return new Distance(name, name.replace("-", " "));
		}
	}

	protected final NumberVectorDistanceFunction<NumberVector> getInstanceOf(Distance distance) throws ServletException {
		if (distance == null) {
			throw new ServletException("undefined parameter 'distance'");
		} else {
			NumberVectorDistanceFunction<NumberVector> d = distances.get(distance.getId());
			if (d == null) {
				throw new ServletException("unknown parameter 'distance' value: '" + distance.getName() + "'");
			} else {
				return d;
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void init() throws ServletException {
		super.init();
		distances = (Map<String, NumberVectorDistanceFunction<NumberVector>>) this.getServletContext().getAttribute("distances");
		this.setDistance(0L, this.getDistance(this.getInitParameter("distance")));
	}

	@Override
	protected boolean setUp(Long id, HttpServletRequest request) throws ServletException {
		this.setDistance(id, this.getDistance(request.getParameter("distance")));
		return true;
	}
	
}
