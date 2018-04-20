package fr.icam.elki.distances;

public class Distance {

	private String id;
	
	private String name;

	public String getId() {
		return id;
	}

	private void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public Distance(String id, String name) {
		super();
		this.setId(id);
		this.setName(name);
	}

	public Distance(Distance distance) {
		super();
		this.setId(distance.getId());
		this.setName(distance.getName());
	}
	
}
