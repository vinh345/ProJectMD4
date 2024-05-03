package com.ra.repository;

import com.ra.model.entity.Address;
import com.ra.model.entity.User;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IAddressRepository extends JpaRepository<Address, Long> {

	Set<Address> findByUser(User user);
	
	int deleteByUser(User user);
}
