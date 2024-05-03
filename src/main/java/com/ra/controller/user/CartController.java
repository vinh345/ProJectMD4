package com.ra.controller.user;

import java.util.List;

import com.ra.Common;
import com.ra.exception.IdFormatException;
import com.ra.model.dto.request.AddShoppingCartRequest;
import com.ra.model.dto.request.FormCartQuantity;
import com.ra.model.dto.response.ShoppingCartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.ra.exception.DataNotFoundException;
import com.ra.model.entity.ShoppingCart;
import com.ra.service.shoppingCartService.IShoppingCartService;

@RestController
@RequestMapping("/api.myservice.com/v1/user/cart")
public class CartController {
	
	@Autowired private IShoppingCartService cartService;
	
	/**
	 * Danh sách sản phẩm trong giỏ hàng
	 * @param pageable
	 * @return
	 * @throws DataNotFoundException 
	 */
	@GetMapping("list")
	public ResponseEntity<List<ShoppingCartResponse>> findByLoginUser(@PageableDefault Pageable pageable, @AuthenticationPrincipal UserDetails userDetails) throws DataNotFoundException {
		return cartService.findByLoginUser(userDetails);
	}

    /**
     * Thêm mới sản phẩm vào giỏ hàng (payload: productId and quantity)
     * @param
     * @param
     * @return
     * @throws DataNotFoundException
     */
    @PostMapping("add")
    public ResponseEntity<ShoppingCartResponse> add(@RequestBody AddShoppingCartRequest request) throws DataNotFoundException, IdFormatException {
    	return cartService.add(request);
    }

	/**
	 * Thay đổi số lượng đặt hàng của 1 sản phẩm  (payload :quantity)
	 * @param cartItemId
	 * @param quantity
	 * @return
	 * @throws DataNotFoundException
	 * @throws IdFormatException
	 */
    @PutMapping("/items/{cartItemId}")
    public ResponseEntity<ShoppingCart> edit(
    		@PathVariable("cartItemId") String cartItemId,
    		@RequestBody FormCartQuantity quantity) throws DataNotFoundException, IdFormatException {
		return cartService.edit(cartItemId, quantity.getQuantity());
    }

	/**
	 * Xóa 1 sản phẩm trong giỏ hàng
	 * @param cartItemId
	 * @return
	 * @throws IdFormatException
	 */
	@DeleteMapping("/items/{cartItemId}")
	public ResponseEntity<Void> delete(
			@PathVariable("cartItemId") String cartItemId) throws IdFormatException {
		int id = Common.getInt(cartItemId, "cartItemId không phải số");
		cartService.delete(id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Xóa toàn bộ sản phẩm trong giỏ hàng
	 * @return
	 * @throws DataNotFoundException
	 */
	@DeleteMapping("clear")
	public ResponseEntity<Void> clear() throws DataNotFoundException {
		return cartService.clear();
	}

	/**
	 * Đặt hàng
	 * @return
	 * @throws DataNotFoundException
	 */
	@PostMapping("checkout")
	public ResponseEntity<Void> checkout() throws DataNotFoundException {
		cartService.checkout();
		return ResponseEntity.noContent().build();
	}
}
