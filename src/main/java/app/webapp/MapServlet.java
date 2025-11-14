package app.webapp;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import services.MapService;
import jakarta.servlet.annotation.*;

@WebServlet("/maps")
public class MapServlet extends HttpServlet {

	private static final long serialVersionUID = -6872994481545827681L;
	private final MapService service = new MapService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException {

	    req.setAttribute("maps", service.getAllMaps());
	    req.getRequestDispatcher("/maps.jsp").forward(req, resp);
	}
	
}
