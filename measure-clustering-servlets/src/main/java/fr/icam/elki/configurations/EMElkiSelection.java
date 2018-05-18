package fr.icam.elki.configurations;

import fr.icam.elki.configurations.ElkiAlgorithm;

public class EMElkiSelection extends ElkiSelection {

	private static final long serialVersionUID = 201804231420004L;

	@Override
	protected ElkiAlgorithm getAlgorithm() {
		return ElkiAlgorithm.EM;
	}

}
