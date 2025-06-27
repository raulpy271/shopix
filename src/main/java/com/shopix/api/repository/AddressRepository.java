package com.shopix.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shopix.api.entities.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
	@NativeQuery("SELECT a.* FROM addresses a WHERE a.user_id = :id")
	List<Address> getAddressByUserId(@Param("id") Long id);

}
