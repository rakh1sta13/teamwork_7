package com.ramen.ordering.mapper;

import com.ramen.ordering.dto.ApplicationDTO;
import com.ramen.ordering.entity.Application;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ApplicationMapper {
  @Mapping(target = "createdAt", source = "createdAt")
  @Mapping(target = "updatedAt", source = "updatedAt")
  ApplicationDTO toDTO(Application application);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  Application toEntity(ApplicationDTO applicationDTO);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntityFromDTO(ApplicationDTO applicationDTO, @MappingTarget Application application);
}