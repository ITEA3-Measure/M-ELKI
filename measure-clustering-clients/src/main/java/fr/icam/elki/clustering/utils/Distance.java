package fr.icam.elki.clustering.utils;

public enum Distance {

	SquaredEuclidean("squared-euclidean"),
	Minimum("minimum"),
	Maximum("maximum");
	
	private String name;
	
	public String toString() {
		return this.name;
	}
	
	Distance(String name) {
		this.name= name;
	}
	
}
