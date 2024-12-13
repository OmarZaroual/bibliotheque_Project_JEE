<%@ page import="java.util.List" %>
<%@ page import="com.bibliotheque.models.Livre" %>
<%@ page import="com.bibliotheque.utils.LivreService" %>

<%
    LivreService livreService = new LivreService();
    List<Livre> livres = livreService.getLivresDisponibles();
%>

<% String success = request.getParameter("success"); %>
<% String errorMessage = request.getParameter("errorMessage"); %>

<% if ("true".equals(success)) { %>
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        Livre ajouté avec succès !
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
<% } else if ("false".equals(success)) { %>
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
        Erreur lors de l'ajout du livre. Veuillez réessayer.
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
<% } %>

<% if (errorMessage != null) { %>
    <div class="alert alert-warning alert-dismissible fade show" role="alert">
        <%= errorMessage %>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
<% } %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestion des Livres</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h1 class="mb-4">Gestion des Livres</h1>
        <!-- Bouton pour ouvrir la fenêtre modale -->
        <button type="button" class="btn btn-success mb-3" data-bs-toggle="modal" data-bs-target="#addBookModal">
            Ajouter un Livre
        </button>

        <!-- Tableau des livres -->
        <table id="booksTable" class="table table-bordered">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Titre</th>
                    <th>Auteur</th>
                    <th>Catégorie</th>
                    <th>Image</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% for (Livre livre : livres) { %>
                    <tr>
                        <td><%= livre.getId() %></td>
                        <td><%= livre.getTitre() %></td>
                        <td><%= livre.getAuteur() %></td>
                        <td><%= livre.getCategorie() %></td>
                        <td>
                            <img src="<%= livre.getImageUrl() %>" alt="Image du livre" class="img-thumbnail" style="max-width: 100px;">
                        </td>
                        <td>
                            <a href="ModifierLivreServlet?id=<%= livre.getId() %>" class="btn btn-primary">Modifier</a>
                            <a href="SupprimerLivreServlet?id=<%= livre.getId() %>" class="btn btn-danger" onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce livre ?');">Supprimer</a>
                        </td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    </div>

    <!-- Modale pour ajouter un livre -->
    <div class="modal fade" id="addBookModal" tabindex="-1" aria-labelledby="addBookModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addBookModalLabel">Ajouter un Livre</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form action="AjouterLivreServlet" method="POST">
                    <div class="modal-body">
                        <div class="mb-3">
                            <label for="titre" class="form-label">Titre</label>
                            <input type="text" class="form-control" id="titre" name="titre" required>
                        </div>
                        <div class="mb-3">
                            <label for="auteur" class="form-label">Auteur</label>
                            <input type="text" class="form-control" id="auteur" name="auteur" required>
                        </div>
                        <div class="mb-3">
                            <label for="categorie" class="form-label">Catégorie</label>
                            <input type="text" class="form-control" id="categorie" name="categorie" required>
                        </div>
                        <div class="mb-3">
                            <label for="image" class="form-label">Image (URL)</label>
                            <input type="text" class="form-control" id="image" name="image" required>
                        </div>
                        <div class="mb-3">
                            <label for="disponibilite" class="form-label">Disponible</label>
                            <input type="checkbox" class="form-check-input" id="disponibilite" name="disponibilite">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button>
                        <button type="submit" class="btn btn-primary">Ajouter</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
