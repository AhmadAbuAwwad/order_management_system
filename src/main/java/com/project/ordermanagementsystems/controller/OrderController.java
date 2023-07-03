package com.project.ordermanagementsystems.controller;

import com.project.ordermanagementsystems.controller.dto.OrderDTO;
import com.project.ordermanagementsystems.controller.request.OrderCreateUpdateRequest;
import com.project.ordermanagementsystems.model.Order;
import com.project.ordermanagementsystems.model.User;
import com.project.ordermanagementsystems.service.OrderService;
import com.project.ordermanagementsystems.service.UserService;
import com.project.ordermanagementsystems.utils.Constants;
import com.project.ordermanagementsystems.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private Mapper mapper;

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderCreateUpdateRequest order, HttpServletRequest request) {
        User user = userService.getUserById((Long) request.getAttribute(Constants.ID));
        Order savedOrder = orderService.createOrder(order, user);
        return new ResponseEntity<>((OrderDTO) mapper.map(OrderDTO.class, savedOrder), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<>(mapper.mapAsList(OrderDTO.class, new ArrayList<>(orders)), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrder(id);
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>((OrderDTO) mapper.map(OrderDTO.class, order), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<OrderDTO>> getOrdersForUserById(HttpServletRequest request) {
        List<Order> orders = orderService.getOrdersForUserId((Long) request.getAttribute(Constants.ID));
        if (orders == null) {
            return new ResponseEntity<>( new ArrayList<>(), HttpStatus.OK);
        }
        return new ResponseEntity<>(mapper.mapAsList(OrderDTO.class, new ArrayList<>(orders)), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id,
                                                @RequestBody OrderCreateUpdateRequest updatedOrderDTO,
                                                HttpServletRequest request) {
        Order savedOrder = orderService.updateOrder(id, updatedOrderDTO, (Long) request.getAttribute(Constants.ID));
        if (savedOrder == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>((OrderDTO) mapper.map(OrderDTO.class, savedOrder), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrderById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
