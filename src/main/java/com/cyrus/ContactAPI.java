package com.cyrus;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/api/contacts")
public class ContactAPI extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            Connection connection = DriverManager.getConnection(DatabaseConnection.url, DatabaseConnection.user, DatabaseConnection.password);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Contacts");

            JSONArray contactList = new JSONArray();
            while (rs.next()) {
                JSONObject contact = new JSONObject();
                contact.put("id", rs.getInt("id"));
                contact.put("name", rs.getString("name"));
                contact.put("email", rs.getString("email"));
                contact.put("gender", rs.getString("gender"));
                contact.put("county", rs.getString("county"));
                contactList.put(contact);
            }

            out.print(contactList.toString());
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
