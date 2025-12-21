package com.food_del_pltfrm.order_service.services;

import com.food_del_pltfrm.order_service.dtos.*;
import com.food_del_pltfrm.order_service.entities.Order;
import com.food_del_pltfrm.order_service.entities.Order_item;
import com.food_del_pltfrm.order_service.entities.Payment;
import com.food_del_pltfrm.order_service.mappers.OrderItemMapper;
import com.food_del_pltfrm.order_service.mappers.OrderMapper;
import com.food_del_pltfrm.order_service.mappers.PaymentMapper;
import com.food_del_pltfrm.order_service.rabbit.EventListener;
import com.food_del_pltfrm.order_service.repositories.OrderItemRepository;
import com.food_del_pltfrm.order_service.repositories.OrderRepository;
import com.food_del_pltfrm.order_service.repositories.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        Integer total = dto.getItems().stream().map(i -> i.getPrice()*i.getQuantity()).reduce(0, Integer::sum);

        Order order = orderMapper.toEntity(dto);
        order.setStatus("waiting");
        order.setUserId(userId);
        order.setTotalPrice((int)(total+(total*0.2)));

                Order savedOrder = orderRepository.save(order);

        // Save items
        List<Order_item> items = dto.getItems().stream()
                .map(itemMapper::toEntity)
                .peek(i -> i.setOrder(savedOrder))
                .map(itemRepository::save)
                .collect(Collectors.toList());


        // Save payments
        List<Payment> payments = dto.getPayments().stream()
                .map(paymentMapper::toEntity)
                .peek(p -> {
                    p.setOrder(savedOrder);
                    p.setAmount(order.getTotalPrice());
                })
                .map(paymentRepository::save)
                .collect(Collectors.toList());


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
        List<Order> orders = orderRepository.findAllByUserId(userId).orElseThrow(() -> new RuntimeException("Not found!"));
        return orders.stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    // ------------------ UPDATE ORDER ------------------
    @Transactional
    public OrderDTO updateOrder(Long id, UpdateOrderDTO dto, Long userId, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().contains("ROLE_ADMIN");
        Integer total = dto.getItems().stream().map(i -> i.getPrice()*i.getQuantity()).reduce(0, Integer::sum);


        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found!"));
        if (order.getStatus().equals("accepted") && !isAdmin){
            throw new RuntimeException("Cannot update order!");
        }
        if (!order.getUserId().equals(userId) && !isAdmin) {
            throw new RuntimeException("Forbidden");
        }
        if (!dto.getRestaurantId().equals(order.getRestaurantId())){
            throw new RuntimeException("Cannot change restaurant after order creation");
        }
        if (!order.getOrder_items().isEmpty()) {
            order.getOrder_items().clear();
        }
        if (!order.getPayments().isEmpty()){
            order.getPayments().clear();
        }
        order.setTotalPrice((int)(total+(total*0.2)));

        dto.getItems().stream()
                .map(itemMapper::toEntity)
                .forEach(item -> {
                    item.setOrder(order);
                    Order_item savedItem = itemRepository.save(item);
                    order.getOrder_items().add(savedItem);
                });

        dto.getPayments().stream()
                .map(paymentMapper::toEntity)
                .forEach(payment -> {
                    payment.setOrder(order);
                    payment.setAmount((int)(total+(total*0.2)));
                    Payment savedPayment = paymentRepository.save(payment);
                    order.getPayments().add(savedPayment);
                });


        Order savedOrder = orderRepository.save(order);
        OrderDTO orderDTO = orderMapper.toDTO(savedOrder);


        return orderDTO;
    }

    // ------------------ DELETE ORDER ------------------
    @Transactional
    public void deleteOrder(Long id, Long userId, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().contains("ROLE_ADMIN");
        Order order = orderRepository.findById(id).orElseThrow(()-> new RuntimeException("Order not found!"));

        if (order.getStatus().equals("accepted") && !isAdmin){
            throw new RuntimeException("Cannot delete order!");
        }
        if (!order.getUserId().equals(userId) && !isAdmin) {
            throw new RuntimeException("Forbidden");
        }
        orderRepository.delete(order);
    }

    // ------------------ ADD ITEM ------------------
    @Transactional
    public OrderDTO addItem(Long orderId, CreateOrderItemDTO dto, Long userId, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().contains("ROLE_ADMIN");
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new RuntimeException("Order not found!"));

        if (!order.getUserId().equals(userId) && !isAdmin) {
            throw new RuntimeException("Forbidden");
        }
        if (order.getStatus().equals("accepted") && !isAdmin){
            throw new RuntimeException("Cannot add Item!");
        }
        Order_item item = itemMapper.toEntity(dto);
        item.setOrder(order);
        itemRepository.save(item);
        order.getOrder_items().add(item);

        Integer total = order.getOrder_items().stream().map(i -> i.getPrice()*i.getQuantity()).reduce(0, Integer::sum);
        order.setTotalPrice((int)(total+(total*0.2)));
        Order saved_order = orderRepository.save(order);
        Payment existingPayment = order.getPayments().get(0);
        existingPayment.setAmount((int)(total+(total*0.2)));
        paymentRepository.save(existingPayment);

        OrderDTO orderDTO = orderMapper.toDTO(saved_order);

        return orderDTO;
    }


    // ------------------ UPDATE ITEM ------------------
    @Transactional
    public OrderDTO updateItem(Long orderId, Long itemId, UpdateOrderItemDTO dto, Long userId, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().contains("ROLE_ADMIN");
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new RuntimeException("Order not found!"));

        if (!order.getUserId().equals(userId) && !isAdmin) {
            throw new RuntimeException("Forbidden");
        }
        if (order.getStatus().equals("accepted") && !isAdmin){
            throw new RuntimeException("Cannot update Item!");
        }
        Order_item item = itemRepository.findByOrderIdAndId(orderId, itemId).orElseThrow(()-> new RuntimeException("OrderItem not found!"));
        item.setDishId(dto.getDishId());
        item.setQuantity(dto.getQuantity());
        item.setPrice(dto.getPrice());

        Order_item saved_item = itemRepository.save(item);
        order.getOrder_items().add(saved_item);


        Integer total = order.getOrder_items().stream().map(i -> i.getPrice()*i.getQuantity()).reduce(0, Integer::sum);
        order.setTotalPrice((int)(total+(total*0.2)));
        Order saved_order = orderRepository.save(order);
        Payment existingPayment = order.getPayments().get(0);
        existingPayment.setAmount((int)(total+(total*0.2)));
        paymentRepository.save(existingPayment);

        OrderDTO orderDTO = orderMapper.toDTO(saved_order);

        return orderDTO;
    }

    // ------------------ DELETE ITEM ------------------
    @Transactional
    public OrderDTO deleteItem(Long orderId, Long itemId, Long userId, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().contains("ROLE_ADMIN");
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new RuntimeException("Order not found!"));

        if (!order.getUserId().equals(userId) && !isAdmin) {
            throw new RuntimeException("Forbidden");
        }

        if (order.getStatus().equals("accepted") && !isAdmin){
            throw new RuntimeException("Cannot delete Item!");
        }

        Optional<Order_item> itemToRemove = order.getOrder_items().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst();

        if (itemToRemove.isEmpty()) {
            throw new RuntimeException("Item not found in order!");
        }
        if (order.getOrder_items().stream().count() != 1){
            itemRepository.delete(itemToRemove.get());
            order.getOrder_items().remove(itemToRemove.get());
        } else
        {
            throw new RuntimeException("Cannot delete item!");
        }
        Integer total = order.getOrder_items().stream().map(i -> i.getPrice()*i.getQuantity()).reduce(0, Integer::sum);
        order.setTotalPrice((int)(total+(total*0.2)));
        Order saved_order = orderRepository.save(order);
        Payment existingPayment = order.getPayments().get(0);
        existingPayment.setAmount((int)(total+(total*0.2)));
        paymentRepository.save(existingPayment);

        OrderDTO orderDTO = orderMapper.toDTO(saved_order);
        return orderDTO;
    }

    // ------------------ ADD PAYMENT ------------------
    @Transactional
    public OrderDTO addPayment(Long orderId, CreatePaymentDTO dto, Long userId, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().contains("ROLE_ADMIN");

        Order order = orderRepository.findById(orderId).orElseThrow(()-> new RuntimeException("Order not found!"));

        if (!order.getUserId().equals(userId) && !isAdmin) {
            throw new RuntimeException("Forbidden");
        }

        if (order.getStatus().equals("accepted") && !isAdmin){
            throw new RuntimeException("Cannot add Payment");
        }

        Payment payment = paymentMapper.toEntity(dto);
        payment.setOrder(order);
        order.getPayments().add(payment);
        Integer total = order.getOrder_items().stream().map(i -> i.getPrice()*i.getQuantity()).reduce(0, Integer::sum);
        paymentRepository.save(payment);
        OrderDTO orderDTO = orderMapper.toDTO(order);

        return orderDTO;
    }

    // ------------------ UPDATE PAYMENT ------------------
    @Transactional
    public OrderDTO updatePayment(Long orderId, Long paymentId, UpdatePaymentDTO dto, Long userId, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().contains("ROLE_ADMIN");

        Order order = orderRepository.findById(orderId).orElseThrow(()-> new RuntimeException("Order not found!"));
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new RuntimeException("Payment not found!"));
        if (!order.getUserId().equals(userId) && !isAdmin) {
            throw new RuntimeException("Forbidden");
        }

        if (order.getStatus().equals("accepted") && !isAdmin){
            throw new RuntimeException("Cannot add Payment");
        }

        if (!payment.getOrder().getId().equals(orderId)) {
            throw new RuntimeException("Payment does not belong to this order");
        }


        payment.setMethod(dto.getMethod());
        payment.setAmount(dto.getAmount());
        payment.setStatus(dto.getStatus());
        payment.setOrder(order);
        order.getPayments().add(payment);
        Integer total = order.getOrder_items().stream().map(i -> i.getPrice()*i.getQuantity()).reduce(0, Integer::sum);

        paymentRepository.save(payment);

        OrderDTO orderDTO = orderMapper.toDTO(order);

        return orderDTO;
    }

    // ------------------ DELETE PAYMENT ------------------
    @Transactional
    public OrderDTO deletePayment(Long orderId, Long paymentId, Long userId, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().contains("ROLE_ADMIN");

        Order order = orderRepository.findById(orderId).orElseThrow(()-> new RuntimeException("Order not found!"));
        Payment payment = order.getPayments().stream().filter(payment1 -> payment1.getId().equals(paymentId)).findAny().orElseThrow(() -> new RuntimeException("Payment not found!"));

        if (!order.getUserId().equals(userId) && !isAdmin) {
            throw new RuntimeException("Forbidden");
        }

        if (order.getStatus().equals("accepted") && !isAdmin){
            throw new RuntimeException("Cannot delete Payment");
        }
        if (!payment.getOrder().getId().equals(orderId)) {
            throw new RuntimeException("Payment does not belong to this order");
        }
        if (order.getPayments().size() == 1){
            throw new RuntimeException("Payments can not be null in order");
        }
        order.getPayments().remove(payment);
        OrderDTO orderDTO = orderMapper.toDTO(orderRepository.save(order));

        return orderDTO;
    }


    @Transactional
    public void deleteOrdersByUserId(Long userId){
        orderRepository.deleteAllByUserId(userId);
    }
}
