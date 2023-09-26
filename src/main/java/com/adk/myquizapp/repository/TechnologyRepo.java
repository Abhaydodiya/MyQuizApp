package com.adk.myquizapp.repository;

import com.adk.myquizapp.model.Technology;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnologyRepo extends JpaRepository<Technology,Integer> {

    public Technology findTechnologyByTechId(int id);
    public Technology findTechnologyByTechnologyName(String name);

}
