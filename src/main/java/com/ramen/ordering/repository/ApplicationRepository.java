package com.ramen.ordering.repository;

import com.ramen.ordering.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
  List<Application> findByApplicationType(Application.ApplicationType applicationType);

  List<Application> findByStatus(Application.ApplicationStatus status);
}