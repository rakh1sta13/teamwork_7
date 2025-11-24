package com.ramen.ordering.mapper;

import com.ramen.ordering.dto.OrderItemDTO;
import com.ramen.ordering.entity.OrderItem;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderItemMapper {
  OrderItemDTO toDTO(OrderItem orderItem);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  OrderItem toEntity(OrderItemDTO orderItemDTO);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntityFromDTO(OrderItemDTO orderItemDTO, @MappingTarget OrderItem orderItem);
}