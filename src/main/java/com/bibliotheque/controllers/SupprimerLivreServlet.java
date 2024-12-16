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

@WebServlet("/SupprimerLivreServlet")
public class SupprimerLivreServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String livreId = request.getParameter("id");  // Get the book ID from the request parameter

        if (livreId != null) {
            try (Connection connection = DatabaseConnection.getConnection()) {
                // SQL query to delete the book by id_livre
                String query = "DELETE FROM livre WHERE id_livre = ?";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setInt(1, Integer.parseInt(livreId));
                    int rowsDeleted = statement.executeUpdate();

                    if (rowsDeleted > 0) {
                        response.sendRedirect("livresBibliothecaire.jsp?success=true");  // Success: Redirect with success flag
                    } else {
                        response.sendRedirect("livresBibliothecaire.jsp?success=false&errorMessage=Erreur%20de%20suppression%20du%20livre.");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("livresBibliothecaire.jsp?success=false&errorMessage=Erreur%20de%20connexion%20à%20la%20base%20de%20données.");
            }
        } else {
            response.sendRedirect("livresBibliothecaire.jsp?success=false&errorMessage=Livre%20introuvable.");
        }
    }
}
