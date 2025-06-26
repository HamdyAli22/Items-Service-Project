package com.item.controller;

import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import com.item.model.ItemDetails;
import com.item.service.ItemDetailsService;
import com.item.service.impl.ItemDetailsServiceImpl;



@WebServlet("/ItemDetailsController")
public class ItemDetailsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	@Resource(name = "jdbc/web_item")
	private DataSource dataSource;
	
	private ItemDetailsService itemDetailsService;
    
    public ItemDetailsController() {
        super();
    }
    
    @Override
    public void init() throws ServletException {
    	itemDetailsService = new ItemDetailsServiceImpl(dataSource);
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userAction = request.getParameter("action");
		if (userAction == null) {
			userAction = "load-details";
		}
		
		switch(userAction) {
		    case "add-details":
			    addItemDetails(request, response);
			    break;
		    case "update-details":
				updateItemDetails(request, response);
				break;
			case "load-details":
				loadItemDetails(request, response);
				break; 
			default:
				loadItemDetails(request, response);
		}
	}

	
	private void loadItemDetails(HttpServletRequest request, HttpServletResponse response) {
		
		int ItemId = Integer.parseInt(request.getParameter("id"));
		ItemDetails itemDetails = itemDetailsService.getItemDetailsById(ItemId);
		
		request.setAttribute("itemDetailsData", itemDetails);
		
		try {
			request.getRequestDispatcher("/load-item-details.jsp").forward(request, response);
		} catch (Exception e) {
		  System.out.println(e.getMessage());
		}
		
	}

	private void updateItemDetails(HttpServletRequest request, HttpServletResponse response) {
      boolean updatedItemDetails = itemDetailsService.updateItemDetails(extractItemDetails(request));
		
		if(updatedItemDetails) {
			try {
				response.sendRedirect("ItemController?action=load-details");
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		
		}
		
	}

	private void addItemDetails(HttpServletRequest request, HttpServletResponse response) {
		
      boolean addeddItemDetails = itemDetailsService.saveItemDetails(extractItemDetails(request));
		
		if(addeddItemDetails) {
			try {
				response.sendRedirect("ItemController?action=load-items");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
		}
		
	}
    private ItemDetails extractItemDetails(HttpServletRequest request) {
		
    	 String description = request.getParameter("itemDesc");
         String brand = request.getParameter("itemBrand");
         String category = request.getParameter("itemCategory");
         int itemId = Integer.parseInt(request.getParameter("itemId"));

         return new ItemDetails(description, brand, category, itemId);
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
