package fr.icam.elki.configurations;

import fr.icam.elki.configurations.ElkiAlgorithm;

public class SLinkElkiSelection extends ElkiSelection {

	private static final long serialVersionUID = 201804231420005L;

	@Override
	protected ElkiAlgorithm getAlgorithm() {
		return ElkiAlgorithm.SLINK;
	}

}
