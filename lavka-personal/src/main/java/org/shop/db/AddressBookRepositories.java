package org.shop.db;

import org.shop.db.persists.AddressBook;
import org.shop.db.persists.Client;
import org.shop.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AddressBookRepositories extends JpaRepository<AddressBook, Long> {

    List<AddressBook> findAllByClient(Client client);

    Optional<AddressBook> findById(Long id);

    @Query("select a from AddressBook a")
    Page<AddressBook> findAll(Pageable pageable);

    @Query(
            value = "select a from address_book a where a.status in ?1",
            countQuery = "select count(*) from address_book a where a.status in ?1",
            nativeQuery = true
    )
    Page<AddressBook> findAllByStatusFilter(List<Status> statusList, Pageable pageable);


    @Query(value = "select a from address_book a where a.status in ?1",
            nativeQuery = true,
            countQuery = "select count(a) from address_book a where  a.status in ?2 and a.client_id = ?1 "
    )
    Page<AddressBook> findAllByClientAndStatusFilter(Long clientId, List<Status> status, Pageable pageable);

}
