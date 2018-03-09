package fr.icam.elki.clustering;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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
import de.lmu.ifi.dbs.elki.distance.distancefunction.minkowski.SquaredEuclideanDistanceFunction;

public abstract class ElkiClustering<M extends Model> extends HttpServlet {

	private static final long serialVersionUID = 20180305100000L;
	
	private Gson gson;
	
	private NumberVectorDistanceFunction<NumberVector> distance;
	
	public void init() throws ServletException {
		gson = new Gson();
		distance = SquaredEuclideanDistanceFunction.STATIC;
	}
	
	protected final NumberVectorDistanceFunction<NumberVector> getDistance() {
		return distance;
	}
	
	protected final void setDistance(String name) throws Exception {
		if (name == null) {
			
		} else if (name.equalsIgnoreCase("squared-euclidean")) {
			distance = SquaredEuclideanDistanceFunction.STATIC;	
		} else {
			
		}
	}

	protected abstract boolean setUp(HttpServletRequest request) throws Exception;
	
	@Override
	public final void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		try {
			this.setDistance(request.getParameter("distance"));
			boolean done = this.setUp(request);
			response.getWriter().write(done ? "true" : "false");
		} catch (Exception e) {
			response.sendError(520, e.getMessage());
		}
	}

	@Override
	public final void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		try {
			Database database = this.getDatabase(request.getInputStream());
			Clustering<M> clusters = this.doProcess(database);
			this.doPrint(database, clusters, response.getOutputStream());
		} catch (Exception e) {
			response.sendError(520, e.getMessage());
		}
	}

	protected abstract Clustering<M> doProcess(Database database) throws Exception;

	protected final Database getDatabase(InputStream input) throws Exception {
	    MultipleObjectsBundle bundle = new MultipleObjectsBundle();
	    List<DoubleVector> vectors = new LinkedList<DoubleVector>();
	    List<ExternalID> identifiers = new LinkedList<ExternalID>();
	    int size = this.doFill(vectors, identifiers, input);
	    SimpleTypeInformation<DoubleVector> type = new VectorFieldTypeInformation<DoubleVector>(DoubleVector.FACTORY, size, size, DoubleVector.FACTORY.getDefaultSerializer());
	    bundle.appendColumn(type, vectors);      
	    bundle.appendColumn(TypeUtil.EXTERNALID, identifiers);
	    DatabaseConnection dbc = new MultipleObjectsBundleDatabaseConnection(bundle);
	    Database db = new StaticArrayDatabase(dbc, null);
	    db.initialize();
		return db;
	}

	private int doFill(List<DoubleVector> vectors, List<ExternalID> identifiers, InputStream input) throws Exception {
		Integer size = null;
		Scanner scanner = new Scanner(input);
		try {
			boolean first = true;
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (first) {
					size = this.doMeta(vectors, identifiers, line);
					first = false;
				} else {
					this.doData(vectors, identifiers, line, size);
				}
			}
		} finally {
			scanner.close();
		}
		return size.intValue();
	}

	private int doMeta(List<DoubleVector> vectors, List<ExternalID> identifiers, String line) {
		/* Integer length = null; */
		Integer size = null;
		Scanner scanner = new Scanner(line);
		scanner.useDelimiter(",");
		try {
			while (scanner.hasNext()) {
				/* length = */ scanner.nextInt();
				size = scanner.nextInt();
			}
		} finally {
			scanner.close();
		}
		return size.intValue();
	}

	private void doData(List<DoubleVector> vectors, List<ExternalID> identifiers, String line, int size) throws Exception {
		Long id = null;
		int i = 0;
		double[] values = new double[size];
		Scanner scanner = new Scanner(line);
		scanner.useDelimiter(",");
		try {
			boolean first = true;
			while (scanner.hasNext()) {
				String item = scanner.next();
				if (first) {
					id = Long.valueOf(item).longValue();
					first = false;
				} else {
					double value = Double.valueOf(item).doubleValue();
					values[i] = value;
					i++;
				}
			}
		} finally {
			scanner.close();
		}
		ExternalID extid = new ExternalID(id.toString());
        identifiers.add(extid);
		DoubleVector vector = new DoubleVector(values);
		vectors.add(vector);
	}

	protected void doPrint(Database db, Clustering<M> clusters, OutputStream output) throws Exception {
	    // Relation<NumberVector> vectors = db.getRelation(TypeUtil.NUMBER_VECTOR_FIELD);
	    Relation<ExternalID> identifiers = db.getRelation(TypeUtil.EXTERNALID);
	    JsonArray jClusters = new JsonArray();
	    for(Cluster<M> cluster : clusters.getAllClusters()) {
			JsonObject jCluster = new JsonObject();
			jCluster.addProperty("name", cluster.getNameAutomatic());
			JsonArray jVectors = new JsonArray();
			for(DBIDIter iter = cluster.getIDs().iter(); iter.valid(); iter.advance()) {
				ExternalID id = identifiers.get(iter);
				JsonObject jVector = new JsonObject();
				jVector.addProperty("id", id.toString());
				jVectors.add(jVector);
			}
			jCluster.add("instances", jVectors);
			jClusters.add(jCluster);
	    }
	    String json = gson.toJson(jClusters);
	    output.write(json.getBytes());
	}

}
