package com.cyrus;

import java.sql.ResultSet;

public class Main {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try (
        
            java.sql.Connection connection = java.sql.DriverManager.getConnection(DatabaseConnection.url, DatabaseConnection.user, DatabaseConnection.password);
            java.sql.Statement stmt = connection.createStatement();
        ) {
            ResultSet resultSet = stmt.executeQuery("SELECT gender, COUNT(*) total FROM contacts GROUP BY gender");
        //    ResultSet resultSet = stmt.executeQuery("SELECT * FROM contacts");
            while (resultSet.next()) {
                // System.out.println(resultSet.getString(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3) + " " + resultSet.getString(4));
                System.out.println(resultSet.getString(1) + " " + resultSet.getInt(2));
            }
            resultSet.close();
            stmt.close();
            
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}