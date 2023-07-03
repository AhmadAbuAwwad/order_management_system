package com.project.ordermanagementsystems.service;

import com.project.ordermanagementsystems.controller.request.OrderCreateUpdateRequest;
import com.project.ordermanagementsystems.exception.ResourceNotFoundException;
import com.project.ordermanagementsystems.exception.ResponseCodes;
import com.project.ordermanagementsystems.exception.UnAuthenticatedException;
import com.project.ordermanagementsystems.exception.UnAuthorizedException;
import com.project.ordermanagementsystems.model.Order;
import com.project.ordermanagementsystems.model.ProductOrder;
import com.project.ordermanagementsystems.model.User;
import com.project.ordermanagementsystems.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductOrderService productOrderService;

    public Order createOrder(OrderCreateUpdateRequest orderCreateUpdateRequest, User user) {

        if (user == null) {
            throw new UnAuthenticatedException("This user is not authenticated",
                    ResponseCodes.UNAUTHENTICATED);
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderedAt(new Date());
        Order savedOrder = orderRepository.save(order);
        List<ProductOrder> productOrders = productOrderService.createProductOrders(order, orderCreateUpdateRequest.getProductsIds(),
                orderCreateUpdateRequest.getQuantities());

        savedOrder.setProductOrders(productOrders);

        return savedOrder;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrder(Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (!orderOptional.isPresent()) {
            throw new ResourceNotFoundException("No order with id: " + id);
        }
        return orderOptional.get();
    }

    public Order updateOrder(Long id, OrderCreateUpdateRequest updatedOrder, Long userId) {
        Order existingOrder = getOrder(id);

        if (existingOrder.getUser().getId() != userId) {
            throw new UnAuthorizedException("This user is not authorized to do this operation for order id"
                    + existingOrder.getId(), ResponseCodes.UNAUTHORIZED);
        }

        if (existingOrder == null) {
            throw new ResourceNotFoundException("No order with id: " + id);
        }

        existingOrder.setOrderedAt(new Date());

        orderRepository.save(existingOrder);

        List<ProductOrder> productOrders = productOrderService.updateProductOrders(existingOrder,
                updatedOrder.getProductsIds(), updatedOrder.getQuantities());
        existingOrder.setProductOrders(productOrders);

        return existingOrder;
    }

    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }

    public List<Order> getOrdersForUserId(Long userId) {
        return orderRepository.findOrderByUserId(userId);
    }

    public Order updateOrderByAdmin(Long id, OrderCreateUpdateRequest updatedOrder) {
        Order existingOrder = getOrder(id);

        if (existingOrder == null) {
            throw new ResourceNotFoundException("No order with id: " + id);
        }

        existingOrder.setOrderedAt(new Date());

        orderRepository.save(existingOrder);

        List<ProductOrder> productOrders = productOrderService.updateProductOrders(existingOrder,
                updatedOrder.getProductsIds(), updatedOrder.getQuantities());
        existingOrder.setProductOrders(productOrders);

        return existingOrder;
    }
}
