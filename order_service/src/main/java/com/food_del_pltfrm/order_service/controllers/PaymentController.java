package com.food_del_pltfrm.order_service.controllers;

import com.food_del_pltfrm.order_service.dtos.CreatePaymentDTO;
import com.food_del_pltfrm.order_service.dtos.OrderDTO;
import com.food_del_pltfrm.order_service.dtos.UpdatePaymentDTO;
import com.food_del_pltfrm.order_service.jwt.JwtTokenProvider;
import com.food_del_pltfrm.order_service.services.OrderService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class PaymentController {
    private final OrderService orderService;
    private final JwtTokenProvider jwtTokenProvider;


    // ----------------- ADD PAYMENT -----------------
    @PostMapping("/user/{userId}/order/{orderId}")
    public ResponseEntity<OrderDTO> addPaymentForUser(
            @PathVariable Long orderId,
            @RequestBody CreatePaymentDTO dto,
            @PathVariable Long userId, Authentication authentication
    ) {
        return ResponseEntity.ok(orderService.addPayment(orderId, dto, userId, authentication));
    }

    @PostMapping("/{orderId}")
    public ResponseEntity<OrderDTO> addPayment(
            @PathVariable Long orderId,
            @RequestBody CreatePaymentDTO dto,
            Authentication authentication
    ) {
        Claims claims = jwtTokenProvider.getAllClaimsFromToken(authentication.getCredentials().toString(), false);
        Long userId = Long.parseLong(claims.get("user_id", String.class));
        return ResponseEntity.ok(orderService.addPayment(orderId, dto, userId, authentication));
    }

    // ----------------- UPDATE PAYMENT -----------------
    @PutMapping("/user/{userId}/order/{orderId}/payment/{paymentId}")
    public ResponseEntity<OrderDTO> updatePaymentForUser(
            @PathVariable Long orderId,
            @PathVariable Long paymentId,
            @RequestBody UpdatePaymentDTO dto,
            @PathVariable Long userId, Authentication authentication
    ) {
        return ResponseEntity.ok(orderService.updatePayment(orderId, paymentId, dto, userId, authentication));
    }
    @PutMapping("/order/{orderId}/payment/{paymentId}")
    public ResponseEntity<OrderDTO> updatePayment(
            @PathVariable Long orderId,
            @PathVariable Long paymentId,
            @RequestBody UpdatePaymentDTO dto,
            Authentication authentication
    ) {
        Long userId = Long.parseLong(authentication.getName());
        return ResponseEntity.ok(orderService.updatePayment(orderId, paymentId, dto, userId, authentication));
    }

    // ----------------- DELETE PAYMENT -----------------
    @DeleteMapping("/user/{userId}/order/{orderId}/payment/{paymentId}")
    public ResponseEntity<OrderDTO> deletePaymentForUser(
            @PathVariable Long orderId,
            @PathVariable Long paymentId,
            Authentication authentication,
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(orderService.deletePayment(orderId, paymentId, userId, authentication));
    }
    @DeleteMapping("/order/{orderId}/payment/{paymentId}")
    public ResponseEntity<OrderDTO> deletePayment(
            @PathVariable Long orderId,
            @PathVariable Long paymentId,
            Authentication authentication
    ) {
        Long userId = Long.parseLong(authentication.getName());
        return ResponseEntity.ok(orderService.deletePayment(orderId, paymentId, userId, authentication));
    }
}
