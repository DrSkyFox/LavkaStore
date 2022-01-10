package org.shop.repositories;

import org.shop.entites.AddressBook;
import org.shop.entites.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AddressBookRepositories extends JpaRepository<AddressBook, Long> {

    List<AddressBook> findAllByClient(Client client);

    Optional<AddressBook> findById(Long id);



}
