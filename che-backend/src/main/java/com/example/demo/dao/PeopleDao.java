package com.example.demo.dao;

import com.example.demo.entity.People;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeopleDao extends JpaRepository<People,Long> {
    Optional<People> findPeopleByPeopleIdAndDeleteTimeNull(String peopleid);
    List<People> findAllByDeleteTimeNull();
}
