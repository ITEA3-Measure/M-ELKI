package fr.icam.elki.processing;

import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import de.lmu.ifi.dbs.elki.data.Cluster;
import de.lmu.ifi.dbs.elki.data.Clustering;
import de.lmu.ifi.dbs.elki.data.DoubleVector;
import de.lmu.ifi.dbs.elki.data.ExternalID;
import de.lmu.ifi.dbs.elki.data.NumberVector;
import de.lmu.ifi.dbs.elki.data.model.Model;
import de.lmu.ifi.dbs.elki.data.type.SimpleTypeInformation;
import de.lmu.ifi.dbs.elki.data.type.TypeUtil;
import de.lmu.ifi.dbs.elki.data.type.VectorFieldTypeInformation;
import de.lmu.ifi.dbs.elki.database.Database;
import de.lmu.ifi.dbs.elki.database.StaticArrayDatabase;
import de.lmu.ifi.dbs.elki.database.ids.DBIDIter;
import de.lmu.ifi.dbs.elki.database.relation.Relation;
import de.lmu.ifi.dbs.elki.datasource.DatabaseConnection;
import de.lmu.ifi.dbs.elki.datasource.MultipleObjectsBundleDatabaseConnection;
import de.lmu.ifi.dbs.elki.datasource.bundle.MultipleObjectsBundle;
import de.lmu.ifi.dbs.elki.distance.distancefunction.NumberVectorDistanceFunction;
import fr.icam.elki.configurations.ElkiAlgorithm;
import fr.icam.elki.configurations.ElkiConfiguration;
import fr.icam.elki.distances.Distance;

public class ElkiProcessing extends HttpServlet {

	private static final long serialVersionUID = 20180518100000L;
		
	private Map<Long, ElkiConfiguration> configurations;
	
	protected ElkiConfiguration getConfiguration(Long id) {
		return configurations.get(id);
	}
	
	private Gson mapper;
	
	protected Gson getMapper() {
		return mapper;
	}
	
	private Map<String, NumberVectorDistanceFunction<NumberVector>> distances;
		
	protected final NumberVectorDistanceFunction<NumberVector> getInstanceOf(Distance distance) throws ServletException {
		if (distance == null) {
			throw new ServletException("undefined parameter 'distance'");
		} else {
			NumberVectorDistanceFunction<NumberVector> d = distances.get(distance.getId());
			if (d == null) {
				throw new ServletException("unknown parameter 'distance' value: '" + distance.getName() + "'");
			} else {
				return d;
			}
		}
	}
		
	@SuppressWarnings("unchecked")
	@Override
	public void init() throws ServletException {
		super.init();
		mapper = new Gson();
		configurations = (Map<Long, ElkiConfiguration>) this.getServletContext().getAttribute("configurations");
		distances = (Map<String, NumberVectorDistanceFunction<NumberVector>>) this.getServletContext().getAttribute("distances");
	}

	private Database database;
		
	@Override
	public final void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		try {
			Long id = this.getResource(request);
			ElkiConfiguration configuration = this.getConfiguration(id);
			ElkiProcessor<? extends Model> processor = this.getProcessor(configuration);
			if (database == null) {
				database = this.doGenerate();
			}
			Clustering<? extends Model> clustering = processor.doProcess(database);
			this.doPrint(database, clustering, response.getOutputStream());				
		} catch (Exception e) {
			response.sendError(520, e.getMessage());
		}
	}
	
	private ElkiProcessor<? extends Model> getProcessor(ElkiConfiguration configuration) {
		if (configuration.isSelected(ElkiAlgorithm.DBSCAN)) {
			return new DBScanElkiProcessor(distances, configuration.getDbscan());
		} else if (configuration.isSelected(ElkiAlgorithm.KMEANS)) {
			return new KMeansElkiProcessor(distances, configuration.getKmeans());
		} else if (configuration.isSelected(ElkiAlgorithm.EM)) {
			return new EMElkiProcessor(configuration.getEm());
		} else if (configuration.isSelected(ElkiAlgorithm.SLINK)) {
			return new SLinkElkiProcessor(distances, configuration.getSlink());
		} else {
			throw new NullPointerException();
		}
	}

	protected Long getResource(HttpServletRequest request) throws ServletException {
		return (Long) request.getAttribute("id");
	}
	
	private final Database doGenerate() throws Exception {
	    MultipleObjectsBundle bundle = new MultipleObjectsBundle();
	    List<DoubleVector> vectors = new LinkedList<DoubleVector>();
	    List<ExternalID> identifiers = new LinkedList<ExternalID>();
	    int size = this.doFill(vectors, identifiers);
	    SimpleTypeInformation<DoubleVector> type = new VectorFieldTypeInformation<DoubleVector>(DoubleVector.FACTORY, size, size, DoubleVector.FACTORY.getDefaultSerializer());
	    bundle.appendColumn(type, vectors);      
	    bundle.appendColumn(TypeUtil.EXTERNALID, identifiers);
	    DatabaseConnection dbc = new MultipleObjectsBundleDatabaseConnection(bundle);
	    Database database = new StaticArrayDatabase(dbc, null);
	    database.initialize();
	    return database;
	}

	private int doFill(List<DoubleVector> vectors, List<ExternalID> identifiers) throws Exception {
		Random random = new Random();
		int size = 1;
		for (int i = 0; i < 1000; i++) {
			ExternalID extid = new ExternalID(Integer.toString(i));
	        identifiers.add(extid);
			double[] values = new double[size];
			for (int j = 0; j < size; j++) {
				values[j] = Double.valueOf(random.nextDouble() * 100).intValue();
			}
			DoubleVector vector = new DoubleVector(values);
			vectors.add(vector);
		}
		return size;
	}

	private void doPrint(Database db, Clustering<? extends Model> clusters, OutputStream output) throws Exception {
		JsonElement jPoints = this.getPoints(db);
		JsonElement jClusters = this.getClusters(db, clusters);
		JsonObject jObject = new JsonObject();
		jObject.add("points", jPoints);
		jObject.add("clusters", jClusters);
		String json = mapper.toJson(jObject);
		output.write(json.getBytes());
	}
	
	private JsonElement getClusters(Database db, Clustering<? extends Model> clusters) {
	    Relation<ExternalID> identifiers = db.getRelation(TypeUtil.EXTERNALID);
	    JsonArray jClusters = new JsonArray();
	    int i = 0;
	    for(Cluster<? extends Model> cluster : clusters.getAllClusters()) {
			JsonObject jCluster = new JsonObject();
			String label = cluster.getNameAutomatic();
			if (label.equalsIgnoreCase("noise")) {
				continue;
			} else {
				i++;
				label = "Cluster " + i;
			}
			jCluster.addProperty("label", label);
			JsonArray jVectors = new JsonArray();
			for(DBIDIter iter = cluster.getIDs().iter(); iter.valid(); iter.advance()) {
				ExternalID id = identifiers.get(iter);
				Long nid = Long.valueOf(id.toString());
				JsonPrimitive jVector = new JsonPrimitive(nid);
				jVectors.add(jVector);
			}
			jCluster.add("cluster", jVectors);
			jClusters.add(jCluster);
	    }
	    return jClusters;
	}

	private JsonElement getPoints(Database db) {
	    Relation<NumberVector> vectors = db.getRelation(TypeUtil.NUMBER_VECTOR_FIELD);
	    Relation<ExternalID> identifiers = db.getRelation(TypeUtil.EXTERNALID);
	    JsonArray jVectors = new JsonArray();
		for(DBIDIter iter = identifiers.iterDBIDs(); iter.valid(); iter.advance()) {
			ExternalID id = identifiers.get(iter);
			NumberVector vector = vectors.get(iter);
			JsonObject jVector = new JsonObject();
			Long nid = Long.valueOf(id.toString());
			jVector.addProperty("id", nid);
			JsonArray jValues = new JsonArray();
			for (int i = 0; i < vector.getDimensionality(); i++) {
				jValues.add(vector.doubleValue(i));	
			}
			jVector.add("values", jValues);
			jVectors.add(jVector);
		}
		return jVectors;
	}

}
