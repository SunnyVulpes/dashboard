package com.example.demo.dao;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.Logs;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogsDao extends JpaRepository<Logs,Long> {
    List<Logs> findLogsByOrderByIdDesc(Pageable pageable);
}
