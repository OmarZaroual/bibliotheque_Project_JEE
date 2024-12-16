package com.bibliotheque.utils;

import com.bibliotheque.models.Livre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LivreService {

    // Existing method to get available books
    public List<Livre> getLivresDisponibles() {
        List<Livre> livres = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Livre WHERE disponibilite = TRUE";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Livre livre = new Livre();
                livre.setId(resultSet.getInt("id_livre"));
                livre.setTitre(resultSet.getString("titre"));
                livre.setAuteur(resultSet.getString("auteur"));
                livre.setCategorie(resultSet.getString("categorie"));
                livre.setImageUrl(resultSet.getString("image_url"));
                livre.setDisponibilite(resultSet.getBoolean("disponibilite"));
                livres.add(livre);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return livres;
    }

    // New method to get a book by its ID
    public Livre getLivreById(int id) {
        Livre livre = null;
        String query = "SELECT * FROM livre WHERE id_livre = ?";

        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                livre = new Livre();
                livre.setId(resultSet.getInt("id_livre"));
                livre.setTitre(resultSet.getString("titre"));
                livre.setAuteur(resultSet.getString("auteur"));
                livre.setCategorie(resultSet.getString("categorie"));
                livre.setImageUrl(resultSet.getString("image_url"));
                livre.setDisponibilite(resultSet.getBoolean("disponibilite"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return livre;
    }
    
        public boolean updateLivre(Livre livre) {
        boolean updateSuccess = false;

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE Livre SET titre = ?, auteur = ?, categorie = ?, image_url = ?, disponibilite = ? WHERE id_livre = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, livre.getTitre());
                statement.setString(2, livre.getAuteur());
                statement.setString(3, livre.getCategorie());
                statement.setString(4, livre.getImageUrl());
                statement.setBoolean(5, livre.isDisponibilite());
                statement.setInt(6, livre.getId());

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    updateSuccess = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updateSuccess;
    }
        
        public Set<String> getCategories() {
        Set<String> categories = new HashSet<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT DISTINCT categorie FROM Livre";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                categories.add(resultSet.getString("categorie"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories;
    }

        
    public List<Livre> rechercherLivres(String titre, String auteur, String categorie) {
        List<Livre> livres = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Construction de la requête SQL dynamique
            StringBuilder query = new StringBuilder("SELECT * FROM Livre WHERE 1=1");

            // Ajout de conditions selon les critères de recherche
            if (titre != null && !titre.isEmpty()) {
                query.append(" AND titre LIKE ?");
            }
            if (auteur != null && !auteur.isEmpty()) {
                query.append(" AND auteur LIKE ?");
            }
            if (categorie != null && !categorie.isEmpty()) {
                query.append(" AND categorie LIKE ?");
            }

            PreparedStatement statement = connection.prepareStatement(query.toString());

            // Paramétrage de la requête
            int paramIndex = 1;
            if (titre != null && !titre.isEmpty()) {
                statement.setString(paramIndex++, "%" + titre + "%");
            }
            if (auteur != null && !auteur.isEmpty()) {
                statement.setString(paramIndex++, "%" + auteur + "%");
            }
            if (categorie != null && !categorie.isEmpty()) {
                statement.setString(paramIndex++, "%" + categorie + "%");
            }

            ResultSet resultSet = statement.executeQuery();

            // Récupérer les livres trouvés
            while (resultSet.next()) {
                Livre livre = new Livre();
                livre.setId(resultSet.getInt("id_livre"));
                livre.setTitre(resultSet.getString("titre"));
                livre.setAuteur(resultSet.getString("auteur"));
                livre.setCategorie(resultSet.getString("categorie"));
                livre.setImageUrl(resultSet.getString("image_url"));
                livre.setDisponibilite(resultSet.getBoolean("disponibilite"));
                livres.add(livre);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return livres;
    }

}
