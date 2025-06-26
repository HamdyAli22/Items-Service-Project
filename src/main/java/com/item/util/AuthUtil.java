package com.item.util;

import javax.servlet.http.*;
import javax.sql.DataSource;

import com.item.model.UserAccount;
import com.item.service.UserService;
import com.item.service.impl.UserServiceImpl;

public class AuthUtil {

    public static boolean ensureUserLoggedIn(HttpServletRequest request, HttpServletResponse response, DataSource dataSource) {
        try {
            HttpSession session = request.getSession(false);

            if (session != null && session.getAttribute("loggedInUser") != null) {
                return true;
            }

            // Check cookies
            Cookie[] cookies = request.getCookies();
            String rememberedUsername = null;

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("rememberUser".equals(cookie.getName())) {
                        rememberedUsername = cookie.getValue();
                        break;
                    }
                }
            }

            if (rememberedUsername != null) {
                UserService userService = new UserServiceImpl(dataSource);
                UserAccount user = userService.getUserByUsername(rememberedUsername);
                if (user != null) {
                    session = request.getSession(true); // create new session
                    session.setAttribute("loggedInUser", user);
                    return true;
                }
            }

            response.sendRedirect("login.jsp");
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

