package com.ra.repository;

import com.ra.model.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWishListRepository extends JpaRepository<WishList, Long> {
}
