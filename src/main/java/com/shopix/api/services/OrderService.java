package com.shopix.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopix.api.dtos.OrderCreateDTO;
import com.shopix.api.dtos.OrderResponseDTO;
import com.shopix.api.entities.Order;
import com.shopix.api.mappers.OrderMapper;
import com.shopix.api.repository.OrderRepository;

@Service
public class OrderService {
	@Autowired
	OrderRepository orderRepository;
	
	public OrderResponseDTO store(OrderCreateDTO create)
	{
		Order order = OrderMapper.toEntity(create);
		return OrderMapper.toDTO(orderRepository.save(order));
	}

	public List<OrderResponseDTO> list()
	{
		List<Order> orders = orderRepository.findAll();
		return orders.stream().map(OrderMapper::toDTO).toList();
	}
}
