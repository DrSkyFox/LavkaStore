package org.shop.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.shop.dto.OrderDTO;
import org.shop.enums.OrderStatus;
import org.shop.pages.OrdersPage;
import org.shop.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@Slf4j
@Tag(name = "Client API")
@RestController
@RequestMapping("/api/v1/")
public class OrderController {


    private final IOrderService orderService;

    @Autowired
    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }




    @Operation(summary = "Get All Order. All Roles.")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping("/order/all")
    public ResponseEntity<OrdersPage> getAllOrdersByClient(
            @RequestParam(
                    name = "status", required = false,
                    defaultValue = "CREATED, PENDING_PAYMENT, PAID, CONFIRMATION, PACKED, SHIPPED, DELIVERED, CANCELED") List<OrderStatus> status,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrders(status, pageable));
    }


    @Operation(summary = "Get All Order. All Roles.")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping("/order/all")
    public ResponseEntity<OrdersPage> getAllOrdersByClientFilterByDates(
            @RequestParam(
                    name = "status", required = false,
                    defaultValue = "CREATED, PENDING_PAYMENT, PAID, CONFIRMATION, PACKED, SHIPPED, DELIVERED, CANCELED") List<OrderStatus> status,
            @RequestParam("startDate") Date startDate,
            @RequestParam("endDate") Date endDate,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrders(status, pageable));
    }


    @Operation(summary = "Get All Order. All Roles.")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'MANAGER', 'ADMIN')")
    @GetMapping("/client/{id]/order/all")
    public ResponseEntity<OrdersPage> getAllOrdersByClientID(
            @PathVariable("id") Long id,
            @RequestParam(
                    name = "status", required = false,
                    defaultValue = "CREATED, PENDING_PAYMENT, PAID, CONFIRMATION, PACKED, SHIPPED, DELIVERED, CANCELED") List<OrderStatus> status,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable
            ) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrdersByPagesAndClientId(id, status,pageable));
    }




    @Operation(summary = "Get All Order. All Roles.")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'MANAGER', 'ADMIN')")
    @GetMapping("/get/{id}")
    public ResponseEntity<OrderDTO> getInfoOrderById(@PathVariable("id") Long id) {
        OrderDTO orderDTO = orderService.getInfoOrderByIdOrder(id);
        return ResponseEntity.status(HttpStatus.OK).body(orderDTO);
    }


    @Operation(summary = "Get All Order. All Roles.")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PutMapping("/update/status")
    public ResponseEntity<OrderDTO> changeOrderStatus(OrderDTO orderDTO) {
        orderDTO = orderService.changeOrderStatusOrder(orderDTO);
        return new ResponseEntity<>(orderDTO, HttpStatus.OK);
    }







}
