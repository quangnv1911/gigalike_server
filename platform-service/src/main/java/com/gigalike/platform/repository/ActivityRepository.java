package com.gigalike.platform.repository;

import com.gigalike.platform.entity.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ActivityRepository extends MongoRepository<Activity, UUID> {

}
