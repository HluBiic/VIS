<%@page import="model.Match"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Map" %>
<%@ page import="model.Tournament" %>
<%@ page import="model.Team" %>

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
    /* Header layout */
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

    /* Table layout */
    .table-container {
      margin-bottom: 2rem;
    }

    h4 {
      margin-top: 1rem;
      margin-bottom: 0.5rem;
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
    <!-- R6 Icon on the left -->
    <a href="index">
      <img
        class="icon"
        src="https://images.seeklogo.com/logo-png/32/2/rainbow-six-siege-logo-png_seeklogo-325646.png"
        alt="R6 Icon"
      />
    </a>
    <!-- Title -->
    <h2>R6 Tournaments Hub</h2>
    <!-- Dark mode switch -->
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

  <div class="header-bottom mb-3">
    <div><h4>Data from DB</h4></div>
  
  </div>
  <!-- Database Notebook Layout -->
  <div class="row">
    <!-- Left Column -->
    <div class="col-md-6">
      <div class="table-container">
        <h4>Maps</h4>
        <div class="table-responsive">
          <table class="table table-bordered table-striped" id="matchesTable">
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
              </tr>
            </thead>
            <tbody>
	            <%
		            List<Map> maps2 = (List<Map>) request.getAttribute("maps");
		            for (Map map : maps2) {
	    		%>
    			<tr>
    				<td><%= map.getId() %></td>
    				<td><%= map.getName() %></td>
    			</tr>
    			<%
		            }
    			%>
			</tbody>
          </table>
        </div>
      </div>

      <div class="table-container">
        <h4>Teams</h4>
        <div class="table-responsive">
          <table class="table table-bordered table-striped">
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Region</th>
              </tr>
            </thead>
			    <tbody>
			        <%
			            List<Team> teams = (List<Team>) request.getAttribute("teams");
			            for (Team team : teams) {
			        %>
			        <tr>
			        	<td><%= team.getId() %></td>
			            <td><%= team.getName() %></td>
						<td><%= team.getRegion() %></td>
			        </tr>
			        <%
			            }
			        %>
			    </tbody>
          </table>
        </div>
      </div>
    </div>

    <!-- Right Column -->
    <div class="col-md-6">
      <div class="table-container">
        <h4>Tournaments</h4>
        <div class="table-responsive">
          <table class="table table-bordered table-striped">
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
			            for (Tournament t : tours) {
			        %>
			        <tr>
			            <td><%= t.getId() %></td>
						<td><%= t.getName() %></td>
						<td><%= t.getLocation() %></td>
						<td><%= t.getDate() %></td>
			        </tr>
			        <%
			            }
			        %>
			    </tbody>
          </table>
        </div>
      </div>

      <div class="table-container">
        <h4>Matches</h4>
        <div class="table-responsive">
          <table class="table table-bordered table-striped">
            <thead>
              <tr>
			  	<th>ID</th>
			    <th>Tournament</th>
			    <th>Teams</th>
			    <th>Map</th>
			    <th>Score</th>
              </tr>
            </thead>
				<tbody>
				    <%
				        List<Match> matches = (List<Match>) request.getAttribute("matches");
				        List<Map> maps = (List<Map>) request.getAttribute("maps");
				        for (Match m : matches) {
				        	Map map = null;
				            for (Map mp : maps) {
				                if (mp.getId() == m.getMap().getId()) { // match map ID
				                    map = mp;
				                    break;
				                }
				            }
				    %>
				    <tr>
				        <td><%= m.getId() %></td>
				        <td><%= m.getTournament().getName() %></td>
				        <td><%= m.getTeamA().getName() %> vs <%= m.getTeamB().getName() %> </td>
				        <td><%= m.getScore() %></td>
				        <td><%= map != null ? map.getName() : "Unknown Map" %></td>
				    </tr>
				    <%
				        }
				    %>
				</tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</div>

<script>
  document.addEventListener("DOMContentLoaded", () => {
    // Theme switching
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
