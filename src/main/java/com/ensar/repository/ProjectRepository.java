package com.ensar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ensar.entity.ForecastDashBoard;
import com.ensar.entity.Organization;
import com.ensar.entity.Project;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {
	Project findByName(String name);

    boolean existsByName(String name);
}
