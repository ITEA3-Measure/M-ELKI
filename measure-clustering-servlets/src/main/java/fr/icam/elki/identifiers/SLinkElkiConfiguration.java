package fr.icam.elki.identifiers;

import fr.icam.elki.distances.Distance;

public class SLinkElkiConfiguration {

	private Integer size;
	
	private Distance distance;

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

	public SLinkElkiConfiguration() {
		super();
	}
	
	public SLinkElkiConfiguration(Integer size, Distance distance) {
		super();
		this.size = size;
		this.distance = distance;
	}

	public SLinkElkiConfiguration(SLinkElkiConfiguration configuration) {
		super();
		this.size = configuration.getSize();
		this.distance = new Distance(configuration.getDistance());
	}
	
}
