package fr.icam.elki.processing;

import de.lmu.ifi.dbs.elki.data.Clustering;
import de.lmu.ifi.dbs.elki.data.model.Model;
import de.lmu.ifi.dbs.elki.database.Database;

public interface ElkiProcessor<M extends Model> {

	public Clustering<M> doProcess(Database database) throws Exception;

	
}
