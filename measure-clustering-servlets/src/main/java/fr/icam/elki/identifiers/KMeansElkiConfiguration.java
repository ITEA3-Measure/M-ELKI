package fr.icam.elki.identifiers;

import fr.icam.elki.distances.Distance;

public class KMeansElkiConfiguration {

	private Integer length;
	
	private Integer limit;
	
	private Distance distance;

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Distance getDistance() {
		return distance;
	}

	public void setDistance(Distance distance) {
		this.distance = distance;
	}

	public KMeansElkiConfiguration() {
		super();
	}
	
	public KMeansElkiConfiguration(Integer length, Integer limit, Distance distance) {
		super();
		this.length = length;
		this.limit = limit;
		this.distance = distance;
	}

	public KMeansElkiConfiguration(KMeansElkiConfiguration configuration) {
		super();
		this.length = configuration.getLength();
		this.limit = configuration.getLimit();
		this.distance = new Distance(configuration.getDistance());
	}
	
}