<%@ page import="java.util.*, com.cyrus.Contact" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
</head>
<body>
    <h2>Admin Dashboard</h2>

    <h3>Gender Statistics</h3>
    <table border="1">
        <tr>
            <th>Gender</th>
            <th>Total Contacts</th>
        </tr>
        <%
            Map<String, Integer> genderStats = (Map<String, Integer>) request.getAttribute("genderStats");

            if (genderStats != null) {  // ✅ Prevent NullPointerException
                for (String gender : genderStats.keySet()) {
        %>
                <tr>
                    <td><%= gender %></td>
                    <td><%= genderStats.get(gender) %></td>
                </tr>
        <%
                }
            } else {
        %>
                <tr>
                    <td colspan="2">No gender statistics available.</td>
                </tr>
        <%
            }
        %>
    </table>

    <h3>County Statistics</h3>
    <table border="1">
        <tr>
            <th>County</th>
            <th>Total Contacts</th>
        </tr>
        <%
            Map<String, Integer> countyStats = (Map<String, Integer>) request.getAttribute("countyStats");

            if (countyStats != null) {  // ✅ Prevent NullPointerException
                for (String county : countyStats.keySet()) {
        %>
                <tr>
                    <td><%= county %></td>
                    <td><%= countyStats.get(county) %></td>
                </tr>
        <%
                }
            } else {
        %>
                <tr>
                    <td colspan="2">No county statistics available.</td>
                </tr>
        <%
            }
        %>
    </table>
    <h3>Recent Contacts</h3>
    <table border="1">
        <tr>
            <th>Name</th>
            <th>Phone Number</th>
            <th>Email</th>
        </tr>
        <%
            List<Contact> recentContacts = (List<Contact>) request.getAttribute("recentContacts");

            if (recentContacts != null && !recentContacts.isEmpty()) {  // ✅ Prevent NullPointerException
                for (Contact contact : recentContacts) {
        %>
                <tr>
                    <td><%= contact.getFullName() %></td>
                    <td><%= contact.getPhoneNumber() %></td>
                    <td><%= contact.getEmail() %></td>
                </tr>
        <%
                }
            } else {
        %>
                <tr>
                    <td colspan="3">No recent contacts available.</td>
                </tr>   
        <%
            }
        %>
    </table>

</body>
</html>