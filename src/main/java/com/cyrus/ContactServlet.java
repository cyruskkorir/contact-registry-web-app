package com.cyrus;
// ContactServlet.java

import java.io.BufferedReader;
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
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ContactServlet", urlPatterns = "/contact")
public class ContactServlet extends HttpServlet {
    private Connection connection;

    @Override
    public void init() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DatabaseConnection.URL, DatabaseConnection.USER, DatabaseConnection.PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(ContactServlet.class.getName()).log(Level.SEVERE, "Database connection error: {0}", e.getMessage());    
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
            try (
                PreparedStatement stmt = connection.prepareStatement(
                    "insert into contacts(full_name, phone_number, email_address, id_number, date_of_birth, gender, county_of_residence) values(?, ?, ?, ?, ?, ?, ?)"
                    );
            ) {
                stmt.setString(1, fullName);
                stmt.setString(2, phoneNumber);
                stmt.setString(3, email);
                stmt.setString(4, idNumber);
                stmt.setDate(5, Date.valueOf(dob));
                stmt.setString(6, gender);
                stmt.setString(7, county);
                stmt.execute();
            }
        } catch (SQLException e) {
            Logger.getLogger(ContactServlet.class.getName()).log(Level.SEVERE, "Error inserting contact: {0}", e.getMessage());
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
        } catch (SQLException e) {
            Logger.getLogger(ContactServlet.class.getName()).log(Level.SEVERE, "Error fetching contacts: {0}", e.getMessage());
        }

        request.setAttribute("contacts", contacts);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    // ✅ UPDATE: Modify Contact Information (Handled via PUT)
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String json = sb.toString();
        JSONObject jsonObject = new JSONObject(json);

        String fullName = jsonObject.getString("full_name");
        String phoneNumber = jsonObject.getString("phone_number");
        String email = jsonObject.getString("email_address");
        String idNUmber = jsonObject.getString("id_number");
        String dob = jsonObject.getString("date_of_birth");
        String county = jsonObject.getString("county_of_residence");



        Logger.getLogger(ContactServlet.class.getName()).log(Level.INFO, "Updating contact with ID: {0}", id);



        try (
            PreparedStatement stmt = connection.prepareStatement(
                "UPDATE contacts SET phone_number = ?, email_address = ?, id_number = ?, date_of_birth = ?, full_name = ?, county_of_residence = ? WHERE id = ?"
            )){

            stmt.setString(1, phoneNumber);
            stmt.setString(2, email);
            stmt.setString(3, idNUmber);
            stmt.setString(4, dob);
            stmt.setString(5, fullName);
            stmt.setString(6, county);
            stmt.setString(7, id);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            Logger.getLogger(ContactServlet.class.getName()).log(Level.SEVERE, "Error updating contact: {0}", e.getMessage());
        }

        response.getWriter().write("Contact Updated Successfully");
    }

    // ✅ DELETE: Remove Contact (Handled via DELETE)
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");

    
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM contacts WHERE id = ?")) {
                stmt.setString(1, id);
                stmt.executeUpdate();
                response.getWriter().write("Contact Deleted Successfully");
        } catch (SQLException e) {
            Logger.getLogger(ContactServlet.class.getName()).log(Level.SEVERE, "Error deleting contact: {0}", e.getMessage());
        }
    }
}