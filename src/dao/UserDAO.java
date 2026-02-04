package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DBConnection;
import model.users;

public class UserDAO {

    public boolean registerUser(users user) {

        String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace(); // keep this ON
            return false;
        }
    }

   public users login(String email, String password) {

    String sql = "SELECT * FROM users WHERE email=? AND password=?";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, email);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new users(
                rs.getInt("user_id"),   // ✅ VERY IMPORTANT
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("role")
            );
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}


}
