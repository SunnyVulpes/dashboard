package com.example.demo.base;
import com.alibaba.fastjson.JSON;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    public Long id;

    @Column(name = "create_time", insertable = false, updatable = false, columnDefinition = "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'")
    public LocalDateTime createTime;

    @Column(name = "update_time", insertable = false, updatable = false, columnDefinition = "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'")
    public LocalDateTime updateTime;

    public LocalDateTime deleteTime;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
        updateTime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updateTime = LocalDateTime.now();
    }
}
