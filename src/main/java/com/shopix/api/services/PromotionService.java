package com.shopix.api.services;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopix.api.dtos.PromotionCreateDTO;
import com.shopix.api.dtos.PromotionResponseDTO;
import com.shopix.api.dtos.PromotionUpdateDTO;
import com.shopix.api.entities.Promotion;
import com.shopix.api.mappers.PromotionMapper;
import com.shopix.api.repository.ProductRepository;
import com.shopix.api.repository.PromotionRepository;

@Service
public class PromotionService {
	@Autowired
	PromotionRepository promotionRepository;
	@Autowired
	ProductRepository productRepository;
	
	public PromotionResponseDTO store(PromotionCreateDTO dto)
	{
		Promotion promotion = PromotionMapper.toEntity(dto, productRepository);
		return PromotionMapper.toDTO(promotionRepository.save(promotion));
	}
	
	public List<PromotionResponseDTO> list()
	{
		List<Promotion> promotions = promotionRepository.findAll();
		return promotions
			.stream()
			.filter(PromotionService::validPromotion)
			.map(PromotionMapper::toDTO)
			.toList();
	}
	
	public PromotionResponseDTO show(Long id)
	{
		Promotion promotion = promotionRepository
			.findById(id)
			.orElseThrow(() -> new RuntimeException("Promoção não encontrada"));
		return PromotionMapper.toDTO(promotion);
	}
	
	public PromotionResponseDTO update(PromotionUpdateDTO dto)
	{
		Promotion promotion = promotionRepository
			.findById(dto.id())
			.orElseThrow(() -> new RuntimeException("Promoção não encontrada"));
		promotion.setName(dto.name());
		promotion.setDiscountPercentage(dto.discountPercentage());
		promotion.setStartDate(dto.startDate());
		promotion.setEndDate(dto.endDate());
		promotion.setActive(dto.isActive());
		return PromotionMapper.toDTO(promotion);
	}
	
	public void destroy(Long id)
	{
		Promotion promotion = promotionRepository
			.findById(id)
			.orElseThrow(() -> new RuntimeException("Promoção não encontrada"));
		promotionRepository.delete(promotion);
	}
	
	public PromotionResponseDTO productPromotions(Long id)
	{
		List<Promotion> ps = promotionRepository.getPromotionsByProductId(id);
		Promotion promotion = ps.stream()
			.filter(PromotionService::validPromotion)
			.max(Comparator.comparingDouble(p -> p.getDiscountPercentage()))
			.orElseThrow(() -> new RuntimeException("Promoção não encontrada"));
		return PromotionMapper.toDTO(promotion);
	}
	
	public static boolean validPromotion(Promotion p)
	{
		if (p.isActive()) {
			LocalDate now = LocalDate.now();
			LocalDate start = p.getStartDate().toLocalDate();
			LocalDate end = p.getEndDate().toLocalDate();
			return (
				(start.isBefore(now) || start.equals(now)) &&
				(now.isBefore(end) || now.equals(end))
			);
		} else {
			return false;
		}
	}
}
