<%@page import="com.bibliotheque.utils.EmpruntService"%>
<%@page import="com.bibliotheque.models.Emprunt"%>
<%@page import="com.bibliotheque.models.Livre"%>
<%@page import="java.util.List"%>

<%
    // Récupérer l'utilisateur courant (par exemple, à partir de la session)
    int idUtilisateur = (int) session.getAttribute("userId");

    // Utiliser EmpruntService pour obtenir les emprunts de cet utilisateur
    EmpruntService empruntService = new EmpruntService();
    List<Emprunt> emprunts = empruntService.getEmpruntsParUtilisateur(idUtilisateur);
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Emprunts</title>
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
                        <a class="nav-link" href="livres.jsp">Livres</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" href="emprunts.jsp">Emprunts</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Liste des emprunts -->
    <div class="container mt-5">
    <h2>Historique des emprunts</h2>
    <table class="table">
        <thead>
            <tr>
                <th>Titre</th>
                <th>Auteur</th>
                <th>Date d'emprunt</th>
                <th>Date de retour</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <% for (Emprunt emprunt : emprunts) { %>
                <tr>
                    <td><%= emprunt.getLivre().getTitre() %></td>
                    <td><%= emprunt.getLivre().getAuteur() %></td>
                    <td><%= emprunt.getDateEmprunt() %></td>
                    <td><%= emprunt.getDateRetour() == null ? "Non retourné" : emprunt.getDateRetour() %></td>
                    <td>
                        <% if (emprunt.getDateRetour() == null) { %>
                            <a href="RetourServlet?id=<%= emprunt.getId() %>" class="btn btn-primary">Retourner</a>
                        <% } else { %>
                            <button class="btn btn-secondary" disabled>Déjà retourné</button>
                        <% } %>
                    </td>
                </tr>
            <% } %>
        </tbody>
    </table>
</div>


    <!-- Bootstrap Bundle avec Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
