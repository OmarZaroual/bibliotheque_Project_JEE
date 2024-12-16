package com.bibliotheque.controllers;

import com.bibliotheque.models.Livre;
import com.bibliotheque.utils.LivreService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ModifierLivreServlet")
public class ModifierLivreServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the book ID from the URL parameter
        int idLivre = Integer.parseInt(request.getParameter("id"));

        // Use LivreService to get the book details
        LivreService livreService = new LivreService();
        Livre livre = livreService.getLivreById(idLivre);

        if (livre != null) {
            // Set the book object in the request scope for the JSP page to access
            request.setAttribute("livre", livre);
            // Forward to the modal form in the JSP for editing the book
            request.getRequestDispatcher("/livresBibliothecaire.jsp").forward(request, response);
        } else {
            // If the book is not found, redirect to an error page or show a message
            response.sendRedirect("livresBibliothecaire.jsp?errorMessage=Livre%20non%20trouvé");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the form data from the request
        int idLivre = Integer.parseInt(request.getParameter("id_livre"));
        String titre = request.getParameter("titre");
        String auteur = request.getParameter("auteur");
        String categorie = request.getParameter("categorie");
        String imageUrl = request.getParameter("image_url");
        boolean disponibilite = request.getParameter("disponibilite") != null;

        // Create a Livre object with the updated details
        Livre livre = new Livre();
        livre.setId(idLivre);
        livre.setTitre(titre);
        livre.setAuteur(auteur);
        livre.setCategorie(categorie);
        livre.setImageUrl(imageUrl);
        livre.setDisponibilite(disponibilite);

        // Use LivreService to update the book details in the database
        LivreService livreService = new LivreService();
        boolean updateSuccess = livreService.updateLivre(livre);

        // Redirect with a success or failure message
        if (updateSuccess) {
            response.sendRedirect("livresBibliothecaire.jsp?success=true");
        } else {
            response.sendRedirect("livresBibliothecaire.jsp?success=false&errorMessage=Échec%20de%20la%20modification");
        }
    }
}
