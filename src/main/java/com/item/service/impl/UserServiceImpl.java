package com.item.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.item.model.UserAccount;
import com.item.service.UserService;
import com.item.util.JdbcUtil;

public class UserServiceImpl implements UserService{
	
	private DataSource dataSource;
	
	 public UserServiceImpl(DataSource dataSource) {
	        this.dataSource = dataSource;
	    }

	@Override
	public boolean registerUser(UserAccount user) {
		Connection connection = null;
	    PreparedStatement statement = null;
		String query = "INSERT INTO USER_ACCOUNT (USERNAME, PASSWORD, EMAIL, PHONE) VALUES (?, ?, ?, ?)";
		try {
			connection =  dataSource.getConnection();
			statement = connection.prepareStatement(query);
			
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword()); 
			statement.setString(3, user.getEmail());
			statement.setString(4, user.getPhone());
				
			int res =  statement.executeUpdate();
			
			return res == 1;
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}finally {
			JdbcUtil.close(connection, statement);
	    }
	}

	@Override
	public UserAccount login(String username, String password) {
		Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet rs = null;
		String query = "SELECT * FROM USER_ACCOUNT WHERE USERNAME = ? AND PASSWORD = ?";
		try {
			connection =  dataSource.getConnection();
			statement = connection.prepareStatement(query);
			
			statement.setString(1, username);
			statement.setString(2, password);
				
			rs =  statement.executeQuery();
			
			if (rs.next()) {
                return new UserAccount(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getDate("created_at"),
                    rs.getString("email"),
                    rs.getString("phone")
                );
            }
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}finally {
			JdbcUtil.close(connection, statement,rs);
	    }
		return null;
	}

	@Override
	public String validateUser(UserAccount user,boolean forLogin) {
		
		String username = user.getUsername();
	    String password = user.getPassword();
	    String email = user.getEmail();
	    String phone = user.getPhone();
	    
	    
	    if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
	        return "Username is required.";
	    }
	    
		if (username == null || username.length() <= 5) {
	        return "Username must be more than 5 characters";
	    }

	    if (!forLogin && isUserExist(username)) {
	        return "Username already exists";
	    }
	    
	    if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
	        return "Password is required.";
	    }
	    
	    if (password == null || password.length() < 7) {
	        return "Password must be at least 7 characters long";
	    }
	    
	    String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$#@%&]).+$";

	    if (!password.matches(passwordPattern)) {
	        return "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character";
	    }
	    
	    if (email != null && !email.trim().isEmpty()) {
	        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")) {
	            return "Invalid email address";
	        }
	        if (isEmailExist(email)) {
	            return "Email already exists";
	        }
	    }
	    
	    if (phone != null && !phone.trim().isEmpty()) {
	        if (!phone.matches("^\\d{10,15}$")) {
	            return "Invalid phone number. Only digits allowed (10–15 digits)";
	        }
	        if (isPhoneExist(phone)) {
	            return "Phone number already exists";
	        }
	    }
		return null;
	}

	private boolean isUserExist(String username) {
		Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    String query = "SELECT COUNT(*) FROM user_account WHERE username = ?";
	    try {
	        conn = dataSource.getConnection();
	        stmt = conn.prepareStatement(query);
	        stmt.setString(1, username);
	        rs = stmt.executeQuery();

	        if (rs.next()) {
	            return rs.getInt(1) > 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        JdbcUtil.close(conn, stmt, rs);
	    }
		return false;
	}
	
	private boolean isEmailExist(String email) {
		Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    String query = "SELECT COUNT(*) FROM user_account WHERE email = ?";
	    try {
	        conn = dataSource.getConnection();
	        stmt = conn.prepareStatement(query);
	        stmt.setString(1, email);
	        rs = stmt.executeQuery();

	        if (rs.next()) {
	            return rs.getInt(1) > 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        JdbcUtil.close(conn, stmt, rs);
	    }
		return false;
	}
	
	private boolean isPhoneExist(String phone) {
		Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    String query = "SELECT COUNT(*) FROM user_account WHERE phone = ?";
	    try {
	        conn = dataSource.getConnection();
	        stmt = conn.prepareStatement(query);
	        stmt.setString(1, phone);
	        rs = stmt.executeQuery();

	        if (rs.next()) {
	            return rs.getInt(1) > 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        JdbcUtil.close(conn, stmt, rs);
	    }
		return false;
	}

	@Override
	public UserAccount getUserByUsername(String username) {
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

	    try {
	        conn = dataSource.getConnection();
	        String sql = "SELECT * FROM user_account WHERE username = ?";
	        stmt = conn.prepareStatement(sql);
	        stmt.setString(1, username);
	        rs = stmt.executeQuery();

	        if (rs.next()) {
	            return new UserAccount(
	                rs.getInt("id"),
	                rs.getString("username"),
	                rs.getString("password"),
	                rs.getDate("created_at"),
	                rs.getString("email"),
	                rs.getString("phone")
	            );
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        JdbcUtil.close(conn, stmt, rs);
	    }

	    return null;
	}

}
