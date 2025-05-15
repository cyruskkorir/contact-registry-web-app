package com.cyrus;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/report")
public class ReportServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String countyFilter = request.getParameter("county");

        try {
            List<Contact> contacts;
            List<String> counties = new ArrayList<>();
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection(DatabaseConnection.URL, DatabaseConnection.USER, DatabaseConnection.PASSWORD)) {
                String sql = "SELECT * FROM contacts";
                if (countyFilter != null && !countyFilter.isEmpty()) {
                    sql += " WHERE county_of_residence = ?";
                }  try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                    if (countyFilter != null && !countyFilter.isEmpty()) {
                        stmt.setString(1, countyFilter);
                    }  try (ResultSet rs = stmt.executeQuery(); ResultSet rs2 = connection.createStatement().executeQuery("SELECT DISTINCT county_of_residence FROM contacts")) {
                        while (rs2.next()) {
                            counties.add(rs2.getString("county_of_residence"));
                        }
                        contacts = new ArrayList<>();
                        while (rs.next()) {
                            Contact contact = new Contact(
                                    rs.getLong("id"),
                                    rs.getString("full_name"),
                                    rs.getString("phone_number"),
                                    rs.getString("email_address"),
                                    rs.getString("id_number"),
                                    rs.getDate("date_of_birth").toLocalDate(),
                                    rs.getString("gender"),
                                    rs.getString("county_of_residence"));
                            contacts.add(contact);
                        }
                    }
                }
            }
            request.setAttribute("counties", counties);
            request.setAttribute("contacts", contacts);
            request.getRequestDispatcher("report.jsp").forward(request, response);

        } catch (SQLException e) {
            Logger.getLogger(ReportServlet.class.getName()).log(java.util.logging.Level.SEVERE, "Database error: {0}", e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReportServlet.class.getName()).log(java.util.logging.Level.SEVERE, "JDBC Driver not found: {0}", ex.getMessage());
        }
    }

    
}