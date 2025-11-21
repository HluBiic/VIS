package app.webapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Match;
import model.Tournament;
import services.MatchService;
import services.TournamentService;

/**
 * Servlet responsible for exporting match data for a specific tournament.
 * Currently supports CSV format export, downloading the tournament details and its matches.
 * Requires "tourId" and "format" (e.g., "CSV") as URL parameters.
 */
@WebServlet("/exportMatches")
public class ExportMatchesServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    private final MatchService matchService = new MatchService();
    private final TournamentService tourService = new TournamentService(); 

	/**
	 * Handles HTTP GET requests to generate and download match data exports.
	 * It validates the tournament ID, fetches the data, and outputs a CSV file
	 * containing tournament details and a list of all its matches.
	 *
	 * @param req The HttpServletRequest, expected to contain "tourId" and "format" parameters.
	 */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String tourIdParam = req.getParameter("tourId");
        String format = req.getParameter("format"); 
        
        if (tourIdParam == null || tourIdParam.trim().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tournament ID is missing");
            return;
        }

        int tourId;
        try {
            tourId = Integer.parseInt(tourIdParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Tournament ID format");
            return;
        }

        List<Match> matches = matchService.getAllTourMatches(tourId);
        Tournament tournament = tourService.getTourById(tourId); 

        if (tournament == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Tournament not found in database");
            return;
        }

        resp.setCharacterEncoding("UTF-8");

        if ("CSV".equalsIgnoreCase(format)) {
            resp.setContentType("text/csv");
            String filename = tournament.getName().replaceAll("[^a-zA-Z0-9.-]", "_") + "_matches.csv";
            resp.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

            try (PrintWriter writer = resp.getWriter()) {
                writer.println("Tournament Details");
                writer.printf("Name,%s%n", escapeCsv(tournament.getName()));
                writer.printf("Date,%s%n", tournament.getDate());
                writer.printf("Location,%s%n", escapeCsv(tournament.getLocation()));
                writer.println();
                
                writer.println("Team A,Team B,Score,Map");
                
                if (matches != null && !matches.isEmpty()) {
                    for (Match m : matches) {
                        writer.printf("%s,%s,%s,%s%n",
                                escapeCsv(m.getTeamA().getName()),
                                escapeCsv(m.getTeamB().getName()),
                                escapeCsv(m.getScore()),
                                escapeCsv(m.getMap().getName()));
                    }
                } else {
                    writer.println("No matches played yet,,,");
                }
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid format requested");
        }
    }
    
	/**
	 * Escapes a string for use in a CSV file, handling commas, quotes, and newlines
	 * by enclosing the data in double quotes and escaping existing double quotes.
	 *
	 * @param data The string to escape.
	 * @return The CSV-safe escaped string.
	 */
    private String escapeCsv(String data) {
        if (data == null) return "";
        if (data.contains(",") || data.contains("\"") || data.contains("\n")) {
            return "\"" + data.replace("\"", "\"\"") + "\"";
        }
        return data;
    }
}