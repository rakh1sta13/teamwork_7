package com.ramen.ordering.service;

import com.ramen.ordering.dto.ApplicationDTO;
import com.ramen.ordering.entity.Application;

import java.util.List;

public interface ApplicationService {
  List<ApplicationDTO> getAllApplications();
  List<ApplicationDTO> getApplicationsByType(Application.ApplicationType applicationType);
  List<ApplicationDTO> getApplicationsByStatus(Application.ApplicationStatus status);
  ApplicationDTO getApplicationById(Long id);
  ApplicationDTO createApplication(ApplicationDTO applicationDTO);
  ApplicationDTO updateApplication(Long id, ApplicationDTO applicationDTO);
  boolean deleteApplication(Long id);
}