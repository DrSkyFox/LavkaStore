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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@Slf4j
@Tag(name = "Client API")
@RestController
@RequestMapping(value = "/api/v1/", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {


    private final IOrderService orderService;

    @Autowired
    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }


    @Operation(summary = "Get All Order. Roles: Manager, Admin.")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/order/all")
    public ResponseEntity<OrdersPage> getAllOrders(
            @RequestParam(
                    name = "status", required = false,
                    defaultValue = "CREATED, PENDING_PAYMENT, PAID, CONFIRMATION, PACKED, SHIPPED, DELIVERED, CANCELED") List<OrderStatus> status,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrders(status, pageable));
    }


    @Operation(summary = "Get All Order. Roles: Manager, Admin")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping(value = "/order/all")
    public ResponseEntity<OrdersPage> getAllOrdersFilterByDates(
            @RequestParam(
                    name = "status", required = false,
                    defaultValue = "CREATED, PENDING_PAYMENT, PAID, CONFIRMATION, PACKED, SHIPPED, DELIVERED, CANCELED") List<OrderStatus> status,
            @RequestParam("startDate") @DateTimeFormat(pattern = "dd/MM/yyyy") Date  startDate,
            @RequestParam(value = "endDate", defaultValue = "#{T(java.time.LocalDateTime).now}") @DateTimeFormat(pattern = "dd/MM/yyyy") Date endDate,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrdersBetweenDates(status, startDate, endDate, pageable));
    }


    @Operation(summary = "Get All Order. All Roles.")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'MANAGER', 'ADMIN')")
    @GetMapping("/client/{id}/order/all")
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
    @PreAuthorize("hasAnyRole('CUSTOMER','MANAGER', 'ADMIN')")
    @GetMapping(value = "/client/{id}/order/all")
    public ResponseEntity<OrdersPage> getAllOrdersByClientFilterByDates(
            @PathVariable("id") Long id,
            @RequestParam(
                    name = "status", required = false,
                    defaultValue = "CREATED, PENDING_PAYMENT, PAID, CONFIRMATION, PACKED, SHIPPED, DELIVERED, CANCELED") List<OrderStatus> status,
            @RequestParam("startDate") @DateTimeFormat(pattern = "dd/MM/yyyy") Date  startDate,
            @RequestParam(value = "endDate", defaultValue = "#{T(java.time.LocalDateTime).now}") @DateTimeFormat(pattern = "dd/MM/yyyy") Date endDate,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        OrdersPage ordersPage = orderService.getAllOrdersByPagesAndClientIdAndBetweenDate(id, status, startDate, endDate, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(ordersPage);
    }


    @Operation(summary = "Get All Order. All Roles.")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'MANAGER', 'ADMIN')")
    @GetMapping("order/{id}")
    public ResponseEntity<OrderDTO> getInfoOrderById(@PathVariable("id") Long id) {
        OrderDTO orderDTO = orderService.getInfoOrderByIdOrder(id);
        return ResponseEntity.status(HttpStatus.OK).body(orderDTO);
    }


    @Operation(summary = "Get All Order. All Roles.")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PutMapping("/update/status")
    public ResponseEntity<OrderDTO> updateOrder(OrderDTO orderDTO) {
        orderDTO = orderService.changeOrder(orderDTO);
        return ResponseEntity.status(HttpStatus.OK).body(orderDTO);
    }







}
