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


//former maps.jsp
//
//<%@ page import="java.util.List" %>
//<%@ page import="dto.MapDTO" %>
//
//<html>
//<head>
//    <link rel="stylesheet" href="https://unpkg.com/primeflex/primeflex.css" />
//    <link rel="stylesheet" href="https://unpkg.com/primeicons/primeicons.css" />
//
//    <style>
//        body {
//            background: #f5f7f8; /* Light gray PrimeFaces-like background */
//            font-family: "Segoe UI", Roboto, sans-serif;
//        }
//    </style>
//</head>
//
//<body class="p-4">
//
//    <div class="surface-card p-5 border-round shadow-3 mx-auto" style="max-width: 600px;">
//
//        <h2 class="text-2xl font-bold mb-3 flex align-items-center gap-2">
//            <i class="pi pi-map text-2xl text-primary"></i>
//            Maps
//        </h2>
//
//        <ul class="list-none p-0 m-0">
//            <%
//                List<MapDTO> maps = (List<MapDTO>) request.getAttribute("maps");
//                for (MapDTO m : maps) {
//            %>
//            <li class="flex align-items-center p-3 mb-2 border-round surface-100 shadow-1">
//                <i class="pi pi-compass mr-3 text-blue-600 text-xl"></i>
//
//                <div class="flex flex-column">
//                    <span class="font-semibold text-lg"><%= m.getName() %></span>
//                    <span class="text-sm text-500">ID: <%= m.getId() %></span>
//                </div>
//            </li>
//            <% } %>
//        </ul>
//
//        <p class="mt-4 text-sm text-500">
//            Raw list: <%= maps %>
//        </p>
//
//    </div>
//
//</body>
//</html>
