package fr.icam.elki.measures;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import eu.measure.platform.api.MeasureInstance;
import eu.measure.platform.api.MeasurePlatformClient;
import fr.icam.elki.configurations.ElkiConfiguration;

public class Lister extends HttpServlet {

		private static final long serialVersionUID = 20180514100000L;
			
		private Map<Long, ElkiConfiguration> configurations;
		
		private ElkiConfiguration getConfiguration(Long id) {
			return configurations.get(id);
		}
		
		private MeasurePlatformClient client;
		
		private Gson mapper;
		
		protected Gson getMapper() {
			return mapper;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public void init() throws ServletException {
			super.init();
			mapper = new Gson();
			configurations = (Map<Long, ElkiConfiguration>) this.getServletContext().getAttribute("configurations");
			client = (MeasurePlatformClient) this.getServletContext().getAttribute("measure-platform-client");
		}

		private Long getResource(HttpServletRequest request) throws ServletException {
			return (Long) request.getAttribute("id");
		}
	
		@Override
		public final void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
			Long id = this.getResource(request);
			ElkiConfiguration configuration = this.getConfiguration(id);
			Long projectId = configuration.getProject();
			try {
				List<MeasureInstance> measures = client.getProjectMeasureInstances(projectId);
				configuration.setMeasures(measures);
				mapper.toJson(measures, response.getWriter());
			} catch (Exception e) {
				// throw new ServletException(e);
				mapper.toJson(new MeasureInstance[] {}, response.getWriter());
			}
		}
				
}