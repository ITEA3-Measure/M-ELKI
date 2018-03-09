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

	private int length;
	
	private int limit;
	
	public void setLength(String length) throws ServletException {
		if (length == null) {
			throw new ServletException("missing parameter 'length'");
		} else {
			try {
				this.length = Integer.valueOf(length).intValue();
			} catch (Throwable t) {
				throw new ServletException(t);
			}
		}
	}

	public void setLimit(String limit) throws ServletException {
		if (limit == null) {
			throw new ServletException("missing parameter 'limit'");
		} else {
			try {
				this.limit = Integer.valueOf(limit).intValue();
			} catch (Throwable t) {
				throw new ServletException(t);
			}
		}
	}
	
	@Override
	public void init() throws ServletException {
		super.init();
		this.setLength(this.getInitParameter("length"));
		this.setLimit(this.getInitParameter("limit"));
	}

	@Override
	public final void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Map<String, Object> parameters = new HashMap<String, Object>(3);
		parameters.put("distance", this.getDistance());
		parameters.put("length", length);
		parameters.put("limit", limit);
		this.getMapper().toJson(parameters, response.getWriter());
	}

	@Override
	protected boolean setUp(HttpServletRequest request) throws ServletException {
		super.setUp(request);
		this.setLength(request.getParameter("length"));
		this.setLimit(request.getParameter("limit"));
		return true;
	}
	
	@Override
	protected Clustering<KMeansModel> doProcess(Database database) throws ServletException {
	    RandomlyGeneratedInitialMeans init = new RandomlyGeneratedInitialMeans(RandomFactory.DEFAULT);
	    KMeansLloyd<NumberVector> km = new KMeansLloyd<NumberVector>(distance, length, limit, init);
	    return km.run(database);
	}
	
}
