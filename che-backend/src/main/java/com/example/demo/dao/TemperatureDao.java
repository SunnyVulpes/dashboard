package com.example.demo.dao;

import com.example.demo.dto.TemperatureDto;
import com.example.demo.entity.Temperature;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TemperatureDao extends JpaRepository<Temperature,Long>, JpaSpecificationExecutor<Temperature> {
    Optional<Temperature> findFirstByOrderByIdDesc();
    @Query("SELECT new com.example.demo.dto.TemperatureDto(AVG(t.temperature),date_format(t.updateTime,'%Y-%m-%d %H:%i:00')AS truncatedTime) FROM Temperature t GROUP BY truncatedTime order by truncatedTime")
    List<TemperatureDto> findAverageTemperaturelist(Pageable pageable);
}
