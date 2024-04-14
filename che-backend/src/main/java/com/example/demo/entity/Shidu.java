package com.example.demo.entity;

import com.example.demo.base.BaseEntity;
import com.example.demo.dto.ShiduDto;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Data
@Entity
@Table(indexes={@Index(name="shidu_index",columnList ="shidu" )})
public class Shidu extends BaseEntity {
    Double shidu;
    public ShiduDto convertToDto(){
        ShiduDto shiduDto=new ShiduDto();
        shiduDto.setShidu(this.getShidu());
        shiduDto.setUpdateTime(this.getUpdateTime().toString());
        return shiduDto;
    }
}
