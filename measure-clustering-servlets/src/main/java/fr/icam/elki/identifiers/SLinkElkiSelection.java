package fr.icam.elki.identifiers;

import fr.icam.elki.identifiers.ElkiAlgorithm;

public class SLinkElkiSelection extends ElkiSelection {

	private static final long serialVersionUID = 201804231420005L;

	@Override
	protected ElkiAlgorithm getAlgorithm() {
		return ElkiAlgorithm.SLINK;
	}

}
