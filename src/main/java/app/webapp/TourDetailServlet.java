package app.webapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import model.AllModelFactory;
import model.Tournament;
import services.MapService;
import services.MatchService;
import services.TeamService;
import services.TournamentService;
import jakarta.servlet.annotation.*;

@WebServlet("/tourDetail")
public class TourDetailServlet extends HttpServlet {

	private static final long serialVersionUID = -6872994481545827681L;
	private final MatchService matchService = new MatchService();
	private final MapService mapService = new MapService();
	private final TournamentService tourService = new TournamentService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException {

		String idUrlParam = req.getParameter("id");
		int id = Integer.parseInt(idUrlParam);
		
		Tournament t = tourService.getTourById(id);
		
	    req.setAttribute("tournament", t);
	    req.setAttribute("tourMatches", matchService.getAllTourMatches(id));
	    
	    req.getRequestDispatcher("/tourDetail.jsp").forward(req, resp);
	}
	
}