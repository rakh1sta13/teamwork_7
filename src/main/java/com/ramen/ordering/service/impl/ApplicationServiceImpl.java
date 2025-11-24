package com.ramen.ordering.service.impl;

import com.ramen.ordering.dto.ApplicationDTO;
import com.ramen.ordering.entity.Application;
import com.ramen.ordering.mapper.ApplicationMapper;
import com.ramen.ordering.repository.ApplicationRepository;
import com.ramen.ordering.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationServiceImpl implements ApplicationService {

  private final ApplicationRepository applicationRepository;
  private final ApplicationMapper applicationMapper;

  @Override
  public List<ApplicationDTO> getAllApplications() {
    return applicationRepository.findAll()
        .stream()
        .map(applicationMapper::toDTO)
        .collect(Collectors.toList());
  }

  @Override
  public List<ApplicationDTO> getApplicationsByType(Application.ApplicationType applicationType) {
    return applicationRepository.findByApplicationType(applicationType)
        .stream()
        .map(applicationMapper::toDTO)
        .collect(Collectors.toList());
  }

  @Override
  public List<ApplicationDTO> getApplicationsByStatus(Application.ApplicationStatus status) {
    return applicationRepository.findByStatus(status)
        .stream()
        .map(applicationMapper::toDTO)
        .collect(Collectors.toList());
  }

  @Override
  public ApplicationDTO getApplicationById(Long id) {
    return applicationRepository.findById(id)
        .map(applicationMapper::toDTO)
        .orElse(null);
  }

  @Override
  public ApplicationDTO createApplication(ApplicationDTO applicationDTO) {
    Application application = applicationMapper.toEntity(applicationDTO);
    Application savedApplication = applicationRepository.save(application);
    return applicationMapper.toDTO(savedApplication);
  }

  @Override
  public ApplicationDTO updateApplication(Long id, ApplicationDTO applicationDTO) {
    Application existingApplication = applicationRepository.findById(id).orElse(null);
    if (existingApplication == null) {
      return null;
    }
    applicationMapper.updateEntityFromDTO(applicationDTO, existingApplication);
    Application updatedApplication = applicationRepository.save(existingApplication);
    return applicationMapper.toDTO(updatedApplication);
  }

  @Override
  public boolean deleteApplication(Long id) {
    if (!applicationRepository.existsById(id)) {
      return false;
    }
    applicationRepository.deleteById(id);
    return true;
  }
}