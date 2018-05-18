package fr.icam.elki.measures;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import fr.icam.elki.configurations.ElkiConfiguration;

public class Select extends HttpServlet {

		private static final long serialVersionUID = 20180514100000L;
			
		private Map<Long, ElkiConfiguration> configurations;
		
		private ElkiConfiguration getConfiguration(Long id) {
			return configurations.get(id);
		}
				
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
		}

		private Long getResource(HttpServletRequest request) throws ServletException {
			return (Long) request.getAttribute("id");
		}
	
		@Override
		public final void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
			Long id = this.getResource(request);
			ElkiConfiguration configuration = this.getConfiguration(id);
			String value = request.getParameter("measure");
			Long measureId = Long.valueOf(value);
			Boolean selected = configuration.isSelected(measureId);
			response.getWriter().write(selected ? "true" : "false");
		}
		
		@Override
		public final void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
			Long id = this.getResource(request);
			ElkiConfiguration configuration = this.getConfiguration(id);
			Long measure = Long.valueOf(request.getParameter("measure"));
			Boolean select = Boolean.valueOf(request.getParameter("select"));
			configuration.doSelect(measure, select);
			response.getWriter().write("true");
		}
		
}