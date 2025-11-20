package app.webapp;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.TournamentService;

@WebServlet("/")
public class IndexServlet extends HttpServlet{

	private static final long serialVersionUID = 6975628760728106752L;
	
	private final TournamentService tourService = new TournamentService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
		throws ServletException, IOException {
		
		req.setAttribute("tournaments", tourService.getAllTournaments());
		
		req.getRequestDispatcher("/index.jsp").forward(req, resp);
	}
	
}
