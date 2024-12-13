package com.bibliotheque.utils;

import com.bibliotheque.models.Livre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LivreService {
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
}
