package com.example.demo.dao;

import com.example.demo.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CarDao extends JpaRepository<Car,Long>, JpaSpecificationExecutor<Car> {
}
