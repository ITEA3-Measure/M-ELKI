package fr.icam.elki.configurations;

public class EMElkiConfiguration {

	private Integer length;
	
	private Double delta;
	
	private Integer limit;

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Double getDelta() {
		return delta;
	}

	public void setDelta(Double delta) {
		this.delta = delta;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public EMElkiConfiguration() {
		super();
	}
	
	public EMElkiConfiguration(Integer length, Double delta, Integer limit) {
		super();
		this.length = length;
		this.delta = delta;
		this.limit = limit;
	}

	public EMElkiConfiguration(EMElkiConfiguration configuration) {
		super();
		this.length = configuration.getLength();
		this.delta = configuration.getDelta();
		this.limit = configuration.getLimit();
	}
	
}
