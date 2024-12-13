<%@page import="com.bibliotheque.utils.LivreService"%>
<%@ page import="java.util.List" %>
<%@ page import="com.bibliotheque.models.Livre" %>

<%
    LivreService livreService = new LivreService();
    List<Livre> livres = livreService.getLivresDisponibles();
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Livres</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">Bibliothèque</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link active" href="livres.jsp">Livres</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="emprunts.jsp">Emprunts</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Liste des livres -->
    <div class="container mt-5">
    <h2>Liste des livres disponibles</h2>
    <div class="row">
        <% for (Livre livre : livres) { %>
            <div class="col-md-4">
                <div class="card mb-4">
                    <img src="<%= livre.getImageUrl() %>" class="card-img-top" alt="Image du livre">
                    <div class="card-body">
                        <h5 class="card-title"><%= livre.getTitre() %></h5>
                        <p class="card-text">Auteur : <%= livre.getAuteur() %></p>
                        <p>Catégorie : <%= livre.getCategorie() %></p>
                        <% if (livre.isDisponibilite()) { %>
                            <a href="EmprunterServlet?id=<%= livre.getId() %>" class="btn btn-primary">Emprunter</a>
                        <% } else { %>
                            <button class="btn btn-secondary" disabled>Non disponible</button>
                        <% } %>
                    </div>
                </div>
            </div>
        <% } %>
    </div>
</div>


    <!-- Bootstrap Bundle avec Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
