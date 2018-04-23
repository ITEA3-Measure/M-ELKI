package fr.icam.elki.clustering;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.lmu.ifi.dbs.elki.algorithm.clustering.kmeans.KMeansLloyd;
import de.lmu.ifi.dbs.elki.algorithm.clustering.kmeans.initialization.RandomlyGeneratedInitialMeans;
import de.lmu.ifi.dbs.elki.data.Clustering;
import de.lmu.ifi.dbs.elki.data.NumberVector;
import de.lmu.ifi.dbs.elki.data.model.KMeansModel;
import de.lmu.ifi.dbs.elki.database.Database;
import de.lmu.ifi.dbs.elki.math.random.RandomFactory;
import fr.icam.elki.distances.Distance;
import fr.icam.elki.identifiers.ElkiAlgorithm;

public class KMeansElkiClustering extends ElkiDistanceClustering<KMeansModel> {

	private static final long serialVersionUID = 20180305120000L;
	
	public Integer getLength(Long id) {
		return this.getConfiguration(id).getKmeans().getLength();
	}

	public void setLength(Long id, String length) throws ServletException {
		if (length == null) {
			throw new ServletException("missing parameter 'length'");
		} else {
			try {
				Integer value = Integer.valueOf(length);
				this.getConfiguration(id).getKmeans().setLength(value);
			} catch (Throwable t) {
				throw new ServletException(t);
			}
		}
	}
	
	public Integer getLimit(Long id) {
		return this.getConfiguration(id).getKmeans().getLimit();
	}

	public void setLimit(Long id, String limit) throws ServletException {
		if (limit == null) {
			throw new ServletException("missing parameter 'limit'");
		} else {
			try {
				Integer value = Integer.valueOf(limit);
				this.getConfiguration(id).getKmeans().setLimit(value);
			} catch (Throwable t) {
				throw new ServletException(t);
			}
		}
	}

	@Override
	protected void setDistance(Long id, Distance distance) throws ServletException {
		this.getConfiguration(id).getKmeans().setDistance(distance);
	}

	@Override
	protected Distance getDistance(Long id) throws ServletException {
		return this.getConfiguration(id).getKmeans().getDistance();
	}
	
	@Override
	public void init() throws ServletException {
		super.init();
		this.setLength(0L, this.getInitParameter("length"));
		this.setLimit(0L, this.getInitParameter("limit"));
	}

	@Override
	public final void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Long id = this.getResource(request);
		Map<String, Object> parameters = new HashMap<String, Object>(3);
		parameters.put("distance", this.getDistance(id));
		parameters.put("length", this.getLength(id));
		parameters.put("limit", this.getLimit(id));
		this.getMapper().toJson(parameters, response.getWriter());
	}

	@Override
	protected boolean setUp(Long id, HttpServletRequest request) throws ServletException {
		super.setUp(id, request);
		this.setLength(id, request.getParameter("length"));
		this.setLimit(id, request.getParameter("limit"));
		return true;
	}
	
	@Override
	protected Clustering<KMeansModel> doProcess(Long id, Database database) throws ServletException {
	    RandomlyGeneratedInitialMeans init = new RandomlyGeneratedInitialMeans(RandomFactory.DEFAULT);
	    KMeansLloyd<NumberVector> km = new KMeansLloyd<NumberVector>(this.getInstanceOf(this.getDistance(id)), this.getLength(id), this.getLimit(id), init);
	    return km.run(database);
	}
	@Override
	protected ElkiAlgorithm getAlgorithm() {
		return ElkiAlgorithm.KMEANS;
	}
	
}
