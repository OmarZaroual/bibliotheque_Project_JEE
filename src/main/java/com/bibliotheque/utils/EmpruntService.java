/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bibliotheque.utils;

import com.bibliotheque.models.Emprunt;
import com.bibliotheque.models.Livre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmpruntService {

    /**
     * Récupère la liste des emprunts d'un utilisateur donné.
     *
     * @param idUtilisateur L'identifiant de l'utilisateur.
     * @return Une liste d'emprunts avec les informations des livres associés.
     */
    public List<Emprunt> getEmpruntsParUtilisateur(int idUtilisateur) {
        List<Emprunt> emprunts = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            // Requête pour récupérer les emprunts d'un utilisateur avec les détails des livres
            String query = "SELECT e.id_emprunt, e.date_emprunt, e.date_retour, " +
                           "l.id_livre, l.titre, l.auteur, l.categorie, l.image_url " +
                           "FROM Emprunt e " +
                           "INNER JOIN Livre l ON e.id_livre = l.id_livre " +
                           "WHERE e.id_utilisateur = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idUtilisateur);

            ResultSet resultSet = statement.executeQuery();

            // Parcourir les résultats et construire les objets Emprunt et Livre
            while (resultSet.next()) {
                Emprunt emprunt = new Emprunt();
                emprunt.setId(resultSet.getInt("id_emprunt"));
                emprunt.setDateEmprunt(resultSet.getString("date_emprunt"));
                emprunt.setDateRetour(resultSet.getString("date_retour"));

                // Créer un objet Livre et associer ses informations
                Livre livre = new Livre();
                livre.setId(resultSet.getInt("id_livre"));
                livre.setTitre(resultSet.getString("titre"));
                livre.setAuteur(resultSet.getString("auteur"));
                livre.setCategorie(resultSet.getString("categorie"));
                livre.setImageUrl(resultSet.getString("image_url")); // Facultatif si votre table contient une colonne image_url

                // Associer le livre à l'emprunt
                emprunt.setLivre(livre);

                // Ajouter l'emprunt à la liste
                emprunts.add(emprunt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return emprunts;
    }

    /**
     * Permet de retourner un emprunt et de mettre à jour l'état du livre dans la base de données.
     *
     * @param idEmprunt L'identifiant de l'emprunt.
     * @return true si le retour a été effectué avec succès, false sinon.
     */
    public boolean retournerEmprunt(int idEmprunt) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Mettre à jour la date de retour de l'emprunt
            String updateEmpruntQuery = "UPDATE Emprunt SET date_retour = ? WHERE id_emprunt = ?";
            PreparedStatement empruntStatement = connection.prepareStatement(updateEmpruntQuery);
            empruntStatement.setString(1, java.time.LocalDate.now().toString());
            empruntStatement.setInt(2, idEmprunt);
            int rowsAffected = empruntStatement.executeUpdate();

            if (rowsAffected > 0) {
                // Récupérer l'identifiant du livre associé
                String selectLivreQuery = "SELECT id_livre FROM Emprunt WHERE id_emprunt = ?";
                PreparedStatement selectStatement = connection.prepareStatement(selectLivreQuery);
                selectStatement.setInt(1, idEmprunt);
                ResultSet resultSet = selectStatement.executeQuery();

                if (resultSet.next()) {
                    int idLivre = resultSet.getInt("id_livre");

                    // Mettre à jour la disponibilité du livre
                    String updateLivreQuery = "UPDATE Livre SET disponibilite = TRUE WHERE id_livre = ?";
                    PreparedStatement livreStatement = connection.prepareStatement(updateLivreQuery);
                    livreStatement.setInt(1, idLivre);
                    livreStatement.executeUpdate();
                }

                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
