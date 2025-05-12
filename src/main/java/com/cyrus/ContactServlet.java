package com.cyrus;
// ContactServlet.java

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ContactServlet")
public class ContactServlet extends HttpServlet {
    private Connection connection;

    @Override
    public void init() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DatabaseConnection.url, DatabaseConnection.user, DatabaseConnection.password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ✅ CREATE: Add New Contact (Handled via POST)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullName = request.getParameter("full_name");
        String phoneNumber = request.getParameter("phone_number");
        String email = request.getParameter("email");
        String idNumber = request.getParameter("id_number");
        String dob = request.getParameter("date_of_birth");
        String gender = request.getParameter("gender");
        String county = request.getParameter("county_of_residence");

        try {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "update contacts set full_name=?, phone_number=?, email_address=?, id_number=?, date_of_birth=?, gender=?, county_of_residence=? where id_number=?"
            )) {
                stmt.setString(1, fullName);
                stmt.setString(2, phoneNumber);
                stmt.setString(3, email);
                stmt.setString(4, idNumber);
                stmt.setDate(5, Date.valueOf(dob));
                stmt.setString(6, gender);
                stmt.setString(7, county);
                stmt.setString(8, idNumber); // id_number is the primary key
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
        }

        response.sendRedirect("index.jsp"); // Redirect to main page after insert
    }

    // ✅ READ: Fetch All Contacts (Handled via GET)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Contact> contacts = new ArrayList<>();

        try {
            try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM contacts")) {
                
                while (rs.next()) {
                    Contact contact = new Contact(
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
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("contacts", contacts);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    // ✅ UPDATE: Modify Contact Information (Handled via PUT)
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id_number");
        String phoneNumber = request.getParameter("phone_number");
        String email = request.getParameter("email");

        try {
            PreparedStatement stmt = connection.prepareStatement(
                "UPDATE contacts SET phone_number = ?, email_address = ? WHERE id = ?"
            );
            stmt.setString(1, phoneNumber);
            stmt.setString(2, email);
            stmt.setString(3, id);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.getWriter().write("Contact Updated Successfully");
    }

    // ✅ DELETE: Remove Contact (Handled via DELETE)
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");

        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM contacts WHERE id_number = ?");
            stmt.setString(1, id);
            stmt.executeUpdate();
            stmt.close();
            response.getWriter().write("Contact Deleted Successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}