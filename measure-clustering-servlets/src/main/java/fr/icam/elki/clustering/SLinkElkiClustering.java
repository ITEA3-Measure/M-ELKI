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

	private int size;
	
	public void setSize(String size) throws ServletException {
		if (size == null) {
			throw new ServletException("missing parameter 'size'");
		} else {
			try {
				this.size = Integer.valueOf(size).intValue();
			} catch (Throwable t) {
				throw new ServletException(t);
			}
		}
	}
	
	@Override
	public void init() throws ServletException {
		super.init();
		this.setSize(this.getInitParameter("size"));
	}

	@Override
	public final void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Map<String, Object> parameters = new HashMap<String, Object>(2);
		parameters.put("distance", this.getDistance());
		parameters.put("size", size);
		this.getMapper().toJson(parameters, response.getWriter());
	}

	@Override
	protected boolean setUp(HttpServletRequest request) throws ServletException {
		super.setUp(request);
		this.setSize(request.getParameter("size"));
		return true;
	}
	
	@Override
	protected Clustering<DendrogramModel> doProcess(Database database) throws ServletException {
		SLINK<NumberVector> slink = new SLINK<NumberVector>(distance);
		SimplifiedHierarchyExtraction e = new SimplifiedHierarchyExtraction(slink, size);
	    return e.run(database);
	}

}
