package com.ensar.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity(name = "clients")
@Data
@EqualsAndHashCode(callSuper = true)
public class Client extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "contact")
    private String contact;

 

}
