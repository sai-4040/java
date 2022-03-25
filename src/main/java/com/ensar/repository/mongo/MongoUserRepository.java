package com.ensar.repository.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


import com.ensar.entity.mongo.MongoUser;



public interface MongoUserRepository extends MongoRepository<MongoUser, String> {
    @Query("{ 'name' : ?0 }")
    List<MongoUser> findUsersByName(String name);

    @Query("{ 'age' : { $gt: ?0, $lt: ?1 } }")
    List<MongoUser> findUsersByAgeBetween(int ageGT, int ageLT);

    @Query("{ 'name' : { $regex: ?0 } }")
    List<MongoUser> findUsersByRegexpName(String regexp);

    List<MongoUser> findByName(String name);

    List<MongoUser> findByNameLikeOrderByAgeAsc(String name);

    List<MongoUser> findByAgeBetween(int ageGT, int ageLT);

    List<MongoUser> findByNameStartingWith(String regexp);

    List<MongoUser> findByNameEndingWith(String regexp);

    @Query(value = "{}", fields = "{name : 1}")
    List<MongoUser> findNameAndId();

    @Query(value = "{}", fields = "{_id : 0}")
    List<MongoUser> findNameAndAgeExcludeId();
}