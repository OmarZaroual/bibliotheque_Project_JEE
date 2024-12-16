<%@ page import="com.bibliotheque.utils.LivreService" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bibliotheque.models.Livre" %>
<%@ page import="java.util.Set" %>

<%
    // Retrieve search parameters from the request
    String searchTitle = request.getParameter("searchTitle");
    String searchAuthor = request.getParameter("searchAuthor");
    String searchCategory = request.getParameter("searchCategory");

    // Initialize LivreService to interact with the database
    LivreService livreService = new LivreService();
    
    // Get filtered list of books based on search parameters
    List<Livre> livres = livreService.rechercherLivres(searchTitle, searchAuthor, searchCategory);

    // Get the list of unique categories for the select dropdown
    Set<String> categories = livreService.getCategories();
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Livres</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS for Card Styles -->
    <style>
        .card {
            height: 100%;
            margin-bottom: 20px;
        }

        .card-img-top {
            width: 100%;
            height: 200px;
            object-fit: contain;
            object-position: center center;
            border-top-left-radius: 5px;
            border-top-right-radius: 5px;
        }

        .card-body {
            text-align: center;
            padding: 10px;
        }

        .row {
            display: flex;
            flex-wrap: wrap;
            justify-content: space-between;
        }

        .col-md-4 {
            flex: 0 0 32%;
            max-width: 32%;
            padding: 10px;
        }

        @media (max-width: 768px) {
            .col-md-4 {
                flex: 0 0 48%;
                max-width: 48%;
            }
        }

        @media (max-width: 576px) {
            .col-md-4 {
                flex: 0 0 100%;
                max-width: 100%;
            }
        }
    </style>
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

    <!-- Search Form -->
    <div class="container mt-3">
        <h2>Rechercher des livres</h2>
        <form method="get" action="livres.jsp">
            <div class="row">
                <div class="col-md-4">
                    <input type="text" name="searchTitle" class="form-control" placeholder="Titre" value="<%= searchTitle != null ? searchTitle : "" %>">
                </div>
                <div class="col-md-4">
                    <input type="text" name="searchAuthor" class="form-control" placeholder="Auteur" value="<%= searchAuthor != null ? searchAuthor : "" %>">
                </div>
                <div class="col-md-4">
                    <select name="searchCategory" class="form-control">
                        <option value="">Sélectionner une catégorie</option>
                        <% for (String category : categories) { %>
                            <option value="<%= category %>" <%= category.equals(searchCategory) ? "selected" : "" %>><%= category %></option>
                        <% } %>
                    </select>
                </div>
            </div>
            <button type="submit" class="btn btn-primary mt-3">Rechercher</button>
        </form>
    </div>

    <!-- Liste des livres -->
    <div class="container mt-5">
        <h2>Liste des livres disponibles</h2>
        <div class="row">
            <% for (Livre livre : livres) { %>
                <div class="col-md-4"> <!-- 3 cards per row -->
                    <div class="card">
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
