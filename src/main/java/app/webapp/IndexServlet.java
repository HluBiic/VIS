package app.webapp;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/")
public class IndexServlet extends HttpServlet{

	private static final long serialVersionUID = 6975628760728106752L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
		throws ServletException, IOException {
		
		req.getRequestDispatcher("/index.jsp").forward(req, resp);
	}
	
}
