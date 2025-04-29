package com.shopix.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopix.api.dtos.OrderCreateDTO;
import com.shopix.api.dtos.OrderResponseDTO;
import com.shopix.api.dtos.OrderUpdateDTO;
import com.shopix.api.entities.Order;
import com.shopix.api.mappers.OrderMapper;
import com.shopix.api.repository.OrderRepository;
import com.shopix.api.repository.ProductRepository;

@Service
public class OrderService {
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	ProductRepository productRepository;
	
	public OrderResponseDTO store(OrderCreateDTO create)
	{
		Order order = OrderMapper.toEntity(create, productRepository);
		return OrderMapper.toDTO(orderRepository.save(order));
	}

	public List<OrderResponseDTO> list()
	{
		List<Order> orders = orderRepository.findAll();
		return orders.stream().map(OrderMapper::toDTO).toList();
	}
	
	public OrderResponseDTO show(Long id)
	{
		Order order = orderRepository
			.findById(id)
			.orElseThrow(() -> new RuntimeException("Ordem não encontrada"));
		return OrderMapper.toDTO(order);
	}
	
	public OrderResponseDTO update(OrderUpdateDTO dto)
	{
		Order order = orderRepository
			.findById(dto.id())
			.orElseThrow(() -> new RuntimeException("Ordem não encontrada"));
		order.setStatus(dto.status());
		order.setTotalPrice(dto.totalPrice());
		order.setPaymentMethod(dto.paymentMethod());
		order.setTrackingCode(dto.trackingCode());
		return OrderMapper.toDTO(orderRepository.save(order));
	}
	
	public void destroy(Long id)
	{
		Order order = orderRepository
			.findById(id)
			.orElseThrow(() -> new RuntimeException("Ordem não encontrada"));
		orderRepository.delete(order);
	}
}
