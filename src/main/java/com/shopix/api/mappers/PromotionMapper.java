package com.shopix.api.mappers;

import com.shopix.api.dtos.PromotionCreateDTO;
import com.shopix.api.dtos.PromotionResponseDTO;
import com.shopix.api.entities.Promotion;

public class PromotionMapper {
	public static PromotionResponseDTO toDTO(Promotion promotion)
	{
		return new PromotionResponseDTO(promotion.getId(), promotion.getName(), promotion.getDiscountPercentage(), promotion.getStartDate(), promotion.getEndDate(), promotion.isActive());
	}
	
	public static Promotion toEntity(PromotionCreateDTO dto)
	{
		Promotion promotion = new Promotion();
		promotion.setName(dto.name());
		promotion.setDiscountPercentage(dto.discountPercentage());
		promotion.setStartDate(dto.startDate());
		promotion.setEndDate(dto.endDate());
		promotion.setActive(dto.isActive());
		return promotion;
	}

}
