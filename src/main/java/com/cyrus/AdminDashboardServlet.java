package com.cyrus;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet(name = "AdminDashboardServlet", urlPatterns="/admin")
public class AdminDashboardServlet extends HttpServlet {
    private Connection connection;

    @Override
    public void init() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DatabaseConnection.URL, DatabaseConnection.USER, DatabaseConnection.PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(AdminDashboardServlet.class.getName()).log(java.util.logging.Level.SEVERE, "Database connection error: {0}", e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Statement stmt = connection.createStatement();

            try ( // Gender statistics
                    ResultSet resultSet = stmt.executeQuery("SELECT gender, COUNT(*) total FROM contacts GROUP BY gender")) {
                Map<String, Integer> genderStats = new java.util.HashMap<>();
                while (resultSet.next()) {
                    genderStats.put(resultSet.getString(1), resultSet.getInt(2));
                }
                request.setAttribute("genderStats", genderStats);
            }

            try ( // County statistics
                    ResultSet countyResultSet = stmt.executeQuery("SELECT  county_of_residence, COUNT(*) total FROM contacts GROUP BY county_of_residence")) {
                Map<String, Integer> countyStats = new java.util.HashMap<>();
                while (countyResultSet.next()) {
                    countyStats.put(countyResultSet.getString(1), countyResultSet.getInt(2));
                }
                request.setAttribute("countyStats", countyStats);
            }

            try (ResultSet recentResultSet = stmt.executeQuery("SELECT * FROM contacts ORDER BY id DESC LIMIT 5")) {
                List<Contact> recentContacts = new java.util.ArrayList<>();
                // Fetch recent contacts
                while (recentResultSet.next()) {
                    Contact contact = new Contact(
                            recentResultSet.getLong("id"),
                            recentResultSet.getString("full_name"),
                            recentResultSet.getString("phone_number"),
                            recentResultSet.getString("email_address"),
                            recentResultSet.getString("id_number"),
                            recentResultSet.getDate("date_of_birth").toLocalDate(),
                            recentResultSet.getString("gender"),
                            recentResultSet.getString("county_of_residence")
                            
                    );
                    recentContacts.add(contact);
                }   request.setAttribute("recentContacts", recentContacts);
         
            }

            // Forward to the admin dashboard JSP
            request.getRequestDispatcher("admin.jsp").forward(request, response);

        } catch (SQLException e) {
            Logger.getLogger(AdminDashboardServlet.class.getName()).log(java.util.logging.Level.SEVERE, "Database error: {0}", e.getMessage());
        }
    }
}