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

@WebServlet("/EmprunterServlet")
public class EmprunterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idLivre = Integer.parseInt(request.getParameter("id"));
        int idUtilisateur = (int) request.getSession().getAttribute("userId");
        String dateEmprunt = java.time.LocalDate.now().toString();

        try (Connection connection = DatabaseConnection.getConnection()) {
            // Ajouter un emprunt
            String queryEmprunt = "INSERT INTO Emprunt (id_utilisateur, id_livre, date_emprunt) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(queryEmprunt);
            statement.setInt(1, idUtilisateur);
            statement.setInt(2, idLivre);
            statement.setString(3, dateEmprunt);
            statement.executeUpdate();

            // Mettre à jour la disponibilité du livre
            String queryLivre = "UPDATE Livre SET disponibilite = FALSE WHERE id_livre = ?";
            PreparedStatement updateStatement = connection.prepareStatement(queryLivre);
            updateStatement.setInt(1, idLivre);
            updateStatement.executeUpdate();

            response.sendRedirect("livres.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Erreur lors de l'emprunt.");
        }
    }
}
