package com.ra.service.shoppingCartService;

import java.util.List;

import com.ra.exception.IdFormatException;
import com.ra.model.dto.request.AddShoppingCartRequest;
import com.ra.model.dto.response.ShoppingCartResponse;
import org.springframework.http.ResponseEntity;

import com.ra.exception.DataNotFoundException;
import com.ra.model.entity.ShoppingCart;
import com.ra.service.IGenericService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface IShoppingCartService extends IGenericService<ShoppingCart, Integer> {

	ResponseEntity<List<ShoppingCartResponse>> findByLoginUser(UserDetails userDetails) throws DataNotFoundException;
	
	ResponseEntity<ShoppingCartResponse> add(AddShoppingCartRequest request) throws DataNotFoundException, IdFormatException;

	ResponseEntity<ShoppingCart> edit(String cartItemId, String quantity) throws IdFormatException, DataNotFoundException;

	ResponseEntity<Void> clear() throws DataNotFoundException;

	ResponseEntity<Void> checkout() throws DataNotFoundException;
}
