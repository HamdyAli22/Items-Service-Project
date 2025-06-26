<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Sign Up</title>
  <link href="https://fonts.googleapis.com/css?family=Open+Sans:600&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="css/auth.css">
</head>
<body>
<div class="login-wrap">
  <div class="login-html">
    <h2 style="color: white; text-align: center;">Sign Up</h2>
    <div class="login-form">
      <form action="UserAccountController?action=signup" method="post" class="sign-up-htm">
        <div class="group">
          <label for="username" class="label">Username</label>
          <input id="username" name="username" type="text" class="input" value="${user.username != null ? user.username : ''}">
        </div>
        <div class="group">
          <label for="password" class="label">Password</label>
          <input id="password" name="password" type="password" class="input" value="${user.password != null ? user.password : ''}">
        </div>
         <div class="group">
          <label for="email" class="label">Email</label>
          <input id="email" name="email" type="text" class="input" value="${user.email != null ? user.email : ''}">
        </div>
        <div class="group">
          <label for="phone" class="label">Phone Number</label>
          <input id="phone" name="phone" type="text" class="input" value="${user.phone != null ? user.phone : ''}">
        </div>
        <div class="group">
          <input type="submit" class="button" value="Sign Up">
        </div>
      </form>
      <div class="foot-lnk" style="margin-top: 20px;">
        <a href="login.jsp">Already have an account? Login</a>
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
