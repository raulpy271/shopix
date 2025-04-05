package com.shopix.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopix.api.entities.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
