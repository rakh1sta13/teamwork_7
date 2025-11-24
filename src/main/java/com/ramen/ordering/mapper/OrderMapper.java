package com.ramen.ordering.mapper;

import com.ramen.ordering.dto.OrderDTO;
import com.ramen.ordering.entity.Order;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {BranchMapper.class, OrderItemMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {
  @Mapping(target = "branchId", source = "branch.id")
  @Mapping(target = "branchName", source = "branch.name")
  @Mapping(target = "clientName", source = "clientName")
  @Mapping(target = "clientEmail", source = "clientEmail")
  @Mapping(target = "clientPhone", source = "clientPhone")
  @Mapping(target = "createdAt", source = "createdAt")
  @Mapping(target = "updatedAt", source = "updatedAt")
  OrderDTO toDTO(Order order);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "branch.id", source = "branchId")
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  Order toEntity(OrderDTO orderDTO);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntityFromDTO(OrderDTO orderDTO, @MappingTarget Order order);
}