package com.item.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import javax.sql.DataSource;


import com.item.model.ItemDetails;
import com.item.service.ItemDetailsService;
import com.item.util.JdbcUtil;

public class ItemDetailsServiceImpl implements ItemDetailsService{
	
	private DataSource dataSource;
	
    public ItemDetailsServiceImpl(DataSource dataSource) {
		
		this.dataSource = dataSource;
		
	}


	@Override
	public ItemDetails getItemDetailsById(int id) {
		Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet rs = null;
	    
	    String query = "SELECT * FROM ITEM_DETAILS WHERE ITEM_ID = ?";
	    
	    try {
		    connection =  dataSource.getConnection();
			
		    statement = connection.prepareStatement(query);
			
		    statement.setInt(1, id);
		    
		    rs = statement.executeQuery();
			
			if(rs.next()) {
				return new ItemDetails(
						rs.getInt("id"),
						rs.getString("description"),
						rs.getString("brand"),
						rs.getString("category"),
						rs.getInt("item_id")
						);
		         
				
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			JdbcUtil.close(connection, statement,rs);
	    }
	    
		return null;
	}

	@Override
	public boolean saveItemDetails(ItemDetails itemDetails) {
		Connection connection = null;
	    PreparedStatement statement = null;
	    String query = "INSERT INTO ITEM_DETAILS(DESCRIPTION, BRAND, CATEGORY, ITEM_ID) VALUES (?, ?, ?, ?)"; 
		try {
			connection =  dataSource.getConnection();
			statement = connection.prepareStatement(query);
			
			statement.setString(1, itemDetails.getDescription());
	        statement.setString(2, itemDetails.getBrand());
	        statement.setString(3, itemDetails.getCategory());
	        statement.setInt(4, itemDetails.getItemId());
			
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
	public boolean updateItemDetails(ItemDetails itemDetails) {
		Connection connection = null;
	    PreparedStatement statement = null;
	    String query = "UPDATE ITEM_DETAILS SET DESCRIPTION = ?, BRAND = ?, CATEGORY = ? WHERE ITEM_ID = ?";
		try {
		    connection =  dataSource.getConnection();
			
		    statement = connection.prepareStatement(query);
			
			 statement.setString(1, itemDetails.getDescription());
		     statement.setString(2, itemDetails.getBrand());
		     statement.setString(3, itemDetails.getCategory());
		     statement.setInt(4, itemDetails.getItemId());
		     
			int res =  statement.executeUpdate();
			
	        return res == 1;
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}finally {
			JdbcUtil.close(connection, statement);
	    }
	}

	
}
