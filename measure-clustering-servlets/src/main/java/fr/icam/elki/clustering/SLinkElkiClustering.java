package fr.icam.elki.clustering;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.lmu.ifi.dbs.elki.algorithm.clustering.hierarchical.SLINK;
import de.lmu.ifi.dbs.elki.algorithm.clustering.hierarchical.extraction.SimplifiedHierarchyExtraction;
import de.lmu.ifi.dbs.elki.data.Clustering;
import de.lmu.ifi.dbs.elki.data.NumberVector;
import de.lmu.ifi.dbs.elki.data.model.DendrogramModel;
import de.lmu.ifi.dbs.elki.database.Database;

public class SLinkElkiClustering extends ElkiDistanceClustering<DendrogramModel> {

	private static final long serialVersionUID = 20180305130000L;

	private Map<Long, Integer> sizes;
	
	public void setSize(Long id, String size) throws ServletException {
		if (size == null) {
			throw new ServletException("missing parameter 'size'");
		} else {
			try {
				this.sizes.put(id, Integer.valueOf(size));
			} catch (Throwable t) {
				throw new ServletException(t);
			}
		}
	}
	
	@Override
	public void init() throws ServletException {
		super.init();
		this.sizes = new HashMap<Long, Integer>(128);
		this.setSize(0L, this.getInitParameter("size"));
	}

	@Override
	public final void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Long id = this.getResource(request);
		Map<String, Object> parameters = new HashMap<String, Object>(2);
		parameters.put("distance", this.getDistance(id));
		parameters.put("size", sizes.get(id));
		this.getMapper().toJson(parameters, response.getWriter());
	}

	@Override
	protected boolean setUp(Long id, HttpServletRequest request) throws ServletException {
		super.setUp(id, request);
		this.setSize(id, request.getParameter("size"));
		return true;
	}
	
	@Override
	protected Clustering<DendrogramModel> doProcess(Long id, Database database) throws ServletException {
		SLINK<NumberVector> slink = new SLINK<NumberVector>(distances.get(id));
		SimplifiedHierarchyExtraction e = new SimplifiedHierarchyExtraction(slink, sizes.get(id));
	    return e.run(database);
	}

}
