package fr.icam.elki.clustering.utils;

import java.util.List;

public class Cluster {

	private String name;
	
	List<Id> instances;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Id> getInstances() {
		return instances;
	}

	public void setInstances(List<Id> instances) {
		this.instances = instances;
	}
	
}
