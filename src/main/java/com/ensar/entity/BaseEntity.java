package com.ensar.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@MappedSuperclass
public abstract class BaseEntity {
    
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonIgnore
    @CreatedDate
    @Column(name = "created_date_time")
    private Timestamp createdDateTime;

	@JsonIgnore
    @LastModifiedDate
    @Column(name = "last_updated_date_time")
    private Timestamp lastUpdatedDateTime;

    @PrePersist
    public void init() {
        
        createdDateTime = Timestamp.valueOf(LocalDateTime.now());
    }

    @PreUpdate
    public void preUpdate() {
        lastUpdatedDateTime = Timestamp.valueOf(LocalDateTime.now());
    }

}
