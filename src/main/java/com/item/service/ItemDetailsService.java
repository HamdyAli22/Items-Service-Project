package com.item.service;

import com.item.model.ItemDetails;


public interface ItemDetailsService {

    ItemDetails getItemDetailsById(int id);

    boolean saveItemDetails(ItemDetails itemDetails);

    boolean updateItemDetails(ItemDetails itemDetails);
    
}

