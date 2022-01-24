package org.shop.db;

import org.shop.db.persists.Client;
import org.shop.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findById(Long id);

    List<Client> findAll();


    @Query("select c from Client c" +
            " where c.phoneNumber = :phone" +
            " or c.email =:email" +
            " or c.status =:status")
    Optional<Client> findClientByPhoneOrEmail(
            @Param("phone") Integer phone,
            @Param("email") String email,
            @Param("status") Status status
    );

    @Query(value = "select c from Client c where c.status in ?1", nativeQuery = true, countQuery = "select count(*) from Client c where c.status in ?1")
    Page<Client> findAll(List<Status> statusList, Pageable pageable);




}
