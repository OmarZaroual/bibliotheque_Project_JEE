package com.bibliotheque.controllers;

import com.bibliotheque.models.Livre;
import com.bibliotheque.utils.LivreService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/RechercherLivresServlet")
public class RechercherLivresServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer les paramètres de recherche
        String titre = request.getParameter("titre");
        String auteur = request.getParameter("auteur");
        String categorie = request.getParameter("categorie");

        // Utiliser LivreService pour rechercher les livres en fonction des critères
        LivreService livreService = new LivreService();
        List<Livre> livres = livreService.rechercherLivres(titre, auteur, categorie);

        // Ajouter les livres trouvés à l'attribut de la requête
        request.setAttribute("livres", livres);

        // Rediriger vers la page JSP pour afficher les livres filtrés
        request.getRequestDispatcher("/livres.jsp").forward(request, response);
    }
}
