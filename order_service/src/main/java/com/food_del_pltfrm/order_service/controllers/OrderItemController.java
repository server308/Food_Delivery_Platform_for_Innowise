package com.food_del_pltfrm.order_service.controllers;

import com.food_del_pltfrm.order_service.dtos.CreateOrderItemDTO;
import com.food_del_pltfrm.order_service.dtos.OrderDTO;
import com.food_del_pltfrm.order_service.dtos.UpdateOrderItemDTO;
import com.food_del_pltfrm.order_service.jwt.JwtTokenProvider;
import com.food_del_pltfrm.order_service.services.OrderService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class OrderItemController {
    private final OrderService orderService;
    private final JwtTokenProvider jwtTokenProvider;


    // ----------------- ADD ITEM TO ORDER -----------------
    @PostMapping("/user/{userId}/order/{orderId}")
    public ResponseEntity<OrderDTO> addItemToOrderForUser(
            @PathVariable Long orderId,
            @RequestBody CreateOrderItemDTO dto,
            @PathVariable Long userId, Authentication authentication
    ) {
        return ResponseEntity.ok(orderService.addItem(orderId, dto, userId, authentication));
    }

    @PostMapping("/order/{orderId}")
    public ResponseEntity<OrderDTO> addItemToOrder(
            @PathVariable Long orderId,
            @RequestBody CreateOrderItemDTO dto,
            Authentication authentication
    ) {
        Claims claims = jwtTokenProvider.getAllClaimsFromToken(authentication.getCredentials().toString(), false);
        Long userId = Long.parseLong(claims.get("user_id", String.class));
        return ResponseEntity.ok(orderService.addItem(orderId, dto, userId, authentication));
    }


    // ----------------- UPDATE ITEM -----------------
    @PutMapping("/user/{userId}/order/{orderId}/item/{itemId}")
    public ResponseEntity<OrderDTO> updateItemToOrderForUser(
            @PathVariable Long userId,
            @PathVariable Long orderId,
            @PathVariable Long itemId,
            @RequestBody UpdateOrderItemDTO dto, Authentication authentication
    ) {
        return ResponseEntity.ok(orderService.updateItem(orderId, itemId, dto, userId, authentication));
    }

    @PutMapping("/order/{orderId}/item/{itemId}")
    public ResponseEntity<OrderDTO> updateItemToOrder(
            @PathVariable Long orderId,
            @PathVariable Long itemId,
            @RequestBody UpdateOrderItemDTO dto,
            Authentication authentication
    ) {
        Claims claims = jwtTokenProvider.getAllClaimsFromToken(authentication.getCredentials().toString(), false);
        Long userId = Long.parseLong(claims.get("user_id", String.class));
        return ResponseEntity.ok(orderService.updateItem(orderId, itemId, dto, userId, authentication));
    }



    // ----------------- DELETE ITEM -----------------

    @DeleteMapping("/user/{userId}/order/{orderId}/item/{itemId}")
    public ResponseEntity<OrderDTO> deleteItemToOrderForUser(
            @PathVariable Long userId,
            @PathVariable Long orderId,
            @PathVariable Long itemId,
            Authentication authentication
    ) {
        return ResponseEntity.ok(orderService.deleteItem(orderId, itemId, userId, authentication));
    }
    @DeleteMapping("/order/{orderId}/item/{itemId}")
    public ResponseEntity<OrderDTO> deleteItemToOrder(
            @PathVariable Long orderId,
            @PathVariable Long itemId,
            Authentication authentication
    ) {
        Claims claims = jwtTokenProvider.getAllClaimsFromToken(authentication.getCredentials().toString(), false);
        Long userId = Long.parseLong(claims.get("user_id", String.class));
        return ResponseEntity.ok(orderService.deleteItem(orderId, itemId, userId, authentication));
    }
}
