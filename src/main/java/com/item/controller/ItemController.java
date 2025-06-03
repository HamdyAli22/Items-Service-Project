package com.item.controller;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import com.item.service.impl.ItemServiceImpl;
import com.item.model.Item;
import com.item.service.ItemService;

@WebServlet("/ItemController")
public class ItemController extends HttpServlet {
	
	   @Resource(name = "jdbc/web_item")
		private DataSource dataSource;
	
	private ItemService itemService;
	   
	private static final long serialVersionUID = 1L;
       

    public ItemController() {
        super();
        
    }
    
    @Override
    public void init() throws ServletException {
        itemService = new ItemServiceImpl(dataSource);
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
String userAction = request.getParameter("action");
		
		if (userAction == null) {
			userAction = "load-items";
		}
		
		switch(userAction) {
		    case "add-item":
			    addItem(request, response);
			    break;
		    case "remove-item":
				removeItem(request, response);
				break;
		    case "update-item":
				updateItem(request, response);
				break;
			case "load-item":
				loadItem(request, response);
				break; 
			case "load-items":
				loadItems(request, response);
				break;
			default:
				loadItems(request, response);
		}
	}

	private void updateItem(HttpServletRequest request, HttpServletResponse response) {
		 
		boolean updatedItem = itemService.upadteItem(extractItem(request));
		
		if(updatedItem) {
			loadItems(request, response);
		}
	}


	private void addItem(HttpServletRequest request, HttpServletResponse response) {
	          
		boolean addeddItem = itemService.saveItem(extractItem(request));
		
		if(addeddItem) {
			loadItems(request, response);
		}
		
	}
	
	private void removeItem(HttpServletRequest request, HttpServletResponse response) {
		
		int id = Integer.parseInt(request.getParameter("id"));
		boolean removedItem = itemService.removeItem(id);
		
		if(removedItem) {
			loadItems(request, response);
		}
		
	}
	
	private void loadItem(HttpServletRequest request, HttpServletResponse response) {
		
		int id = Integer.parseInt(request.getParameter("id"));
		Item item = itemService.loadItem(id);
        request.setAttribute("itemData", item);
		
		try {
			request.getRequestDispatcher("/update-item.jsp").forward(request, response);
		} catch (Exception e) {
		  System.out.println(e.getMessage());
		}
		
	}


	private void loadItems(HttpServletRequest request, HttpServletResponse response) {
		
		List<Item> items = itemService.loadItems();
		
		request.setAttribute("itemsData", items);
		
		try {
			request.getRequestDispatcher("/load-items.jsp").forward(request, response);
		} catch (Exception e) {
		  System.out.println(e.getMessage());
		}
		
	}
	
	private Item extractItem(HttpServletRequest request) {
		
		String itemName        = request.getParameter("itemName");
		double itemPrice       = Double.parseDouble(request.getParameter("itemPrice"));
		int itemTotalNumber        = Integer.parseInt(request.getParameter("itemTotalNumber")); 
		
		Item item = new Item(itemName,itemPrice,itemTotalNumber);
		
		String itemId = request.getParameter("itemId");
		
		if(Objects.nonNull(itemId)) {
			item.setId(Integer.parseInt(itemId));
		}
		
		return item;
		
	}
	
}

	
