package org.shop.service;

import lombok.extern.slf4j.Slf4j;
import org.shop.db.persists.AddressBook;
import org.shop.db.persists.Client;
import org.shop.db.persists.Order;
import org.shop.db.AddressBookRepositories;
import org.shop.db.ClientRepository;
import org.shop.db.OrderRepository;
import org.shop.dto.OrderDTO;
import org.shop.enums.OrderStatus;
import org.shop.events.ChangeOrderAddress;
import org.shop.events.ChangeOrderStatus;
import org.shop.events.ChangeOrderTotalCost;
import org.shop.exceptions.*;
import org.shop.pages.OrdersPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderService implements IOrderService {


    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final AddressBookRepositories addressRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public OrderService(OrderRepository orderRepository, ClientRepository clientRepository, AddressBookRepositories addressRepository, ApplicationEventPublisher eventPublisher) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.addressRepository = addressRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional(readOnly = true)
    @Override
    public OrdersPage getAllOrders(List<OrderStatus>  status , Pageable pageable) {

        log.info("Get All Orders. StatusList - {}", status);
        var orders = orderRepository.findAllByStatus(status, pageable).stream().map(OrderDTO::new).collect(Collectors.toList());

        return OrdersPage.builder()
                .orders(orders)
                .pageNumber(pageable.getPageNumber())
                .sizeOfPage(pageable.getPageSize())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public OrdersPage getAllOrdersBetweenDates(List<OrderStatus>  status, Date startDate, Date endDate, Pageable pageable) {

        log.info("Get All Orders. StatusList - {}", status);
        var orders = orderRepository.findAllByStatusBetweenDates(status, startDate, endDate , pageable).stream().map(OrderDTO::new).collect(Collectors.toList());

        return OrdersPage.builder()
                .orders(orders)
                .pageNumber(pageable.getPageNumber())
                .sizeOfPage(pageable.getPageSize())
                .build();
    }




    @Transactional(readOnly = true)
    @Override
    public OrdersPage getAllOrdersByPagesAndClientId(Long clientId, List<OrderStatus>  status, Pageable pageable) {

        log.info("Get All Orders for client - {}", clientId);
        var orders = orderRepository.findAllByStatusForClient(clientId, status, pageable).stream().map(OrderDTO::new).collect(Collectors.toList());

        return OrdersPage.builder()
                .orders(orders)
                .pageNumber(pageable.getPageNumber())
                .sizeOfPage(pageable.getPageSize())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public OrdersPage getAllOrdersByPagesAndClientIdAndBetweenDate(Long clientId, List<OrderStatus>  status, Date startDate, Date endDate, Pageable pageable) {

        log.info("Get All Orders for client - {} between dates: {} and {}. StatusOrderFilter - {}", clientId, startDate, endDate, status);
        var orders = orderRepository.findAllByStatusForClientBetweenDates(clientId,status,startDate,endDate,pageable).stream().map(OrderDTO::new).collect(Collectors.toList());

        return OrdersPage.builder()
                .orders(orders)
                .pageNumber(pageable.getPageNumber())
                .sizeOfPage(pageable.getPageSize())
                .build();

    }


    @Override
    public OrderDTO getInfoOrderByIdOrder(Long id) {
        log.info("Get Info Order by ID - {}", id);
        return orderRepository.findById(id).map(OrderDTO::new).orElseThrow(OrderNotExists::new);
    }

    @Transactional
    @Override
    public OrderDTO changeOrder(OrderDTO orderDTO) {
        log.info("change order information");
        if(orderDTO.getId() == null) {
            throw new BadRequestParameters("ID not found");
        }

        log.info("Get order from DB");
        Optional<Order> fromDB = orderRepository.findById(orderDTO.getId());
        if(fromDB.isEmpty()) {
            throw new OrderNotExists("Order not found");
        }

        if(orderDTO.getOrderCreated() != fromDB.get().getOrderCreated()) {
            throw new BadRequestParameters("Can't change date !");
        }

        Optional<Client> client = clientRepository.findById(fromDB.get().getId());
        Optional<AddressBook> addressBook = addressRepository.findById(orderDTO.getAddressID());
        if(addressBook.isEmpty()) {
            throw new AddressBookNotExists("Address not found");
        }

        if(client.isEmpty()) {
            throw new ClientNotExists("Client not exists");
        }

        checkChangeOrderStatus(orderDTO, fromDB.get(),  client.get());
        checkChangeAddressReception(orderDTO, fromDB.get(), addressBook.get());
        checkChangeTotalCost(orderDTO, fromDB.get(), client.get());

        fromDB.get().setAll(orderDTO);
        fromDB = Optional.of(orderRepository.save(fromDB.get()));

        return fromDB.map(OrderDTO::new).orElseThrow(OperationException::new);
    }


    private void checkChangeOrderStatus(OrderDTO orderDTO, Order order, Client client) {
        if(orderDTO.getOrderStatus() != order.getOrderStatus()) {
            eventPublisher.publishEvent(new ChangeOrderStatus(
                    orderDTO,
                    orderDTO.getId(),
                    orderDTO.getOrderUID(),
                    orderDTO.getOrderStatus(),
                    client.getId(),
                    client.getFirstName() + " " + client.getSecondName(),
                    client.getEmail(),
                    client.getPhoneNumber()
            ));
        }
    }



    private void checkChangeAddressReception(OrderDTO orderDTO, Order order, AddressBook address) {
        if(orderDTO.getAddressID().equals(order.getAddressBook().getId())) {
            eventPublisher.publishEvent(new ChangeOrderAddress(
                    orderDTO,
                    orderDTO.getId(),
                    address,
                    "Address of receipt changed"
            ));
        }
    }


    private void checkChangeTotalCost(OrderDTO orderDTO, Order order, Client client) {
        if(orderDTO.getTotalCost().equals(order.getTotalCost())) {
            eventPublisher.publishEvent(new ChangeOrderTotalCost(orderDTO,orderDTO.getId(),orderDTO.getTotalCost(),client));
        }
    }

}
