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
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
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
    .section-title {
      font-weight: 600;
      margin-top: 1rem;
    }
    .invisible-card {
      background-color: transparent !important;
      border: none !important;
      padding: 0 !important;
    }
    .invisible-card .content-padding {
      padding: 1rem;
    }
  </style>
</head>
<body class="p-4">
<%
    Tournament t = (Tournament) request.getAttribute("tournament");
    List<Match> matches = (List<Match>) request.getAttribute("tourMatches");
%>
<div class="container my-4">

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
  <div class="header-bottom mb-3">
    <div><h4>Tournament Details</h4></div>
  </div>
  <div class="row">
    <div class="col-md-6 mb-4">
      <div class="invisible-card">
        <div class="content-padding">
          <h2><%= t.getName() %></h2>
          <h5 class="section-title">Basic info</h5>
          <ul>
            <li><strong>Date:</strong> <%= t.getDate() %></li>
            <li><strong>Location:</strong> <%= t.getLocation() %></li>
          </ul>
          <%
              if (matches != null && !matches.isEmpty()) {
          %>
              <h5 class="section-title">Stats</h5>
              <ul>
                <li>Most Played Map: Bank (TODO)</li>
                <li>Top Team: G2 (TODO)</li>
              </ul>
          <%
              }
          %>
        </div>
      </div>
    </div>
    <div class="col-md-6 mb-4">
      <div class="invisible-card">
        <div class="content-padding">
          <h2>Matches</h2>
          <div class="table-responsive mt-3">
            <%
                if (matches == null || matches.isEmpty()) {
            %>
                <p class="text-center mt-3">No matches played on this tournament yet.</p>
            <%
                } else {
            %>
                <table class="table table-bordered table-striped text-white" id="matchesTable">
                  <thead>
                    <tr>
                      <th>Teams</th>
                      <th>Score</th>
                      <th>Map</th>
                    </tr>
                  </thead>
                  <tbody>
                  <%
                      for (Match m : matches) {
                  %>
                      <tr>
                        <td><%= m.getTeamA().getName() %> vs <%= m.getTeamB().getName() %></td>
                        <td><%= m.getScore() %></td>
                        <td><%= m.getMap().getName() %></td>
                      </tr>
                  <%
                      }
                  %>
                  </tbody>
                </table>
            <%
                }
            %>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<script>
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
