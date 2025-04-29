package com.shopix.api.mappers;

import java.util.List;

import com.shopix.api.dtos.OrderCreateDTO;
import com.shopix.api.dtos.OrderItemResponseDTO;
import com.shopix.api.dtos.OrderResponseDTO;
import com.shopix.api.entities.Order;
import com.shopix.api.entities.OrderItem;
import com.shopix.api.repository.ProductRepository;

public class OrderMapper {
	public static OrderResponseDTO toDTO(Order order)
	{
		List<OrderItemResponseDTO> items = order.getItems().stream().map(OrderItemMapper::toDTO).toList();
		return new OrderResponseDTO(order.getId(), order.getTotalPrice(), order.getStatus(), order.getPaymentMethod(), order.getTrackingCode(), order.getCreated_at(), order.getUpdated_at(), items);
	}
	
	public static Order toEntity(OrderCreateDTO dto, ProductRepository productRepository)
	{
		List<OrderItem> items = dto
				.items()
				.stream()
				.map(item -> OrderItemMapper.toEntity(item, productRepository))
				.toList();
		Order order = new Order();
		order.setTotalPrice(dto.totalPrice());
		order.setStatus(dto.status());
		order.setPaymentMethod(dto.paymentMethod());
		order.setTrackingCode(dto.trackingCode());
		order.setItems(items);
		return order;
	}
}
