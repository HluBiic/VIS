<%@page import="model.Match"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Map" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>HLU0035 - VIS project</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
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

    .section-title {
      font-weight: 600;
      margin-top: 1rem;
    }

    .info-label {
      font-weight: bold;
    }

    table th, table td {
      vertical-align: middle;
    }

    #matchesTable a:hover {
      text-decoration: underline;
    }

    /* Make "cards" invisible but keep padding */
    .invisible-card {
      background-color: transparent !important;
      border: none !important;
      padding: 0 !important;
    }

    /* Optional: add spacing inside invisible card */
    .invisible-card .content-padding {
      padding: 1rem;
    }
  </style>
</head>
<body class="p-4">
<div class="container my-4">
  <!-- Top header -->
  <div class="header-top">
	<a href="index">
		<img
			class="icon"
		    src="https://images.seeklogo.com/logo-png/32/2/rainbow-six-siege-logo-png_seeklogo-325646.png"
		    alt="R6 Icon"
		 />
	</a>
    <h2>R6 Tournaments Hub</h2>
    <div class="form-check form-switch mb-0">
      <input class="form-check-input" type="checkbox" id="darkModeSwitch" />
      <label class="form-check-label" for="darkModeSwitch">Dark Mode</label>
    </div>
  </div>

  <hr class="custom-line" />

  <!-- Bottom header -->
  <div class="header-bottom mb-3">
    <div><h4>Tournament Details</h4></div>
  </div>

  <!-- Two-column layout -->
  <div class="row">
    <!-- Left Column: Tournament Info -->
    <div class="col-md-6 mb-4">
      <div class="invisible-card">
        <div class="content-padding">
          <h2>TurnajA</h2>
          <div class="my-2"></div>

          <h5 class="section-title">Basic info</h5>
          <ul>
            <li><strong>Date: </strong>26.10.2025</li>
            <li><strong>Location: </strong>Italy</li>
          </ul>
          <div class="my-3"></div>

          <h5 class="section-title">Stats</h5>
          <ul>
            <li>Most Played Map: Bank</li>
            <li>Top Team: G2</li>
          </ul>
        </div>
      </div>
    </div>

    <!-- Right Column: Matches Table -->
    <div class="col-md-6 mb-4">
      <div class="invisible-card">
        <div class="content-padding">
          <h2>Matches (template)</h2>
          <div class="table-responsive mt-3">
            <table class="table table-bordered table-striped text-white" id="matchesTable">
              <thead>
              <tr>
                <th>Teams</th>
                <th>Score</th>
                <th>Map</th>
              </tr>
              </thead>
              <tbody>
              <tr>
                <td>G2 vs Faze</td>
                <td>2-1</td>
                <td>Bank</td>
              </tr>
              <tr>
                <td>Team Liquid vs Natus Vincere</td>
                <td>0-2</td>
                <td>Oregon</td>
              </tr>
              <tr>
                <td>Fnatic vs TSM</td>
                <td>2-0</td>
                <td>Kafe</td>
              </tr>
              </tbody>
            </table>
            <h2>MATCHES FROM DB</h2>
			<table class="table table-bordered table-striped text-white" id="mapsTable">
			    <thead>
			        <tr>
			            <th>Match ID</th>
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
				        <td> TODO </td>
				        <td><%= m.getScore() %></td>
				        <td><%= map != null ? map.getName() : "Unknown Map" %></td>
				    </tr>
				    <%
				        }
				    %>
				</tbody>
			</table>  
            
			<h2>MAPS FROM DB</h2>
			<table class="table table-bordered table-striped text-white" id="mapsTable">
			    <thead>
			        <tr>
			            <th>Map Name</th>
			        </tr>
			    </thead>
			    <tbody>
			        <%
			            List<Map> maps2 = (List<Map>) request.getAttribute("maps");
			            for (Map map : maps2) {
			        %>
			        <tr>
			            <td><%= map.getName() %></td>

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
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<script>
  // Dark mode toggle
  document.addEventListener("DOMContentLoaded", () => {
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
