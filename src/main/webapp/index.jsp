<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, com.cyrus.Contact" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Manage Contacts</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>
<body class="container mt-4">
    <h1>Manage Contacts</h1>

    <!-- Contact Form -->
    <form id="contactForm">
        <!-- <input type="hidden" id="contactId" name="id"> -->
        <input type="text" id="fullName" name="full_name" placeholder="Full Name" required>
        <input type="email" id="emailAddress" name="email" placeholder="Email Address" required>
        <input type="text" id="phoneNumber" name="phone_number" placeholder="Phone Number" required>
        <input type="text" id="countyOfResidence" name="county_of_residence" placeholder="County of Residence" required>
        <input type="text" id="idNumber" name="id_number" placeholder="ID Number" required>
        <label>Gender:</label>
        <label><input type="radio" name="gender" value="Male" required> Male</label>
        <label><input type="radio" name="gender" value="Female" required> Female</label>
        <label>Date of Birth:</label>
        <input type="date" id="dateOfBirth" name="date_of_birth"/>
        <button type="submit">Save Contact</button>
    </form>

    <!-- Contact Table -->
    <h3>Contact List</h3>
    <table class="table table-bordered">
        <thead>
            <tr>
                <th>Name</th>
                <th>Email</th>
                <th>Phone</th>
                <th>County</th>
                <th>ID Number</th>
                <th>Date of Birth</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody id="contactTableBody">
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
                            <td><%= contact.getIdNumber() %></td>
                            <td><%= contact.getDateOfBirth() %></td>
                            <td>
                                <button class="edit btn btn-warning" data-id="<%= contact.getIdNumber() %>">Edit</button>
                                <button class="delete btn btn-danger" data-id="<%= contact.getIdNumber() %>">Delete</button>
                            </td>
                        </tr>
            <%
                    }
                } else {
            %>
                <tr>
                    <td colspan="7" class="text-center">No contacts available.</td>
                </tr>
            <%
                }
            %>
        </tbody>
    </table>

    <script>
        $(document).ready(function () {
            $("#contactForm").on("submit", function(event) {
                event.preventDefault();
                $.ajax({
                    url: "ContactServlet",
                    type: "POST",
                    data: $(this).serialize(),
                    success: function(response) {
                        alert("Contact saved successfully!");
                        $("#contactForm")[0].reset();
                        location.reload();
                    },
                    error: function() {
                        alert("Error saving contact!");
                    }
                });
            });

            $(".delete").on("click", function () {
                let contactId = $(this).data("id");
                if (confirm("Are you sure you want to delete this contact?")) {
                    $.ajax({
                        url: "ContactServlet?id=" + contactId,
                        type: "DELETE",
                        success: function() {
                            alert("Contact deleted successfully!");
                            location.reload();
                        },
                        error: function(xhr, status, error) {
                            alert("Error deleting contact!"+ xhr.responseText);
                            console.error("Error details: " + error);
                        }
                    });
                }
            });

            $(".edit").on("click", function () {
                let contactId = $(this).data("id");
                $.ajax({
                    url: "ContactServlet?id=" + contactId,
                    type: "PUT",
                    success: function(contact) {
                            // $("#contactId").val(contact.id);
                            $("#fullName").val(contact.full_name);
                            $("#emailAddress").val(contact.email);
                            $("#phoneNumber").val(contact.phone_number);
                            $("#countyOfResidence").val(contact.county_of_residence);
                            $("#idNumber").val(contact.id_number);
                            $("#dateOfBirth").val(contact.date_of_birth);
                            $("input[name='gender'][value='" + contact.gender + "']").prop("checked", true);

                        //change button to update
                         $("#contactForm button").text("Update Contact").attr("data-action", "update");
                    },
                    error: function() {
                        alert("Error fetching contact details!");
                    }
                });
            });
        });
    </script>
</body>
</html>