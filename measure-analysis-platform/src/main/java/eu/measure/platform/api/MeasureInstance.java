package eu.measure.platform.api;

public class MeasureInstance {

    private Long id;

    private String instanceName;

    private String instanceDescription;

    private String measureName;

    private String measureVersion;

    private Boolean isShedule;

    private String shedulingExpression;

    private MeasureType measureType;

    private Boolean manageLastMeasurement;

    private String remoteAdress;

    private String remoteLabel;

    private Boolean isRemote;

    private Project project;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public String getInstanceDescription() {
		return instanceDescription;
	}

	public void setInstanceDescription(String instanceDescription) {
		this.instanceDescription = instanceDescription;
	}

	public String getMeasureName() {
		return measureName;
	}

	public void setMeasureName(String measureName) {
		this.measureName = measureName;
	}

	public String getMeasureVersion() {
		return measureVersion;
	}

	public void setMeasureVersion(String measureVersion) {
		this.measureVersion = measureVersion;
	}

	public Boolean getIsShedule() {
		return isShedule;
	}

	public void setIsShedule(Boolean isShedule) {
		this.isShedule = isShedule;
	}

	public String getShedulingExpression() {
		return shedulingExpression;
	}

	public void setShedulingExpression(String shedulingExpression) {
		this.shedulingExpression = shedulingExpression;
	}

	public MeasureType getMeasureType() {
		return measureType;
	}

	public void setMeasureType(MeasureType measureType) {
		this.measureType = measureType;
	}

	public Boolean getManageLastMeasurement() {
		return manageLastMeasurement;
	}

	public void setManageLastMeasurement(Boolean manageLastMeasurement) {
		this.manageLastMeasurement = manageLastMeasurement;
	}

	public String getRemoteAdress() {
		return remoteAdress;
	}

	public void setRemoteAdress(String remoteAdress) {
		this.remoteAdress = remoteAdress;
	}

	public String getRemoteLabel() {
		return remoteLabel;
	}

	public void setRemoteLabel(String remoteLabel) {
		this.remoteLabel = remoteLabel;
	}

	public Boolean getIsRemote() {
		return isRemote;
	}

	public void setIsRemote(Boolean isRemote) {
		this.isRemote = isRemote;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}