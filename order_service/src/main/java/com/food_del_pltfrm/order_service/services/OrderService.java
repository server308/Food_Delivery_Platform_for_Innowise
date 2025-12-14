package com.food_del_pltfrm.order_service.services;

import com.food_del_pltfrm.order_service.dtos.*;
import com.food_del_pltfrm.order_service.entities.Order;
import com.food_del_pltfrm.order_service.entities.Order_item;
import com.food_del_pltfrm.order_service.entities.Payment;
import com.food_del_pltfrm.order_service.mappers.OrderItemMapper;
import com.food_del_pltfrm.order_service.mappers.OrderMapper;
import com.food_del_pltfrm.order_service.mappers.PaymentMapper;
import com.food_del_pltfrm.order_service.repositories.OrderItemRepository;
import com.food_del_pltfrm.order_service.repositories.OrderRepository;
import com.food_del_pltfrm.order_service.repositories.PaymentRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository itemRepository;
    private final PaymentRepository paymentRepository;

    private final OrderMapper orderMapper;
    private final OrderItemMapper itemMapper;
    private final PaymentMapper paymentMapper;


    // ------------------ CREATE ORDER ------------------
    @Transactional
    public OrderDTO createOrder(CreateOrderDTO dto, Long userId) {

        Integer total = dto.getItems().stream().map(i -> i.getPrice()).reduce(0, Integer::sum);
        Order order = orderMapper.toEntity(dto);
        order.setStatus("waiting");
        order.setUserId(userId);
        order.setTotalPrice(total);
        Order savedOrder = orderRepository.save(order);

        // Save items
        List<Order_item> items = dto.getItems().stream()
                .map(itemMapper::toEntity)
                .peek(i -> i.setOrder(savedOrder))
                .map(itemRepository::save)
                .toList();

        // Save payments
        List<Payment> payments = dto.getPayments().stream()
                .map(paymentMapper::toEntity)
                .peek(p -> p.setOrder(savedOrder))
                .map(paymentRepository::save)
                .toList();


        savedOrder.setOrder_items(items);
        savedOrder.setPayments(payments);


        OrderDTO orderDTO = orderMapper.toDTO(savedOrder);


        return orderDTO;
    }

    // ------------------ GET ORDER BY ID ------------------
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow();
        return orderMapper.toDTO(order);
    }

    // ------------------ GET ORDERS BY USER ------------------
    public List<OrderDTO> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    // ------------------ UPDATE ORDER ------------------
    @Transactional
    public OrderDTO updateOrder(Long id, UpdateOrderDTO dto, Long userId) {
        Order order = orderRepository.findById(id).orElseThrow();

        // Validate user owner
        if (!order.getUserId().equals(userId)) {
            throw new RuntimeException("Forbidden: cannot edit another user's order");
        }

        order.setStatus(dto.getStatus());
        order.setRestaurantId(dto.getRestaurantId());
        order.setTotalPrice(dto.getTotalPrice());

        Order savedOrder = orderRepository.save(order);
        OrderDTO orderDTO = orderMapper.toDTO(savedOrder);


        return orderDTO;    }

    // ------------------ DELETE ORDER ------------------
    @Transactional
    public void deleteOrder(Long id, Long userId) {
        Order order = orderRepository.findById(id).orElseThrow();

        if (!order.getUserId().equals(userId)) {
            throw new RuntimeException("Forbidden");
        }

        orderRepository.delete(order);


    }

    // ------------------ ADD ITEM ------------------
    @Transactional
    public OrderDTO addItem(Long orderId, CreateOrderItemDTO dto, Long userId) {
        Order order = orderRepository.findById(orderId).orElseThrow();

        if (!order.getUserId().equals(userId)) {
            throw new RuntimeException("Forbidden");
        }

        Order_item item = itemMapper.toEntity(dto);
        item.setOrder(order);
        itemRepository.save(item);

        OrderDTO orderDTO = orderMapper.toDTO(orderRepository.findById(orderId).orElseThrow());


        return orderDTO;
    }

    // ------------------ UPDATE ITEM ------------------
    @Transactional
    public OrderDTO updateItem(Long orderId, Long itemId, UpdateOrderItemDTO dto, Long userId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        Order_item item = itemRepository.findById(itemId).orElseThrow();

        if (!order.getUserId().equals(userId)) {
            throw new RuntimeException("Forbidden");
        }

        if (!item.getOrder().getId().equals(orderId)) {
            throw new RuntimeException("Item does not belong to this order");
        }

        item.setDishId(dto.getDishId());
        item.setQuantity(dto.getQuantity());
        item.setPrice(dto.getPrice());

        itemRepository.save(item);

        OrderDTO orderDTO = orderMapper.toDTO(orderRepository.findById(orderId).orElseThrow());


        return orderDTO;
    }

    // ------------------ DELETE ITEM ------------------
    @Transactional
    public OrderDTO deleteItem(Long orderId, Long itemId, Long userId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        Order_item item = itemRepository.findById(itemId).orElseThrow();

        if (!order.getUserId().equals(userId)) {
            throw new RuntimeException("Forbidden");
        }

        itemRepository.delete(item);
        OrderDTO orderDTO = orderMapper.toDTO(orderRepository.findById(orderId).orElseThrow());


        return orderDTO;
    }

    // ------------------ ADD PAYMENT ------------------
    @Transactional
    public OrderDTO addPayment(Long orderId, CreatePaymentDTO dto, Long userId) {
        Order order = orderRepository.findById(orderId).orElseThrow();

        if (!order.getUserId().equals(userId)) {
            throw new RuntimeException("Forbidden");
        }

        Payment payment = paymentMapper.toEntity(dto);
        payment.setOrder(order);
        paymentRepository.save(payment);

        OrderDTO orderDTO = orderMapper.toDTO(order);


        return orderDTO;
    }

    // ------------------ UPDATE PAYMENT ------------------
    @Transactional
    public OrderDTO updatePayment(Long orderId, Long paymentId, UpdatePaymentDTO dto, Long userId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        Payment payment = paymentRepository.findById(paymentId).orElseThrow();

        if (!order.getUserId().equals(userId)) {
            throw new RuntimeException("Forbidden");
        }

        if (!payment.getOrder().getId().equals(orderId)) {
            throw new RuntimeException("Payment does not belong to this order");
        }

        payment.setMethod(dto.getMethod());
        payment.setAmount(dto.getAmount());
        payment.setStatus(dto.getStatus());

        paymentRepository.save(payment);

        OrderDTO orderDTO = orderMapper.toDTO(order);


        return orderDTO;
    }

    // ------------------ DELETE PAYMENT ------------------
    @Transactional
    public OrderDTO deletePayment(Long orderId, Long paymentId, Long userId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        Payment payment = paymentRepository.findById(paymentId).orElseThrow();

        if (!order.getUserId().equals(userId)) {
            throw new RuntimeException("Forbidden");
        }

        paymentRepository.delete(payment);
        OrderDTO orderDTO = orderMapper.toDTO(orderRepository.findById(orderId).orElseThrow());


        return orderDTO;
    }
}
