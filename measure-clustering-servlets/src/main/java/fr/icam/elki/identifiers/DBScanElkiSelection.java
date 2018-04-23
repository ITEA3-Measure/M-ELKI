package fr.icam.elki.identifiers;

import fr.icam.elki.identifiers.ElkiAlgorithm;

public class DBScanElkiSelection extends ElkiSelection {

	private static final long serialVersionUID = 201804231420002L;

	@Override
	protected ElkiAlgorithm getAlgorithm() {
		return ElkiAlgorithm.DBSCAN;
	}

}
