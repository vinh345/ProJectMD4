package com.ra.repository;

import com.ra.model.entity.ShoppingCart;
import com.ra.model.entity.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {
	
	List<ShoppingCart> findByUser(User user);
}
