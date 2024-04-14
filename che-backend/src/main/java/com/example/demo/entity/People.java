package com.example.demo.entity;

import com.example.demo.base.BaseEntity;
import com.example.demo.dto.PeopleDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedEntityGraph;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class People extends BaseEntity {
    @Column(unique = true)
    String peopleId;
    //0不在，1在
    Integer status;
    public PeopleDto convertToDto(){
        PeopleDto peopleDto=new PeopleDto();
        peopleDto.setPeopleId(this.getPeopleId());
        peopleDto.setUpdateTime(this.getUpdateTime());
        peopleDto.setStatus(this.getStatus());
        return  peopleDto;
    }
}
