package com.example.demo.entity;

import com.example.demo.base.BaseEntity;
import com.example.demo.dto.TemperatureDto;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Data
@Entity
@Table(indexes={@Index(name="temperature_index",columnList ="temperature" )})
public class Temperature extends BaseEntity {
    Double temperature;
    public TemperatureDto convertToDto(){
        TemperatureDto temperatureDto=new TemperatureDto();
        temperatureDto.setTemperature(this.getTemperature());
        temperatureDto.setUpdateTime(this.getUpdateTime().toString());
        return temperatureDto;
    }
}
