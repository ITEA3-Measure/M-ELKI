package fr.icam.elki.identifiers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ElkiIdentifierManager implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Map<Long, ElkiConfiguration> identifiers = new HashMap<Long, ElkiConfiguration>(128);
		identifiers.put(0L, new ElkiConfiguration());
		sce.getServletContext().setAttribute("identifiers", identifiers);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("identifiers", null);
	}

}
