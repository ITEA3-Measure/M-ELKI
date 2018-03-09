package fr.icam.elki.distances;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class ElkiDistanceProvider extends HttpServlet {

	private static final long serialVersionUID = 20180305180000L;
	
	private List<Distance> distances;

	private Gson mapper;
	
	@SuppressWarnings("unchecked")
	@Override
	public void init() throws ServletException {
		super.init();
		mapper = new Gson();
		Map<String, Object> distances = (Map<String, Object>) this.getServletContext().getAttribute("distances");
		this.distances = new ArrayList<Distance>(distances.size());
		for (String name : distances.keySet()) {
			Distance distance = new Distance(name, this.getName(name));
			this.distances.add(distance);
		}
	}

	@Override
	public final void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		mapper.toJson(distances, response.getWriter());
	}

	private String getName(String name) {
		return name.replaceAll("-", " ");
	}
	
}
