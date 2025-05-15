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
        <input type="hidden" id="contactId" name="id">
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
        <button type="submit" class="btn btn-primary">Save Contact</button>
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
                                <button class="edit btn btn-warning" data-id="<%= contact.getId() %>" >Edit</button>
                                <button class="delete btn btn-danger" data-id="<%= contact.getId() %>">Delete</button>
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
                    url: "contact",
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
                        url: "contact?id=" + contactId,
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

            // Helper function to escape HTML entities for input values
            function escapeHtml(unsafe) {
                return unsafe
                     .replace(/&/g, "&amp;")
                     .replace(/</g, "&lt;")
                     .replace(/>/g, "&gt;")
                     .replace(/"/g, "&quot;")
                     .replace(/'/g, "&#039;");
            }

            // Define the Edit handler logic
            function handleEditClick() {
                let contactId = $(this).data("id");
                let $row = $(this).closest("tr"); // Store reference to the row
                let $editButton = $(this); // Store reference to the button

                // make the table row editable
                $row.find("td").each(function() {
                    let $cell = $(this);
                    if ($cell.index() < 6) { // Only make the first 6 cells editable
                        let currentValue = $cell.text();
                        // Replace cell content with an input field, pre-filled with current value
                        $cell.html("<input type='text' class='form-control form-control-sm' value='" + escapeHtml(currentValue) + "' />");
                    }
                });

                // Change button to Save and attach Save handler
                $editButton.text("Save").removeClass("edit btn-warning").addClass("save btn-success").off("click").on("click", handleSaveClick);

                // Store contactId on the row for easier access in save handler
                $row.data("contact-id", contactId);
            }

            // Define the Save handler logic
            function handleSaveClick() {
                let $saveButton = $(this);
                let $row = $saveButton.closest("tr");
                let contactId = $row.data("contact-id"); // Retrieve contactId from the row

                let updatedData = {};
                // Map cell indices to meaningful parameter names expected by the server
                // These should match the 'name' attributes in your main form and what your servlet expects
                let columnNames = ["full_name", "email", "phone_number", "county_of_residence", "id_number", "date_of_birth"];

                $row.find("input[type='text']").each(function() { // Target only the text inputs we created
                    let $input = $(this);
                    let cellIndex = $input.closest("td").index();
                    if (cellIndex < columnNames.length) {
                         updatedData[columnNames[cellIndex]] = $input.val();
                    }
                });

                $.ajax({
                    url: "contact?id=" + contactId,
                    type: "PUT",
                    data: updatedData, // Send the collected data with meaningful names
                    success: function(response) { // Assuming server might send back the updated contact or a success message
                        alert("Contact updated successfully!");

                        // Update the row with the new text values and revert inputs
                        $row.find("td").each(function() {
                            let $cell = $(this);
                            let cellIndex = $cell.index();
                            if (cellIndex < 6 && columnNames[cellIndex]) { // Only process the cells that were editable
                                 let newValue = updatedData[columnNames[cellIndex]];
                                 $cell.text(newValue !== undefined ? newValue : ''); // Set the cell text, handle undefined
                            }
                        });

                        // Revert button back to Edit and attach Edit handler
                        $saveButton.text("Edit").removeClass("save btn-success").addClass("edit btn-warning").off("click").on("click", handleEditClick);

                        // Remove the stored contactId from the row
                        $row.removeData("contact-id");

                        // No page reload needed!
                    },
                    error: function(xhr, status, error) {
                        alert("Error updating contact! " + xhr.responseText);
                        console.error("Error details: ", status, error, xhr.responseText);
                        // Optional: Revert button to Save or leave row editable on error,
                        // or even revert to original values if you stored them.
                        // For simplicity, we'll leave it as is, but you might want to revert the button:
                        // $saveButton.text("Save").removeClass("edit btn-warning").addClass("save btn-success"); // Keep it as Save
                    }
                });
            }

            // Attach the initial Edit handler to all buttons with class 'edit'
            $(".edit").on("click", handleEditClick);
        });
    </script>
</body>
</html>