package app.webapp;

import java.io.IOException;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import model.Tournament;
import services.MatchService;
import services.TournamentService;
import jakarta.servlet.annotation.*;

/**
 * Servlet responsible for handling requests to display the detailed information
 * of a single tournament, including its matches, most played map, and best team.
 * The tournament ID is expected as a URL parameter "id".
 */
@WebServlet("/tourDetail")
public class TourDetailServlet extends HttpServlet {

	private static final long serialVersionUID = -6872994481545827681L;
	private final MatchService matchService = new MatchService();
	private final TournamentService tourService = new TournamentService();

	/**
	 * Handles HTTP GET requests to retrieve and display tournament details. It 
	 * parses the tournament ID, fetches the corresponding Tournament, its matches,
	 * and brief statistics, then forwards the data to the tourDetail.jsp view.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException {

		String idUrlParam = req.getParameter("id");
		int id = Integer.parseInt(idUrlParam);
		
		Tournament t = tourService.getTourById(id);
		
	    req.setAttribute("tournament", t);
	    req.setAttribute("tourMatches", matchService.getAllTourMatches(id));
	    req.setAttribute("mostPlayedMap", matchService.getMostPlayedMap(t.getId()));
	    req.setAttribute("bestTeam", matchService.getBestTeam(t.getId()));
	    
	    req.getRequestDispatcher("/tourDetail.jsp").forward(req, resp);
	}
}