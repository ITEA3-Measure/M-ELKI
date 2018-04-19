package fr.icam.elki.identifiers;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ElkiIdentifierManager implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		List<Long> identifiers = new LinkedList<Long>();
		identifiers.add(0L);
		sce.getServletContext().setAttribute("identifiers", identifiers);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("identifiers", null);
	}

}
