package com.ensar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ensar.entity.Feature;
import com.ensar.entity.ForecastDashBoard;
import com.ensar.entity.Organization;
import com.ensar.entity.Project;
import com.ensar.entity.Release;

import java.util.List;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, String> {
	
	Feature findByName(String name);

    
}
