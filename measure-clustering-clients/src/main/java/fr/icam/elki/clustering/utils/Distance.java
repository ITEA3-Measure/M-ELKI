package fr.icam.elki.clustering.utils;

public enum Distance {

	SquaredEuclidean("");
	
	private String name;
	
	public String toString() {
		return this.name;
	}
	
	Distance(String name) {
		this.name= name;
	}
	
}
