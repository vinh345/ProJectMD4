package com.ra.service.wishListService;

import com.ra.model.entity.WishList;
import com.ra.repository.IWishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WishListServiceIMPL implements IWishListService {

    @Autowired private IWishListRepository wishListRepository;
    @Override
    public Page<WishList> findAll(Pageable pageable) {
        return wishListRepository.findAll(pageable);
    }

    @Override
    public Optional<WishList> findById(Long id) {
        return wishListRepository.findById(id);
    }

    @Override
    public WishList save(WishList wishList) {
        return wishListRepository.save(wishList);
    }

    @Override
    public void delete(Long id) {
        wishListRepository.deleteById(id);
    }
}
