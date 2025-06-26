<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="javax.servlet.http.Cookie" %>
<%@ page session="true" %>
<%
    Object user = session.getAttribute("loggedInUser");
    if (user != null) {
        response.sendRedirect("ItemController?action=load-items");
        return;
    }

    // Check cookies
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if ("rememberUser".equals(cookie.getName())) {
                // Redirect to controller to handle cookie-based login
                response.sendRedirect("UserAccountController?action=auto-login");
                return;
            }
        }
    }
%>


<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Login</title>
  <link href="https://fonts.googleapis.com/css?family=Open+Sans:600&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="css/auth.css">
</head>
<body>
<div class="login-wrap">
  <div class="login-html">
    <h2 style="color: white; text-align: center;">Login</h2>
    <div class="login-form">
      <form action="UserAccountController?action=login" method="post" class="sign-in-htm">
        <div class="group">
          <label for="username" class="label">Username</label>
          <input id="username" name="username" type="text" class="input" value="${user != null ? user.username : ''}">
        </div>
        <div class="group">
          <label for="password" class="label">Password</label>
          <input id="password" name="password" type="password" class="input" value="${user != null ? user.password : ''}">
        </div>
        <div class="group">
          <input type="submit" class="button" value="Login">
        </div>
      </form>
      <div class="foot-lnk" style="margin-top: 20px;">
        <a href="signup.jsp">Don't have an account? Sign up</a>
      </div>
      <% String error = (String) request.getAttribute("error");
         if (error != null) { %>
        <p class="error" style="color: red; text-align: center;"><%= error %></p>
      <% } %>
    </div>
  </div>
</div>
</body>
</html>
