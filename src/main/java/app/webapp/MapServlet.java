package app.webapp;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import services.MapService;
import services.MatchService;
import services.TeamService;
import services.TournamentService;
import jakarta.servlet.annotation.*;

@WebServlet("/maps")
public class MapServlet extends HttpServlet {

	private static final long serialVersionUID = -6872994481545827681L;
	private final MapService mapService = new MapService();
	private final MatchService matchService = new MatchService(); //presunut so samostatneho servletu asi
	private final TournamentService tourService = new TournamentService();
	private final TeamService teamService = new TeamService(); 

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException {

	    req.setAttribute("maps", mapService.getAllMaps());
	    req.setAttribute("matches", matchService.getAllMatches());
	    req.setAttribute("tournaments", tourService.getAllTournaments());
	    req.setAttribute("teams", teamService.getAllTeams());
	    
	    req.getRequestDispatcher("/maps.jsp").forward(req, resp);
	}
	
}