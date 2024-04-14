package com.example.demo.dao;

import com.example.demo.dto.PeopleNumDto;
import com.example.demo.entity.PeopleNum;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeopleNumDao extends JpaRepository<PeopleNum,Long>, JpaSpecificationExecutor<PeopleNum> {
    Optional<PeopleNum> findFirstByOrderByIdDesc();
    @Query("SELECT new com.example.demo.dto.PeopleNumDto(t.peoplenum,t.updateTime) FROM PeopleNum t GROUP BY t.peoplenum,t.updateTime order by t.updateTime")
    List<PeopleNumDto> findAveragePeoplenumlist(Pageable pageable);
}
