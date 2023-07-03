package com.project.ordermanagementsystems.controller;

import com.project.ordermanagementsystems.controller.dto.OrderDTO;
import com.project.ordermanagementsystems.controller.request.AuthRequest;
import com.project.ordermanagementsystems.controller.request.OrderCreateUpdateRequest;
import com.project.ordermanagementsystems.controller.request.RegisterRequest;
import com.project.ordermanagementsystems.model.Order;
import com.project.ordermanagementsystems.service.OrderService;
import com.project.ordermanagementsystems.service.UserService;
import com.project.ordermanagementsystems.utils.Constants;
import com.project.ordermanagementsystems.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private Mapper mapper;

    @PutMapping("/order/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id,
                                                @RequestBody OrderCreateUpdateRequest updatedOrderDTO) {
        Order savedOrder = orderService.updateOrderByAdmin(id, updatedOrderDTO);
        if (savedOrder == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>((OrderDTO) mapper.map(OrderDTO.class, savedOrder), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> registerAdmin(@Valid @RequestBody RegisterRequest registerRequest) {
        userService.addAdmin(registerRequest);
        return ResponseEntity.ok("You have added an admin successfully");
    }


    @GetMapping("/order/user/{id}")
    public ResponseEntity<List<OrderDTO>> getOrdersForUserById(@PathVariable Long id) {
        List<Order> orders = orderService.getOrdersForUserId(id);
        if (orders == null) {
            return new ResponseEntity<>( new ArrayList<>(), HttpStatus.OK);
        }
        return new ResponseEntity<>(mapper.mapAsList(OrderDTO.class, new ArrayList<>(orders)), HttpStatus.OK);
    }
}