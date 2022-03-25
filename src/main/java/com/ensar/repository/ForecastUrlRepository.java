package com.ensar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ensar.entity.ForecastDashBoard;

import java.util.List;

@Repository
public interface ForecastUrlRepository extends JpaRepository<ForecastDashBoard, String> {
    List<ForecastDashBoard> findByOrganizationIdOrderByCreatedDateTimeDesc(Long orgId);
}
