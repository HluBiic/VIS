package app.webapp;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.MapService;
import services.MatchService;
import services.TeamService;
import services.TournamentService;

/**
 * Servlet handling the "/help" path, primarily used to fetch lists of all major
 * application entities (Maps, Matches, Tournaments, Teams) and forward them
 * to the help.jsp view for display.
 */
@WebServlet("/help")
public class HelpServlet extends HttpServlet {

	private static final long serialVersionUID = -654736769620570964L;
	private final MapService mapService = new MapService();
	private final MatchService matchService = new MatchService(); //presunut so samostatneho servletu asi
	private final TournamentService tourService = new TournamentService();
	private final TeamService teamService = new TeamService(); 

	/**
	 * Handles HTTP GET requests for the help page. Fetches all entities from all 
	 * service layers and attaches them as request attributes, then forwards the 
	 * request to the /help.jsp page.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
	    req.setAttribute("maps", mapService.getAllMaps());
	    req.setAttribute("matches", matchService.getAllMatches());
	    req.setAttribute("tournaments", tourService.getAllTournaments());
	    req.setAttribute("teams", teamService.getAllTeams());
			
		req.getRequestDispatcher("/help.jsp").forward(req, resp);
	}
}