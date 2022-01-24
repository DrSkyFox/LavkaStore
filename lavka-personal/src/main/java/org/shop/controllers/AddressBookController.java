package org.shop.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.shop.dto.AddressBookDTO;
import org.shop.enums.Status;
import org.shop.pages.AddressBookPage;
import org.shop.service.IAddressBookService;
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
public class AddressBookController {

    private final IAddressBookService addressBookService;

    @Autowired
    public AddressBookController(IAddressBookService addressBookService) {
        this.addressBookService = addressBookService;
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @Operation(summary = "Get All Address by filter Status. Pageable")
    @GetMapping(value = "/address/all", params = {"status"})
    public ResponseEntity<AddressBookPage> getAllAddressByFilterStatus(
            @RequestParam(name = "statusList", required = false, defaultValue = "ACTIVE, DELETED") List<Status> statusList,
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
            ) {
        AddressBookPage addressBookPage = addressBookService.getAllAddressPage(statusList, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(addressBookPage);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @Operation(summary = "Get All Address by filter Status and Client Id. Pageable")
    @GetMapping(value = "/client/{id}/address/all", params = {"status"})
    public ResponseEntity<AddressBookPage> getAllAddressOfClientByFilterStatus(
            @RequestParam(name = "statusList", required = false, defaultValue = "ACTIVE, DELETED") List<Status> statusList,
            @PathVariable("id") Long clientId,
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        AddressBookPage addressBookPage = addressBookService.getAllAddressPageByClient(clientId,statusList, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(addressBookPage);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @Operation(summary = "Create new Address for User")
    @PostMapping("/client/{id}/address/create")
    public ResponseEntity<AddressBookDTO> createNewAddress(@RequestBody AddressBookDTO addressBookDTO, @PathVariable("id") Long idClient) {
        AddressBookDTO addressBook = addressBookService.createNewAddress(addressBookDTO, idClient);
        return ResponseEntity.status(HttpStatus.OK).body(addressBook);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @Operation(summary = "Update current address for User")
    @PutMapping("/client/{id}/address/update")
    public ResponseEntity<AddressBookDTO> updateCurrentAddress(
            @PathVariable("id") Long idClient,
            @RequestBody AddressBookDTO addressBookDTO
    ) {
        AddressBookDTO addressBook = addressBookService.updateAddress(addressBookDTO, idClient);
        return ResponseEntity.status(HttpStatus.OK).body(addressBook);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @Operation(summary = "Get Data Address by Id")
    @GetMapping("/address/{id}")
    public ResponseEntity<AddressBookDTO> getAddressBookById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(addressBookService.getAddressBookInfoById(id));
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @Operation(summary = "Delete Address By Id. Manager access")
    @DeleteMapping("/address/{id}/delete")
    public ResponseEntity<Object> deleteById(@PathVariable("id") Long id) {
        addressBookService.deleteAddressBookById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new Date());
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @Operation(summary = "Delete Address By Id. User access")
    @DeleteMapping("/client/{id}/address/{aId}/delete")
    public ResponseEntity<Object> deleteById(
            @PathVariable("id") Long id,
            @PathVariable("aId") Long addressId
    ) {
        addressBookService.deleteAddressBookByIdForClient(id, addressId);
        return ResponseEntity.status(HttpStatus.OK).body(new Date());
    }







}
