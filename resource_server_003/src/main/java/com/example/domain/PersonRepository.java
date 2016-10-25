package com.example.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 *
 * @author straubec
 */
@RepositoryRestResource
public interface PersonRepository extends CrudRepository<Person, Long> {
    
}
