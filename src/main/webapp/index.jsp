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
      <!-- R6 Icon on the left -->
      <img
        class="icon"
        src="https://images.seeklogo.com/logo-png/32/2/rainbow-six-siege-logo-png_seeklogo-325646.png"
        alt="R6 Icon"
      />
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
      <button
        class="btn btn-success"
        data-bs-toggle="modal"
        data-bs-target="#exportModal"
      >
        EXPORT
      </button>
    </div>

    <!-- Matches Table -->
    <div class="table-responsive" style="max-height: 300px; overflow-y: auto;">
      <table class="table table-bordered table-striped" id="matchesTable">
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Year</th>
            <th>Map</th>
            <th>League</th>
            <th>Winner</th>
          </tr>
        </thead>
        <tbody>
          <tr onclick="selectRow(this)">
            <td>1</td>
            <td><a href="maps" class="text-decoration-none text-primary">
		        Six Invitational
		     </a></td>
            <td>2023</td>
            <td>Bank</td>
            <td>EUL</td>
            <td>G2</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>

  <!-- Export Modal -->
  <div
    class="modal fade"
    id="exportModal"
    tabindex="-1"
    aria-labelledby="exportModalLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exportModalLabel">
            Download Matches
          </h5>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body">
          <p>Please select a format</p>
          <div class="d-flex gap-3">
            <div class="form-check">
              <input
                class="form-check-input"
                type="radio"
                name="exportOption"
                id="exportCSV"
                value="exportCSV"
              />
              <label class="form-check-label" for="exportCSV">CSV</label>
            </div>
            <div class="form-check">
              <input
                class="form-check-input"
                type="radio"
                name="exportOption"
                id="exportJSON"
                value="exportJSON"
              />
              <label class="form-check-label" for="exportJSON">JSON</label>
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button
            type="button"
            class="btn btn-secondary"
            data-bs-dismiss="modal"
          >
            CANCEL
          </button>
          <button type="button" class="btn btn-success">DOWNLOAD</button>
        </div>
      </div>
    </div>
  </div>

  <script>
  document.addEventListener("DOMContentLoaded", () => {
	  const tableRows = document.querySelectorAll("#matchesTable tbody tr");
	  tableRows.forEach(row => {
	    row.addEventListener("click", () => {
	      // Redirect to MapServlet
	      window.location.href = "maps";
	    });
	  });
	});
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
