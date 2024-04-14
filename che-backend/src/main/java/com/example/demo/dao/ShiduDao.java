package com.example.demo.dao;

import com.example.demo.dto.ShiduDto;
import com.example.demo.entity.Shidu;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShiduDao extends JpaRepository<Shidu,Long>, JpaSpecificationExecutor<Shidu> {
    Optional<Shidu> findFirstByOrderByIdDesc();
    @Query("SELECT new com.example.demo.dto.ShiduDto(AVG(t.shidu),date_format(t.updateTime,'%Y-%m-%d %H:%i:00')AS truncatedTime) FROM Shidu t GROUP BY truncatedTime order by truncatedTime")
    List<ShiduDto> findAverageShidulist(Pageable pageable);
}
