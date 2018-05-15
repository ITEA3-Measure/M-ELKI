package fr.icam.elki.identifiers;

import java.util.LinkedList;
import java.util.List;

import eu.measure.platform.api.MeasureInstance;
import fr.icam.elki.distances.Distance;

public class ElkiConfiguration {
	
	private ElkiAlgorithm algorithm;
	
	private DBScanElkiConfiguration dbscan;
	
	private KMeansElkiConfiguration kmeans;
	
	private EMElkiConfiguration em;
	
	private SLinkElkiConfiguration slink;

	private Long project;

	public Long getProject() {
		return project;
	}

	public void setProject(Long project) {
		this.project = project;
	}
	
	private List<MeasureInstance> measures;

	public List<MeasureInstance> getMeasures() {
		return measures;
	}

	public void setMeasures(List<MeasureInstance> measures) {
		this.measures = measures;
	}
	
	public DBScanElkiConfiguration getDbscan() {
		return dbscan;
	}

	public void setDbscan(Double epsilon, Integer size, Distance distance) {
		this.dbscan = new DBScanElkiConfiguration(epsilon, size, distance);
	}

	public KMeansElkiConfiguration getKmeans() {
		return kmeans;
	}

	public void setKmeans(Integer length, Integer limit, Distance distance) {
		this.kmeans = new KMeansElkiConfiguration(length, limit, distance);
	}

	public EMElkiConfiguration getEm() {
		return em;
	}

	public void setEm(Integer length, Double delta, Integer limit) {
		this.em = new EMElkiConfiguration(length, delta, limit);
	}

	public SLinkElkiConfiguration getSlink() {
		return slink;
	}

	public void setSlink(Integer size, Distance distance) {
		this.slink = new SLinkElkiConfiguration(size, distance);
	}
	
	public boolean isSelected(ElkiAlgorithm algorithm) {
		return this.algorithm == algorithm;
	}
	
	public void doSelect(ElkiAlgorithm algorithm) {
		this.algorithm = algorithm;
	}
	
	public ElkiConfiguration() {
		super();
		this.dbscan = new DBScanElkiConfiguration();
		this.kmeans = new KMeansElkiConfiguration();
		this.em = new EMElkiConfiguration();
		this.slink = new SLinkElkiConfiguration();
		this.doSelect(ElkiAlgorithm.DBSCAN);
		this.setMeasures(new LinkedList<MeasureInstance>());
	}
	
	public ElkiConfiguration(ElkiConfiguration configuration) {
		super();
		this.dbscan = new DBScanElkiConfiguration(configuration.getDbscan());
		this.kmeans = new KMeansElkiConfiguration(configuration.getKmeans());
		this.em = new EMElkiConfiguration(configuration.getEm());
		this.slink = new SLinkElkiConfiguration(configuration.getSlink());
		this.doSelect(ElkiAlgorithm.DBSCAN);
		this.setMeasures(new LinkedList<MeasureInstance>());
	}
	
}
