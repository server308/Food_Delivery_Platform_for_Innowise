package com.food_del_pltfrm.order_service.controllers;


import com.food_del_pltfrm.order_service.dtos.*;
import com.food_del_pltfrm.order_service.services.OrderService;
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

    // ----------------- CREATE ORDER -----------------
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(
            @RequestBody CreateOrderDTO dto,
            Authentication authentication
    ) {
        Long userId = Long.parseLong(authentication.getName());

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

    @GetMapping("/by_user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<OrderDTO>> getOrdersByUser(Authentication authentication, @PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrdersByUser(id));
    }

    // ----------------- GET ALL USER ORDERS -----------------
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getUserOrders(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        return ResponseEntity.ok(orderService.getOrdersByUser(userId));
    }

    // ----------------- UPDATE ORDER -----------------
    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(
            @PathVariable Long id,
            @RequestBody UpdateOrderDTO dto,
            Authentication authentication
    ) {
        Long userId = Long.parseLong(authentication.getName());
        return ResponseEntity.ok(orderService.updateOrder(id, dto, userId));
    }

    // ----------------- DELETE ORDER -----------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable Long id,
            Authentication authentication
    ) {
        Long userId = Long.parseLong(authentication.getName());
        orderService.deleteOrder(id, userId);
        return ResponseEntity.noContent().build();
    }

    // ----------------- ADD ITEM TO ORDER -----------------
    @PostMapping("/{orderId}/items")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<OrderDTO> addItem(
            @PathVariable Long orderId,
            @RequestBody CreateOrderItemDTO dto,
            Authentication authentication
    ) {
        Long userId = Long.parseLong(authentication.getName());
        return ResponseEntity.ok(orderService.addItem(orderId, dto, userId));
    }

    // ----------------- UPDATE ITEM -----------------
    @PutMapping("/{orderId}/items/{itemId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<OrderDTO> updateItem(
            @PathVariable Long orderId,
            @PathVariable Long itemId,
            @RequestBody UpdateOrderItemDTO dto,
            Authentication authentication
    ) {
        Long userId = Long.parseLong(authentication.getName());
        return ResponseEntity.ok(orderService.updateItem(orderId, itemId, dto, userId));
    }

    // ----------------- DELETE ITEM -----------------
    @DeleteMapping("/{orderId}/items/{itemId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<OrderDTO> deleteItem(
            @PathVariable Long orderId,
            @PathVariable Long itemId,
            Authentication authentication
    ) {
        Long userId = Long.parseLong(authentication.getName());
        return ResponseEntity.ok(orderService.deleteItem(orderId, itemId, userId));
    }

    // ----------------- ADD PAYMENT -----------------
    @PostMapping("/{orderId}/payments")
    public ResponseEntity<OrderDTO> addPayment(
            @PathVariable Long orderId,
            @RequestBody CreatePaymentDTO dto,
            Authentication authentication
    ) {
        Long userId = Long.parseLong(authentication.getName());
        return ResponseEntity.ok(orderService.addPayment(orderId, dto, userId));
    }

    // ----------------- UPDATE PAYMENT -----------------
    @PutMapping("/{orderId}/payments/{paymentId}")
    public ResponseEntity<OrderDTO> updatePayment(
            @PathVariable Long orderId,
            @PathVariable Long paymentId,
            @RequestBody UpdatePaymentDTO dto,
            Authentication authentication
    ) {
        Long userId = Long.parseLong(authentication.getName());
        return ResponseEntity.ok(orderService.updatePayment(orderId, paymentId, dto, userId));
    }

    // ----------------- DELETE PAYMENT -----------------
    @DeleteMapping("/{orderId}/payments/{paymentId}")
    public ResponseEntity<OrderDTO> deletePayment(
            @PathVariable Long orderId,
            @PathVariable Long paymentId,
            Authentication authentication
    ) {
        Long userId = Long.parseLong(authentication.getName());
        return ResponseEntity.ok(orderService.deletePayment(orderId, paymentId, userId));
    }
}
