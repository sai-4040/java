package com.ensar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ensar.entity.UserPasswordResetRequest;

@Repository
public interface UserPasswordResetRequestRepository extends JpaRepository<UserPasswordResetRequest, String> {
}
