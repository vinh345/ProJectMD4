package com.ra.service.shoppingCartService;

import com.ra.Common;
import com.ra.exception.DataNotFoundException;
import com.ra.exception.IdFormatException;
import com.ra.model.dto.request.AddShoppingCartRequest;
import com.ra.model.dto.response.ShoppingCartResponse;
import com.ra.model.entity.*;
import com.ra.repository.IOrderDetailRepository;
import com.ra.repository.IOrderRepository;
import com.ra.repository.IProductRepository;
import com.ra.repository.IShoppingCartRepository;
import com.ra.repository.IUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartServiceIMPL implements IShoppingCartService {

    @Autowired
    private IShoppingCartRepository shoppingCartRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    private IOrderDetailRepository orderDetailRepository;

    @Override
    public Page<ShoppingCart> findAll(Pageable pageable) {
        return shoppingCartRepository.findAll(pageable);
    }

    @Override
    public Optional<ShoppingCart> findById(Integer id) {
        return shoppingCartRepository.findById(id);
    }

    @Override
    public ShoppingCart save(ShoppingCart shoppingCart) {
        return shoppingCartRepository.save(shoppingCart);
    }


    @Override
    public void delete(Integer id) {
        shoppingCartRepository.deleteById(id);
    }


    @Override
    public ResponseEntity<List<ShoppingCartResponse>> findByLoginUser(UserDetails userDetails) throws DataNotFoundException {
        String username = userDetails.getUsername();
        List<ShoppingCart> carts = shoppingCartRepository.findByUser(userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("username not found")));

        List<ShoppingCartResponse> cartResponses = new ArrayList<>();
        for (ShoppingCart cart : carts) {
            ShoppingCartResponse response = ShoppingCartResponse.builder()
                    .shoppingCartId(cart.getShoppingCartId())
                    .sku(cart.getProduct().getSku())
                    .productName(cart.getProduct().getProductName())
                    .description(cart.getProduct().getDescription())
                    .price(cart.getProduct().getPrice())
                    .image(cart.getProduct().getImage())
                    .categoryName(cart.getProduct().getCategory().getCategoryName())
                    .username(cart.getUser().getUsername())
                    .build();
            cartResponses.add(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(cartResponses);
    }


    @Override
    public ResponseEntity<ShoppingCartResponse> add(AddShoppingCartRequest request) throws DataNotFoundException, IdFormatException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            throw new DataNotFoundException("không thấy User đang Login");
        }
        Long prodId = Common.getLong(request.getProductId(), "productId không phải là số");
        int quantity = Common.getInt(request.getQuantity(), "quantity không phải là số");
        Optional<Product> product = productRepository.findById(prodId);
        if (product.isEmpty()) {
            throw new DataNotFoundException("productId không tìm thấy");
        }

        ShoppingCart cartItem = new ShoppingCart(null, product.get(), user.get(), quantity); // TODO

        ShoppingCart newShoppingCart = shoppingCartRepository.save(cartItem);
        ShoppingCartResponse response = ShoppingCartResponse.builder()
                .shoppingCartId(newShoppingCart.getShoppingCartId())
                .sku(newShoppingCart.getProduct().getSku())
                .productName(newShoppingCart.getProduct().getProductName())
                .description(newShoppingCart.getProduct().getDescription())
                .price(newShoppingCart.getProduct().getPrice())
                .image(newShoppingCart.getProduct().getImage())
                .categoryName(newShoppingCart.getProduct().getCategory().getCategoryName())
                .username(newShoppingCart.getUser().getUsername())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    //thay đổi số lượng đặt hàng của 1 sản phầm
    @Override
    public ResponseEntity<ShoppingCart> edit(String cartItemId, String quantity) throws IdFormatException, DataNotFoundException {
        int id = Common.getInt(cartItemId, "cartItemId không phải số");
        int orderNo = Common.getInt(quantity, "cartItemId không phải số");
        Optional<ShoppingCart> cart = shoppingCartRepository.findById(id);
        if (cart.isEmpty()) {
            throw new DataNotFoundException("cartItemId không tìm thấy");
        }
        ShoppingCart shoppingCart = cart.get();
        shoppingCart.setOrderQuantity(orderNo);
        return ResponseEntity.ok(shoppingCartRepository.save(shoppingCart));
    }


    // xóa giỏ hàng
    @Override
    public ResponseEntity<Void> clear() throws DataNotFoundException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            throw new DataNotFoundException("không thấy User đang Login");
        }
        List<ShoppingCart> list = shoppingCartRepository.findByUser(user.get());
        shoppingCartRepository.deleteAll(list);
        return ResponseEntity.noContent().build();
    }


    //
    @Override
    public ResponseEntity<Void> checkout() throws DataNotFoundException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            throw new DataNotFoundException("không thấy User đang Login");
        }
        List<ShoppingCart> list = shoppingCartRepository.findByUser(user.get());
        Orders orders = new Orders();
        orders.setUser(user.get());
        orders.setStatus(OrderStatus.WAITING);
        orders.setCreatedAt(new Date());
        orderRepository.save(orders);
        BigDecimal bigDecimalValue = new BigDecimal(0);
        list.forEach(each -> {
            OrderDetail detail = new OrderDetail(
            		orders, 
            		each.getProduct(), 
            		each.getProduct().getProductName(),
            		each.getProduct().getPrice(),
            		each.getOrderQuantity());
            orderDetailRepository.save(detail);
            BigDecimal total = each.getProduct().getPrice().multiply(new BigDecimal(each.getOrderQuantity()));
            orders.setTotalPrice(bigDecimalValue.add(total));
        });
        orders.setTotalPrice(bigDecimalValue);
        orderRepository.save(orders);
        return ResponseEntity.noContent().build();
    }
}
