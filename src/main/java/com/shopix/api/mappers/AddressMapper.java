package com.shopix.api.mappers;

import com.shopix.api.dtos.AddressCreateDTO;
import com.shopix.api.dtos.AddressResponseDTO;
import com.shopix.api.entities.Address;

public class AddressMapper {
	public static AddressResponseDTO toDTO(Address address)
	{
		return new AddressResponseDTO(address.getId(), address.getStreet(), address.getNumber(), address.getNeighborhood(), address.getCity(), address.getComplement(), address.getState(), address.getZipCode());
	}
	
	public static Address toEntity(AddressCreateDTO dto)
	{
		Address address = new Address();
		address.setStreet(dto.street());
		address.setNumber(dto.number());
		address.setNeighborhood(dto.neighborhood());
		address.setCity(dto.city());
		address.setComplement(dto.complement());
		address.setState(dto.state());
		address.setZipCode(dto.zipCode());
		return address;
	}

}
