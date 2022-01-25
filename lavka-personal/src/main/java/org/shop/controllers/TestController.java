package org.shop.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.shop.dto.TestDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "Test API. Testing Access")
@RestController
@RequestMapping("/api/v1/test")
public class TestController {



    @Operation(summary = "Get Test")
    @GetMapping(value = "/get/all/{text}")
    public TestDTO getTestAll(@PathVariable String text) {
        return new TestDTO(text);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @Operation(summary = "Get Test")
    @GetMapping(value = "/get/custom/{text}")
    public TestDTO getTestCustomer(@PathVariable String text) {
        return new TestDTO(text);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @Operation(summary = "Get Test")
    @GetMapping(value = "/get/admin/{text}")
    public TestDTO getTestManagerOrAdmin(@PathVariable String text) {
        return new TestDTO(text);
    }





}
