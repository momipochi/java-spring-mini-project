package com.example.mp5spring.repository;

import java.util.List;

import com.example.mp5spring.model.Address;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AddressRepository extends CrudRepository<Address, Long> {
    public List<Address> findByStreetName(String name);

    @Query("from Address as a where a.id < :maxId")
    public List<Address> findAddressesIdLowerThan(@Param("maxId") Long maxId);
}
