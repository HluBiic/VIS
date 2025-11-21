package app.webapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Tournament;
import services.TournamentService;

/**
 * The main servlet handling the root path ("/") of the application.
 * It fetches the list of all tournaments and forwards them to the index.jsp page.
 */
@WebServlet("/")
public class IndexServlet extends HttpServlet{

	private static final long serialVersionUID = 6975628760728106752L;
	
	private final TournamentService tourService = new TournamentService();
	
	/**
	 * Handles HTTP GET requests to the index page. Retrieves all tournaments, 
	 * sets them as a request attribute, and forwards to index.jsp.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
		throws ServletException, IOException {
		
		List<Tournament> tours = tourService.getAllTournaments();
		if (tours == null) tours = new ArrayList<>();

		req.setAttribute("tournaments", tours);
		req.getRequestDispatcher("/index.jsp").forward(req, resp);
	}	
}