<%@ page import="java.util.List" %>
<%@ page import="dto.MapDTO" %>

<html><body>
<h2>Maps</h2>
<ul>
<%
    List<MapDTO> maps = (List<MapDTO>) request.getAttribute("maps");
    for (MapDTO m : maps) {
%>
    <li><%= m.getId() %> - <%= m.getName() %></li>
<% } %>
</ul>
<p>Maps: <%= maps %></p>
</body></html>
