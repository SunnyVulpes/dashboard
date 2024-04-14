package com.example.demo.dao;

import com.example.demo.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhotoDao extends JpaRepository<Photo,Long>, JpaSpecificationExecutor<Photo> {
    Optional<Photo> findFirstByOrderByIdDesc();
    List<Photo> findTop4PhotosByStatusOrderByIdDesc(int status);
}
