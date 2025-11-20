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

@WebServlet("/help")
public class HelpServlet extends HttpServlet {

	private static final long serialVersionUID = -654736769620570964L;
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
			
		req.getRequestDispatcher("/help.jsp").forward(req, resp);
	}
}
