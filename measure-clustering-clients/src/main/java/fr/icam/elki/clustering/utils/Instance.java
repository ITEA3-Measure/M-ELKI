package fr.icam.elki.clustering.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Instance extends Id implements Iterable<Double> {

	private List<Double> values;

	public Instance(String id, List<Double> values) {
		super(id);
		this.values = values;
	}
	
	public Instance(String id, Double... values) {
		this(id, Arrays.asList(values));
	}
	
	public Instance(String id, double... values) {
		this(id, new ArrayList<Double>(values.length));
		for (double value : values) {
			this.values.add(new Double(value));
		}
	}
	
	public int size() {
		return values.size();
	}
	
	@Override
	public Iterator<Double> iterator() {
		return values.iterator();
	}
	
}
