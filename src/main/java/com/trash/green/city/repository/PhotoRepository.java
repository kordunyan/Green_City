package com.trash.green.city.repository;

import com.trash.green.city.domain.PhotoEntity;
import org.springframework.data.repository.CrudRepository;

public interface PhotoRepository extends CrudRepository<PhotoEntity, Integer> {}
