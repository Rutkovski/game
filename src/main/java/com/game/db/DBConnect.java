package com.game.db;

import java.sql.*;

public class DBConnect {
    private final String USER = "root";
    private final String PASSWORD = "root";
    private final String URL = "jdbc:mysql://localhost/rpg?serverTimezone=Europe/Moscow";
    private Connection connection;
    private static DBConnect instance = new DBConnect();

    private DBConnect() {
    }

    public static DBConnect getInstance() {
        return instance;
    }

    public void connect() {

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Statement statement = conn.createStatement();
            System.out.println("Connection succesfull");
            ResultSet resultSet = statement.executeQuery("SELECT * FROM player");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name"));

            }

        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("failed");
        }
    }


}


