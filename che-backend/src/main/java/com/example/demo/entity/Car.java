package com.example.demo.entity;

import com.example.demo.base.BaseEntity;
import com.example.demo.dto.CarDto;
import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class Car extends BaseEntity {

    Double speed;
    public CarDto convertToDto(){
        CarDto carDto=new CarDto();
        carDto.setSpeed(this.getSpeed());
        carDto.setUpdateTime(this.getUpdateTime());
        return carDto;
    }
}
