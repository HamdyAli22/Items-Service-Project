<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.item.model.Item" %>
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Show Items</title>
    <link rel="stylesheet" href="css/show-items.css">
</head>
<body>

<!-- Logout button -->
<form class="logout-form" action="UserAccountController" method="post">
    <input type="hidden" name="action" value="logout">
    <button type="submit">Logout</button>
</form>

<div class="layer">
    <table>
        <h1>Items</h1>
        <thead>
        <tr>
            <th>ID</th>
            <th>NAME</th>
            <th>PRICE</th>
            <th>TOTAL_NUMBER</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <% 
        List<Item> items = (List<Item>) request.getAttribute("itemsData");
        Map<Integer, Boolean> itemDetailsMap = (Map<Integer, Boolean>) request.getAttribute("itemDetailsMap");
        for(Item item : items){
            boolean hasDetails = itemDetailsMap.getOrDefault(item.getId(), false);
        %>
        <tr>
            <td><strong><%= item.getId() %></strong></td>
            <td><%= item.getName() %></td>
            <td><%= item.getPrice() %></td>
            <td><%= item.getTotalNumber() %></td>
            <td>
                <a href='ItemController?action=load-item&id=<%= item.getId() %>'>Update</a>
                <a href='ItemController?action=remove-item&id=<%= item.getId() %>'>Delete</a>
                <% if (!hasDetails) { %>
                    <a href='add-item-details.jsp?id=<%= item.getId() %>'>Add Item Details</a>
                <% } else { %>
                    <a href='ItemDetailsController?action=load-details&id=<%= item.getId() %>'>Show Item Details</a>
                <% } %>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>

    <button class="f"><a href="add-item.html">Add Item</a></button>
</div>

</body>
</html>
