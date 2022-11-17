package com.danieljoanol.populatecitiesdatabase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.danieljoanol.populatecitiesdatabase.entity.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    
    Region findByName(String name);
    
}
