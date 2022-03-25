package com.ensar.entity.mongo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.ensar.annotation.CascadeSave;

import lombok.Data;

@Document
@Data
public class MongoUser {

    @Id
    private String id;
    @Indexed(direction = IndexDirection.ASCENDING)
    private String name;
    @Indexed(direction = IndexDirection.ASCENDING)
    private Integer age;

   

    

}
