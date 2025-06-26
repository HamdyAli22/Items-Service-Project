package com.item.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import com.item.model.UserAccount;
import com.item.service.UserService;
import com.item.service.impl.UserServiceImpl;



@WebServlet("/UserAccountController")
public class UserAccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Resource(name = "jdbc/web_item")
    private DataSource dataSource;
	
	private UserService userService;
   
    public UserAccountController() {
        super();
        
    }
    
    @Override
    public void init() throws ServletException {
        userService = new UserServiceImpl(dataSource);
    }
    
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String userAction = request.getParameter("action");
		    
		    if ("auto-login".equals(userAction)) {
		        autoLogin(request, response);
		    } else {
		        response.sendRedirect("login.jsp");
		    }
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userAction = request.getParameter("action");

		
		if (userAction == null) {
			userAction = "login";
		}
		
		switch (userAction) {
        case "signup":
            register(request, response);
            break;
        case "login":
            login(request, response);
            break;
        case "auto-login":
        	autoLogin(request, response);
            break;
        case "logout":
            logout(request, response);
            break;
        default:
            response.sendRedirect("login.jsp");
    }
		
	}

	private void autoLogin(HttpServletRequest request, HttpServletResponse response) {
	    try {
	    	
	        String rememberedUsername = null;

	        Cookie[] cookies = request.getCookies();
	        if (cookies != null) {
	            for (Cookie cookie : cookies) {
	                if ("rememberUser".equals(cookie.getName())) {
	                    rememberedUsername = cookie.getValue();
	                    break;
	                }
	            }
	        }

	        if (rememberedUsername != null && !rememberedUsername.trim().isEmpty()) {
	            UserAccount user = userService.getUserByUsername(rememberedUsername);
	       
	            if (user != null) {
	                HttpSession session = request.getSession(true);
	                session.setAttribute("loggedInUser", user);
	                response.sendRedirect("ItemController?action=load-items");
	                return;
	            }
	        }
	        
	        response.sendRedirect("login.jsp");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}




	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    HttpSession session = request.getSession(false);
	    if (session != null) {
	        session.invalidate();
	    }

	    Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if ("rememberUser".equals(cookie.getName())) {
	                cookie.setValue("");
	                cookie.setMaxAge(0);
	                cookie.setPath(request.getContextPath()); 
	                response.addCookie(cookie);
	                break;
	            }
	        }
	    }

	    response.sendRedirect("login.jsp");
	}


	private void login(HttpServletRequest request, HttpServletResponse response) {
	    String username = request.getParameter("username");
	    String password = request.getParameter("password");
	    
	    UserAccount user = new UserAccount(username, password,null,null);
	    
	    try {
	    String validationError = userService.validateUser(user,true);

	    if (validationError != null) {
	        request.setAttribute("error", validationError);
	        request.setAttribute("user", user); 
	        request.getRequestDispatcher("login.jsp").forward(request, response);
	        return;
	    }

	     user = userService.login(username, password);

	        if (user != null) {
	            HttpSession session = request.getSession();
	            session.setAttribute("loggedInUser", user);
	            
	            Cookie loginCookie = new Cookie("rememberUser", user.getUsername());
	            loginCookie.setMaxAge(60 * 60 * 24 * 7); 
	            response.addCookie(loginCookie);
	            
	            response.sendRedirect("ItemController?action=load-items");
	        } else {
	            request.setAttribute("error", "Invalid username or password");
	            request.getRequestDispatcher("login.jsp").forward(request, response);
	        }
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	    }
	}

	private void register(HttpServletRequest request, HttpServletResponse response) {
	    String username = request.getParameter("username");
	    String password = request.getParameter("password");
	    String email = request.getParameter("email");
        String phone = request.getParameter("phone");
          
	    UserAccount user = new UserAccount(username, password,email,phone);
	    
	    try {
	    String validationError = userService.validateUser(user,false);
         
        if (validationError != null) {
            request.setAttribute("error", validationError);
            request.setAttribute("user", user); 
            request.getRequestDispatcher("signup.jsp").forward(request, response);
            return;
        }
        
	    boolean success = userService.registerUser(user);

	    
	        if (success) {
	            response.sendRedirect("login.jsp");
	        } else {
	            request.setAttribute("error", "Signup failed");
	            request.getRequestDispatcher("signup.jsp").forward(request, response);
	        }
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        e.printStackTrace();
	    }
	}

}
