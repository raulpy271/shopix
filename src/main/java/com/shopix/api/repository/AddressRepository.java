package com.shopix.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopix.api.entities.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
