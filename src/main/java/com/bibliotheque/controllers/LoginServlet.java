/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.bibliotheque.controllers;

import com.bibliotheque.utils.DatabaseConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Utilisateur WHERE email = ? AND mot_de_passe = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Créer une session pour l'utilisateur
                HttpSession session = request.getSession();
                session.setAttribute("userId", resultSet.getInt("id_utilisateur"));
                session.setAttribute("role", resultSet.getString("role"));

                // Rediriger selon le rôle de l'utilisateur
                if ("bibliothecaire".equals(resultSet.getString("role"))) {
                    response.sendRedirect("livresBibliothecaire.jsp");
                } else {
                    response.sendRedirect("livres.jsp");
                }
            } else {
                response.getWriter().write("Identifiants incorrects !");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Une erreur s'est produite lors de la connexion.");
        }
    }
}
