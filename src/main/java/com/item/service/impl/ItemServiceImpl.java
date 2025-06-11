package com.item.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.sql.DataSource;

import com.item.model.Item;
import com.item.service.ItemService;
import com.item.util.JdbcUtil;

public class ItemServiceImpl implements ItemService{
   
	private DataSource dataSource;
	
	
	public ItemServiceImpl(DataSource dataSource) {
		
		this.dataSource = dataSource;
		
	}

	@Override
	public boolean saveItem(Item item) {
		Connection connection = null;
	    PreparedStatement statement = null;
	    String query = "INSERT INTO ITEM(NAME, PRICE, TOTAL_NUMBER) VALUES (?, ?, ?)"; 
		try {
			connection =  dataSource.getConnection();
			statement = connection.prepareStatement(query);
			
			statement.setString(1, item.getName());
	        statement.setDouble(2, item.getPrice());
	        statement.setInt(3, item.getTotalNumber());
			
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
	public boolean removeItem(int id) {
		Connection connection = null;
	    PreparedStatement statement = null;
	    String query = "DELETE FROM ITEM WHERE ID = ?";
		try {
		    connection =  dataSource.getConnection();
		    statement = connection.prepareStatement(query);
			
			if (Objects.isNull(loadItem(id))) {
	            return false;
	        }
			
			 statement.setInt(1, id);
		     int res = statement.executeUpdate();

		      return res == 1;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			JdbcUtil.close(connection, statement);
	    }
		return false;
	}

	@Override
	public boolean upadteItem(Item item) {
		Connection connection = null;
	    PreparedStatement statement = null;
	    String query = "UPDATE ITEM SET NAME = ?, PRICE = ?, TOTAL_NUMBER = ? WHERE ID = ?";
		try {
		    connection =  dataSource.getConnection();
			
		    statement = connection.prepareStatement(query);
			
			 statement.setString(1, item.getName());
		     statement.setDouble(2, item.getPrice());
		     statement.setInt(3, item.getTotalNumber());
		     statement.setInt(4, item.getId());
		     
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
	public Item loadItem(int id) {
		Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet rs = null;
	    String query = "SELECT * FROM ITEM WHERE ID = ?";
		try {
		    connection =  dataSource.getConnection();
			
		    statement = connection.prepareStatement(query);
			
		    statement.setInt(1, id);
		    
		    rs = statement.executeQuery();
			
			if(rs.next()) {
				return new Item(
						rs.getInt("id"),
						rs.getString("name"),
						rs.getDouble("price"),
						rs.getInt("total_number")
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
	public List<Item> loadItems() {
		Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet rs = null;
	    String query = "SELECT * FROM ITEM ORDER BY ID";
	    List<Item> items = new ArrayList<Item>();
		try {
			connection =  dataSource.getConnection();
		    statement    = connection.prepareStatement(query);
		    rs = statement.executeQuery();
			
			while(rs.next()) {
				Item item = new Item(
						rs.getInt("id"),
						rs.getString("name"),
						rs.getDouble("price"),
						rs.getInt("total_number")
						);
		         
				items.add(item);
			}
			
			return items;
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			JdbcUtil.close(connection, statement,rs);
	    }
		return null;
	}


}
