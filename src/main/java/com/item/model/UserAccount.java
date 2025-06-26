package com.item.model;

import java.util.Date;

public class UserAccount {
	private int id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private Date createdAt;
    
    public UserAccount() {}

    public UserAccount(String username, String password,String email,String phone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public UserAccount(int id, String username, String password, Date createdAt,String email,String phone) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.createdAt = createdAt;
        this.email = email;
        this.phone = phone;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
    
    


}
