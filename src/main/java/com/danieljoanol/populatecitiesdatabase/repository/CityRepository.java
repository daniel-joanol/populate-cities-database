package com.danieljoanol.populatecitiesdatabase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.danieljoanol.populatecitiesdatabase.entity.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    
    City findByName(String name);

}
