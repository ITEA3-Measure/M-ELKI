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
import fr.icam.elki.distances.Distance;
import fr.icam.elki.identifiers.ElkiAlgorithm;

public class SLinkElkiClustering extends ElkiDistanceClustering<DendrogramModel> {

	private static final long serialVersionUID = 20180305130000L;

	public Integer getSize(Long id) {
		return this.getConfiguration(id).getSlink().getSize();
	}
	
	public void setSize(Long id, String size) throws ServletException {
		if (size == null) {
			throw new ServletException("missing parameter 'size'");
		} else {
			try {
				Integer value = Integer.valueOf(size);
				this.getConfiguration(id).getSlink().setSize(value);
			} catch (Throwable t) {
				throw new ServletException(t);
			}
		}
	}

	@Override
	protected void setDistance(Long id, Distance distance) throws ServletException {
		this.getConfiguration(id).getSlink().setDistance(distance);
	}

	@Override
	protected Distance getDistance(Long id) throws ServletException {
		return this.getConfiguration(id).getSlink().getDistance();
	}
	
	@Override
	public void init() throws ServletException {
		super.init();
		this.setSize(0L, this.getInitParameter("size"));
	}

	@Override
	public final void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Long id = this.getResource(request);
		Map<String, Object> parameters = new HashMap<String, Object>(2);
		parameters.put("distance", this.getDistance(id));
		parameters.put("size", this.getSize(id));
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
		SLINK<NumberVector> slink = new SLINK<NumberVector>(this.getInstanceOf(this.getDistance(id)));
		SimplifiedHierarchyExtraction e = new SimplifiedHierarchyExtraction(slink, this.getSize(id));
	    return e.run(database);
	}
	@Override
	protected ElkiAlgorithm getAlgorithm() {
		return ElkiAlgorithm.SLINK;
	}

}
