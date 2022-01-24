package org.shop.service;

import lombok.extern.slf4j.Slf4j;
import org.shop.db.persists.AddressBook;
import org.shop.db.persists.Client;
import org.shop.db.persists.Order;
import org.shop.db.AddressBookRepositories;
import org.shop.db.ClientRepository;
import org.shop.db.OrderRepository;
import org.shop.dto.AddressBookDTO;
import org.shop.dto.ClientDTO;
import org.shop.dto.OrderDTO;
import org.shop.enums.OrderStatus;
import org.shop.exceptions.BadRequestParameters;
import org.shop.exceptions.ClientNotExists;
import org.shop.exceptions.OrderNotExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderService implements IOrderService {


    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final AddressBookRepositories addressRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, ClientRepository clientRepository, AddressBookRepositories addressRepository) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.addressRepository = addressRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderDTO> getAllOrders(ClientDTO client) {
        log.info("Get All Orders for client - {}", client);
        if (client == null) {
            log.info("Client is null -> Get All Orders of All clients");
            return orderRepository.findALl().stream().map(OrderDTO::new).collect(Collectors.toList());
        } else {
            return getAllOrders(client.getId());
        }
    }

    @Override
    public List<OrderDTO> getAllOrdersByIdClient(Long id) {
        log.info("Get All Orders for client - {}", id);
        return getAllOrders(id);
    }


    private List<OrderDTO> getAllOrders(Long id) {
        log.info("Check existing client");
        Optional<Client> client = clientRepository.findById(id);
        if (client.isPresent()) {
            log.info("getAll orders for client");
            return orderRepository.findAllByClientId(id).stream().map(OrderDTO::new).collect(Collectors.toList());
        } else throw new ClientNotExists("Client with id - " + id + " not found");
    }

    @Override
    public OrderDTO getInfoOrderByIdOrder(Long id) {
        log.info("Get Info Order by ID - {}", id);
        return orderRepository.findById(id).map(OrderDTO::new).orElseThrow(OrderNotExists::new);
    }

    @Transactional
    @Override
    public OrderDTO changeOrderStatusOrder(OrderDTO orderDTO) {
        return changeOrderStatus(orderDTO.getId(), orderDTO.getOrderStatus());
    }

    @Transactional
    @Override
    public OrderDTO changeOrderStatusOrderId(Long id, OrderStatus status) {
        return changeOrderStatus(id, status);
    }

    private OrderDTO changeOrderStatus(Long id, OrderStatus status) {
        log.info("Change Order Status - {}", id);
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            Order orderFromBD = order.get();
            log.info("Change order status to {}", status);
            orderFromBD.setOrderStatus(status);
            orderFromBD = orderRepository.save(orderFromBD);
            log.info("Changes saved");
            return new OrderDTO(orderFromBD);
        }
        return null;
    }


    @Transactional
    @Override
    public OrderDTO changeAddressReceiptOfOrder(Long orderId, AddressBookDTO addressBook) {
        return changeRecipientAddressOrder(orderId, addressBook, false);
    }

    @Transactional
    @Override
    public OrderDTO changeAddressReceiptOfOrder(Long orderId, AddressBookDTO addressBook, boolean newAddress) {
        return changeRecipientAddressOrder(orderId, addressBook, newAddress);
    }


    private OrderDTO changeRecipientAddressOrder(Long orderId, AddressBookDTO addressBook, boolean newAddress) {
        log.info("Change AddressReceipt of OrderDTO - {}, New Address - {}", orderId, addressBook);
        log.info("Check exists order");
        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotExists::new);
        AddressBook address;
        AddressBook addressBookFromDTO = new AddressBook(addressBook);
        checkClientIdEquals(order, addressBookFromDTO);

        if ((addressBook.getId() != null) && newAddress) {
            log.info("Check exists address");
            address = addressRepository.findById(addressBook.getId()).orElse(null);

            if (address == null) {
                log.info("Address not found and parameter new Address is true. Create new AddressBook. Checking Client ID");
            } else {
                log.info("Address found. Parameter newAddress is true. Create new address. Changes id to null");
                addressBookFromDTO.setId(null);
            }

            log.info("Save new address");
            address = addressRepository.save(addressBookFromDTO);

        } else if ((addressBook.getId() != null) && !newAddress) {

            log.info("Check exists address");
            address = addressRepository.findById(addressBook.getId()).orElse(null);

            if (address == null) {
                throw new BadRequestParameters("Client ID not equals in bodies: order, addressBook");
            } else {
                log.info("Check and changes address data");
                if (!address.equals(addressBookFromDTO)) {
                    log.info("Update exists data address from {} to {}", address, addressBookFromDTO);
                    address.setAll(addressBook);
                    address = addressRepository.save(address);
                    log.info("Updated data address - {}", address);
                }
            }
        } else if ((addressBook.getId() == null) && newAddress) {
            log.info("AddressBook id is null and newAddress is true. Create new address");
            address = addressRepository.save(addressBookFromDTO);
        } else {
            throw new BadRequestParameters("Client ID not equals in bodies: order, addressBook");
        }

        log.info("Set Address Receipt to {}", address);
        order.setAddressBook(address);
        log.info("Update Order");
        order = orderRepository.save(order);
        return new OrderDTO(order);
    }


    private void checkClientIdEquals(Order order, AddressBook addressBook) {
        if (order.getClient().getId().equals(addressBook.getClient().getId())) {
            throw new BadRequestParameters("Client ID not equals in bodies: order, addressBook");
        }

    }

}
