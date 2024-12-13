package com.bibliotheque.controllers;

import com.bibliotheque.utils.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/AjouterLivreServlet")
public class AjouterLivreServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer les données du formulaire
        String titre = request.getParameter("titre");
        String auteur = request.getParameter("auteur");
        String categorie = request.getParameter("categorie");
        String imageUrl = request.getParameter("image");
        boolean disponibilite = request.getParameter("disponibilite") != null;  // true si la case est cochée

        // Vérifier si la disponibilité est cochée
        if (!disponibilite) {
            // Si la disponibilité n'est pas cochée, renvoyer un message d'erreur sans ajouter à la base de données
            response.sendRedirect("livresBibliothecaire.jsp?success=false&errorMessage=Veuillez%20cocher%20la%20case%20de%20disponibilité");
            return;
        }

        // Afficher les valeurs dans la console pour vérifier que les données sont récupérées
        System.out.println("Titre: " + titre);
        System.out.println("Auteur: " + auteur);
        System.out.println("Catégorie: " + categorie);
        System.out.println("Image URL: " + imageUrl);
        System.out.println("Disponibilité: " + disponibilite);

        try {
            // Insertion dans la base de données
            try (Connection connection = DatabaseConnection.getConnection()) {
                String query = "INSERT INTO livre (titre, auteur, categorie, image_url, disponibilite) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);

                // Préparer les données pour l'insertion
                statement.setString(1, titre);
                statement.setString(2, auteur);
                statement.setString(3, categorie);
                statement.setString(4, imageUrl);  // URL de l'image
                statement.setBoolean(5, disponibilite);  // Valeur de disponibilité (true ou false)

                int rowsInserted = statement.executeUpdate();  // Exécution de la requête
                if (rowsInserted > 0) {
                    // Si l'insertion est réussie, rediriger vers la page avec un message de succès
                    response.sendRedirect("livresBibliothecaire.jsp?success=true");
                } else {
                    // Si l'insertion échoue
                    response.sendRedirect("livresBibliothecaire.jsp?success=false");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // En cas d'exception, rediriger avec un message d'erreur
            response.sendRedirect("livresBibliothecaire.jsp?success=false");
        }
    }
}
