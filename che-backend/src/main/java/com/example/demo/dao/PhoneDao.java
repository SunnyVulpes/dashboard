package com.example.demo.dao;

import com.example.demo.dto.PhoneDto;
import com.example.demo.entity.Phone;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PhoneDao extends JpaRepository<Phone,Long> {
    Optional<Phone> findFirstByOrderByIdDesc();
    @Query("SELECT new com.example.demo.dto.PhoneDto(t.isexist,t.updateTime) FROM Phone t GROUP BY t.isexist,t.updateTime order by t.updateTime")
    List<PhoneDto> findPhonelist(Pageable pageable);
}
