package com.item.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.sql.DataSource;

import com.item.model.Item;
import com.item.service.ItemService;

public class ItemServiceImpl implements ItemService{
   
	private DataSource dataSource;
	
	
	public ItemServiceImpl(DataSource dataSource) {
		
		this.dataSource = dataSource;
		
	}

	@Override
	public boolean saveItem(Item item) {
		Connection connection = null;
	    PreparedStatement statement = null;
		try {
			connection =  dataSource.getConnection();
			String query = "INSERT INTO ITEM(NAME, PRICE, TOTAL_NUMBER) VALUES (?, ?, ?)"; 
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
	        if (statement != null) {
	            try {
	                statement.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
		
	}

	@Override
	public boolean removeItem(int id) {
		Connection connection = null;
	    PreparedStatement statement = null;
		try {
		    connection =  dataSource.getConnection();
			String query = "DELETE FROM ITEM WHERE ID = ?";
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
	        if (statement != null) {
	            try {
	                statement.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            } 
	        }
	    }
		return false;
	}

	@Override
	public boolean upadteItem(Item item) {
		Connection connection = null;
	    PreparedStatement statement = null;
		try {
		    connection =  dataSource.getConnection();
			String query = "UPDATE ITEM SET NAME = ?, PRICE = ?, TOTAL_NUMBER = ? WHERE ID = ?";
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
	        if (statement != null) {
	            try {
	                statement.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            } 
	        }
	    }
	}

	@Override
	public Item loadItem(int id) {
		Connection connection = null;
	    PreparedStatement statement = null;
		try {
		    connection =  dataSource.getConnection();
			String query = "SELECT * FROM ITEM WHERE ID = ?";
		    statement = connection.prepareStatement(query);
			
		    statement.setInt(1, id);
		    
			ResultSet rs = statement.executeQuery();
			
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
	        if (statement != null) {
	            try {
	                statement.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            } 
	        }
	    }
		return null;
	}

	@Override
	public List<Item> loadItems() {
		try {
			Connection connection =  dataSource.getConnection();
			String query = "SELECT * FROM ITEM ORDER BY ID";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			
			List<Item> items = new ArrayList<Item>();
			
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
		}
		return null;
	}


}
