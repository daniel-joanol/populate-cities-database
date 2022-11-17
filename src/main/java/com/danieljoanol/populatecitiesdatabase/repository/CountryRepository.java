package com.danieljoanol.populatecitiesdatabase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.danieljoanol.populatecitiesdatabase.entity.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    
    Country findByName(String name);
}
