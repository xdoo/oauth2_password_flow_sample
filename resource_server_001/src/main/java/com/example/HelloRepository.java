package com.example;

import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author straubec
 */
public interface HelloRepository extends CrudRepository<Hello, Long>{
    
}
