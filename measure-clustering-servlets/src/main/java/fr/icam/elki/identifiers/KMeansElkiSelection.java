package fr.icam.elki.identifiers;

import fr.icam.elki.identifiers.ElkiAlgorithm;

public class KMeansElkiSelection extends ElkiSelection {

	private static final long serialVersionUID = 201804231420003L;

	@Override
	protected ElkiAlgorithm getAlgorithm() {
		return ElkiAlgorithm.KMEANS;
	}

}
