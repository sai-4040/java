package com.ensar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ensar.entity.Epic;
import com.ensar.entity.ForecastDashBoard;
import com.ensar.entity.Organization;
import com.ensar.entity.Project;
import com.ensar.entity.Story;

import java.util.List;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {
	Epic findByName(String name);

    boolean existsByName(String name);
}
