<%@ page import="model.Tournament" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>HLU0035 - VIS project</title>
  <link
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
    rel="stylesheet"
  />
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
  <style>
    .header-top {
      display: flex;
      align-items: center;
      justify-content: space-between;
      margin-bottom: 0.5rem;
    }

    .header-top .icon {
      width: 32px;
      height: 32px;
    }

    .header-top h2 {
      margin: 0;
      flex-grow: 1;
      text-align: center;
    }

    hr.custom-line {
      border: 0;
      height: 2px;
      background-color: #333;
      margin: 0.5rem 0;
    }

    .header-bottom {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-top: 0.5rem;
    }
    
	#matchesTable a:hover {
	    text-decoration: underline;
	}
  </style>
</head>
<body class="p-4">
  <div class="container my-4">
    <!-- Top header -->
    <div class="header-top">
      <!-- R6 Icon on the left + redirection to index -->
		<a href="index">
		  <img
		    class="icon"
		    src="https://images.seeklogo.com/logo-png/32/2/rainbow-six-siege-logo-png_seeklogo-325646.png"
		    alt="R6 Icon"
		  />
		</a>
      <!-- Title -->
      <h2>R6 Tournaments Hub</h2>
      <!-- Dark mode switch on the right -->
      <div class="form-check form-switch mb-0">
        <input
          class="form-check-input"
          type="checkbox"
          id="darkModeSwitch"
        />
        <label class="form-check-label" for="darkModeSwitch">Dark Mode</label>
      </div>
    </div>

    <hr class="custom-line" />

    <!-- Bottom header -->
    <div class="header-bottom mb-3">
      <div><h4>Tournaments</h4></div>
    </div>

    <!-- Matches Table -->
    <div class="table-responsive" style="max-height: 300px; overflow-y: auto;">
      <table class="table table-bordered table-striped" id="matchesTable">
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Location</th>
            <th>Date</th>
          </tr>
        </thead>
		<tbody>
		<%
		    List<Tournament> tours = (List<Tournament>) request.getAttribute("tournaments");
		    if (tours == null || tours.isEmpty()) {
		%>
		        <tr>
		            <td colspan="4" class="text-center text-muted">No tournaments found</td>
		        </tr>
		<%
		    } else {
		        for (Tournament t : tours) {
		%>
		        <tr>
		            <td><%= t.getId() %></td>
		            <td>
		                <a href="tourDetail?id=<%= t.getId() %>" class="text-decoration-none text-primary">
		                    <%= t.getName() %>
		                </a>
		            </td>
		            <td><%= t.getLocation() %></td>
		            <td><%= t.getDate() %></td>
		        </tr>
		<%
		        }
		    }
		%>
		</tbody>
      </table>
    </div>
	<hr class="custom-line" />
	
	<!-- Help link text -->
	<div class="mb-4">
	  <a href="help" class="text-decoration-none text-primary">Help</a>
	</div>
  </div>

  <script>
    // Theme switching
    document.addEventListener("DOMContentLoaded", (event) => {
      const htmlElement = document.documentElement;
      const switchElement = document.getElementById("darkModeSwitch");
      const currentTheme = localStorage.getItem("bsTheme") || "dark";
      htmlElement.setAttribute("data-bs-theme", currentTheme);
      switchElement.checked = currentTheme === "dark";

      switchElement.addEventListener("change", function () {
        if (this.checked) {
          htmlElement.setAttribute("data-bs-theme", "dark");
          localStorage.setItem("bsTheme", "dark");
        } else {
          htmlElement.setAttribute("data-bs-theme", "light");
          localStorage.setItem("bsTheme", "light");
        }
      });
    });
  </script>
</body>
</html>
