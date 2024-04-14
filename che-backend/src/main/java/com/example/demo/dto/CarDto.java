package com.example.demo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CarDto {
    private Double speed;
    private LocalDateTime updateTime;
}
