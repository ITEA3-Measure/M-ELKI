package fr.icam.elki.identifiers;

import fr.icam.elki.distances.Distance;

public class DBScanElkiConfiguration {

	private Double epsilon;
	
	private Integer size;
	
	private Distance distance;

	public Double getEpsilon() {
		return epsilon;
	}

	public void setEpsilon(Double epsilon) {
		this.epsilon = epsilon;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Distance getDistance() {
		return distance;
	}

	public void setDistance(Distance distance) {
		this.distance = distance;
	}

	public DBScanElkiConfiguration() {
		super();
	}
	
	public DBScanElkiConfiguration(Double epsilon, Integer size, Distance distance) {
		super();
		this.epsilon = epsilon;
		this.size = size;
		this.distance = distance;
	}
	
	public DBScanElkiConfiguration(DBScanElkiConfiguration configuration) {
		super();
		this.epsilon = configuration.getEpsilon();
		this.size = configuration.getSize();
		this.distance = new Distance(configuration.getDistance());
	}
	
}
