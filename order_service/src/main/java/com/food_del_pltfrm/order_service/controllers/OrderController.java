package com.food_del_pltfrm.order_service.controllers;


import com.food_del_pltfrm.order_service.dtos.*;
import com.food_del_pltfrm.order_service.jwt.JwtTokenProvider;
import com.food_del_pltfrm.order_service.services.OrderService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final JwtTokenProvider jwtTokenProvider;

    // ----------------- CREATE ORDER -----------------
    @PostMapping()
    public ResponseEntity<OrderDTO> createOrder(
            @RequestBody CreateOrderDTO dto,
            Authentication authentication
    ) {
        Claims claims = jwtTokenProvider.getAllClaimsFromToken(authentication.getCredentials().toString(), false);
        Long userId = Long.parseLong(claims.get("user_id", String.class));
        OrderDTO created = orderService.createOrder(dto, userId);
        return ResponseEntity
                .created(URI.create("/api/orders/" + created.getId()))
                .body(created);
    }

    @PostMapping("/user/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<OrderDTO> createOrderForUser(
            @RequestBody CreateOrderDTO dto, @PathVariable Long userId
    ) {
        OrderDTO created = orderService.createOrder(dto, userId);
        return ResponseEntity
                .created(URI.create("/api/orders/" + created.getId()))
                .body(created);
    }

    // ----------------- GET ORDER BY ID -----------------
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<OrderDTO>> getOrdersByUser(Authentication authentication, @PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrdersByUser(id));
    }

    // ----------------- GET ALL USER ORDERS -----------------
    @GetMapping("/me")
    public ResponseEntity<List<OrderDTO>> getMyOrders(Authentication authentication) {
        Claims claims = jwtTokenProvider.getAllClaimsFromToken(authentication.getCredentials().toString(), false);
        Long userId = Long.parseLong(claims.get("user_id", String.class));
        return ResponseEntity.ok(orderService.getOrdersByUser(userId));
    }

    // ----------------- UPDATE ORDER -----------------
    @PutMapping("/user/{userId}/order/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<OrderDTO> updateOrderForUser(
            @PathVariable Long id,
            @RequestBody UpdateOrderDTO dto,
            @PathVariable Long userId,
            Authentication authentication
    ) {
        return ResponseEntity.ok(orderService.updateOrder(id, dto, userId, authentication));
    }


    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(
            @PathVariable Long id,
            @RequestBody UpdateOrderDTO dto,
            Authentication authentication
    ) {
        Claims claims = jwtTokenProvider.getAllClaimsFromToken(authentication.getCredentials().toString(), false);
        Long userId = Long.parseLong(claims.get("user_id", String.class));
        return ResponseEntity.ok(orderService.updateOrder(id, dto, userId, authentication));
    }

    // ----------------- DELETE ORDER -----------------
    @DeleteMapping("/user/{userId}/order/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteOrderForUser(
            @PathVariable Long id,
            @PathVariable Long userId, Authentication authentication
    ) {
        orderService.deleteOrder(id, userId, authentication);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable Long id,
            Authentication authentication
    ) {
        Claims claims = jwtTokenProvider.getAllClaimsFromToken(authentication.getCredentials().toString(), false);
        Long userId = Long.parseLong(claims.get("user_id", String.class));
        orderService.deleteOrder(id, userId, authentication);
        return ResponseEntity.noContent().build();
    }




}
