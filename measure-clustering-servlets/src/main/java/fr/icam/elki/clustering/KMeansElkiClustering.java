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

public class KMeansElkiClustering extends ElkiDistanceClustering<KMeansModel> {

	private static final long serialVersionUID = 20180305120000L;

	private Map<Long, Integer> lengths;
	
	private Map<Long, Integer> limits;
	
	public void setLength(Long id, String length) throws ServletException {
		if (length == null) {
			throw new ServletException("missing parameter 'length'");
		} else {
			try {
				this.lengths.put(id, Integer.valueOf(length));
			} catch (Throwable t) {
				throw new ServletException(t);
			}
		}
	}

	public void setLimit(Long id, String limit) throws ServletException {
		if (limit == null) {
			throw new ServletException("missing parameter 'limit'");
		} else {
			try {
				this.limits.put(id, Integer.valueOf(limit));
			} catch (Throwable t) {
				throw new ServletException(t);
			}
		}
	}
	
	@Override
	public void init() throws ServletException {
		super.init();
		this.lengths = new HashMap<Long, Integer>(128);
		this.limits = new HashMap<Long, Integer>(128);
		this.setLength(0L, this.getInitParameter("length"));
		this.setLimit(0L, this.getInitParameter("limit"));
	}

	@Override
	public final void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Long id = this.getResource(request);
		Map<String, Object> parameters = new HashMap<String, Object>(3);
		parameters.put("distance", this.getDistance(id));
		parameters.put("length", lengths.get(id));
		parameters.put("limit", limits.get(id));
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
	    KMeansLloyd<NumberVector> km = new KMeansLloyd<NumberVector>(distances.get(id), lengths.get(id), limits.get(id), init);
	    return km.run(database);
	}
	
}
