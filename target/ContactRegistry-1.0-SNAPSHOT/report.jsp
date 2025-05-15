<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, com.cyrus.Contact" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Contact Report</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid black;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
    <h1>Contact Report</h1>

    <!-- Filter Contacts by County -->
    <form method="get" action="ReportServlet">
        <label for="county">Filter by County:</label>
        <select name="county" id="county">
            <option value="">All Counties</option>
            <%
                List<String> counties = (List<String>) request.getAttribute("counties");
                if (counties != null) {
                    for (String county : counties) {
            %>
                <option value="<%= county %>"><%= county %></option>
            <%
                    }
                }
            %>
        </select>
        <button type="submit">Filter</button>
    </form>

    <!-- Contacts Report Table -->
    <table>
        <thead>
            <tr>
                <th>Name</th>
                <th>Email</th>
                <th>Phone</th>
                <th>County</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<Contact> contacts = (List<Contact>) request.getAttribute("contacts");
                if (contacts != null && !contacts.isEmpty()) {
                    for (Contact contact : contacts) {
            %>
                <tr>
                    <td><%= contact.getFullName() %></td>
                    <td><%= contact.getEmail() %></td>
                    <td><%= contact.getPhoneNumber() %></td>
                    <td><%= contact.getCountyOfResidence() %></td>
                </tr>
            <%
                    }
                } else {
            %>
                <tr>
                    <td colspan="4" class="text-center">No contacts available.</td>
                </tr>
            <%
                }
            %>
        </tbody>
    </table>

    <!-- Print Report Button -->
    <button onclick="window.print()">Print Report</button>
</body>
</html>