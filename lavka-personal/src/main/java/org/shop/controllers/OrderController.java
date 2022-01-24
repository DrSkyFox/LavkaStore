package org.shop.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.shop.dto.ClientDTO;
import org.shop.dto.OrderDTO;
import org.shop.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "Client API")
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final IOrderService orderService;

    @Autowired
    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Get All Order. All Roles.")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'MANAGER', 'ADMIN')")
    @PostMapping("/all")
    public ResponseEntity<List<OrderDTO>> getAllOrdersByClient(@RequestBody ClientDTO client) {
        List<OrderDTO> orders = orderService.getAllOrders(client);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @Operation(summary = "Get All Order. All Roles.")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'MANAGER', 'ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<OrderDTO>> getAllOrdersByClientID(@RequestParam("id") Long id) {
        List<OrderDTO> orders = orderService.getAllOrdersByIdClient(id);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }


    @Operation(summary = "Get All Order. All Roles.")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'MANAGER', 'ADMIN')")
    @GetMapping("/get/{id}")
    public ResponseEntity<OrderDTO> getInfoOrderById(@PathVariable("id") Long id) {
        OrderDTO orderDTO = orderService.getInfoOrderByIdOrder(id);
        return new ResponseEntity<>(orderDTO, HttpStatus.OK);
    }


    @Operation(summary = "Get All Order. All Roles.")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PutMapping("/update/status")
    public ResponseEntity<OrderDTO> changeOrderStatus(OrderDTO orderDTO) {
        orderDTO = orderService.changeOrderStatusOrder(orderDTO);
        return new ResponseEntity<>(orderDTO, HttpStatus.OK);
    }







}
