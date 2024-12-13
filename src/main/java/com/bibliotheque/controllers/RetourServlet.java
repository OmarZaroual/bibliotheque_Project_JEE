/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
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

@WebServlet("/RetourServlet")
public class RetourServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idEmprunt = Integer.parseInt(request.getParameter("id"));

        try (Connection connection = DatabaseConnection.getConnection()) {
            // Mettre à jour la date de retour de l'emprunt
            String updateEmpruntQuery = "UPDATE Emprunt SET date_retour = ? WHERE id_emprunt = ?";
            PreparedStatement empruntStatement = connection.prepareStatement(updateEmpruntQuery);
            empruntStatement.setString(1, java.time.LocalDate.now().toString());
            empruntStatement.setInt(2, idEmprunt);
            empruntStatement.executeUpdate();

            // Récupérer l'identifiant du livre associé
            String selectLivreQuery = "SELECT id_livre FROM Emprunt WHERE id_emprunt = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectLivreQuery);
            selectStatement.setInt(1, idEmprunt);
            int idLivre = -1;
            var resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                idLivre = resultSet.getInt("id_livre");
            }

            // Mettre à jour la disponibilité du livre
            if (idLivre != -1) {
                String updateLivreQuery = "UPDATE Livre SET disponibilite = TRUE WHERE id_livre = ?";
                PreparedStatement livreStatement = connection.prepareStatement(updateLivreQuery);
                livreStatement.setInt(1, idLivre);
                livreStatement.executeUpdate();
            }

            // Redirection vers la page des emprunts après le retour
            response.sendRedirect("emprunts.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Erreur lors du retour du livre.");
        }
    }
}

