package com.shopix.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopix.api.dtos.BuyItemDTO;
import com.shopix.api.dtos.OrderBuyDTO;
import com.shopix.api.dtos.OrderCreateDTO;
import com.shopix.api.dtos.OrderResponseDTO;
import com.shopix.api.dtos.OrderUpdateDTO;
import com.shopix.api.entities.Address;
import com.shopix.api.entities.Cart;
import com.shopix.api.entities.CartItem;
import com.shopix.api.entities.Order;
import com.shopix.api.entities.OrderItem;
import com.shopix.api.entities.ProductVariation;
import com.shopix.api.entities.Promotion;
import com.shopix.api.entities.User;
import com.shopix.api.enuns.OrderStatus;
import com.shopix.api.mappers.OrderMapper;
import com.shopix.api.repository.AddressRepository;
import com.shopix.api.repository.CartRepository;
import com.shopix.api.repository.OrderRepository;
import com.shopix.api.repository.ProductRepository;
import com.shopix.api.repository.PromotionRepository;

@Service
public class OrderService {
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	CartRepository cartRepository;
	@Autowired
	PromotionRepository promotionRepository;
	@Autowired
	AddressRepository addressRepository;
	
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

	public List<OrderResponseDTO> listMyOrders(Authentication auth)
	{
		User user = (User) auth.getPrincipal();
		List<Order> orders = orderRepository.getOrdersByUserId(user.getId());
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
	
	@Transactional
	public OrderResponseDTO buy(Authentication auth, OrderBuyDTO dto) throws Exception
	{
		User user = (User) auth.getPrincipal();
		Address address = addressRepository.findById(dto.address_id()).get();
		List<CartItem> toRemove = new ArrayList<>();
		Order order = new Order();
		order.setUser(user);
		order.setAddress(address);
		List<OrderItem> items = new ArrayList<>();
		float total = 0;
		for (BuyItemDTO item: dto.items()) {
			CartItem ci = cartRepository.getCartItemById(item.cart_item_id());
			if (ci != null) {
				if (ci.getVar().getStock() < ci.getQuantity()) {
					throw new Exception("Não há stoque suficiente para a quantidade desejada");
				}
				float perc = 1;
				if (item.promotion_id().isPresent()) {
					Optional<Promotion> prom = promotionRepository.findById(item.promotion_id().get());
					if (prom.isPresent() && PromotionService.validPromotion(prom.get())) {
						perc = 1f - prom.get().getDiscountPercentage();
					} else {
						throw new Exception("A promoção selecionada está inativa");
					}
				}
				float subtotal = (float) ci.getVar().getProduct().getPrice() * ci.getQuantity() * perc;
				total += subtotal;
				OrderItem oi = new OrderItem();
				oi.setQuantity(ci.getQuantity());
				oi.setSubtotal(subtotal);
				oi.setVar(ci.getVar());
				items.add(oi);
				ProductVariation var = ci.getVar();
				var.setStock(var.getStock() - ci.getQuantity());
				productRepository.save(var.getProduct());
				toRemove.add(ci);
			} else {
				throw new Exception("Item do carrinho não encontrado");
			}
		}
		order.setPaymentMethod(dto.paymentMethod());
		order.setItems(items);
		order.setTotalPrice(total);
		order.setStatus(OrderStatus.PENDING_PAYMENT);
		for (CartItem item: toRemove) {
			Cart cart = item.getCart();
			cart.getItems().remove(item);
			cartRepository.save(cart);
		}
		return OrderMapper.toDTO(orderRepository.save(order));
	}
}
