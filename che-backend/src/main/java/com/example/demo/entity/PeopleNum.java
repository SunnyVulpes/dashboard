package com.example.demo.entity;

import com.example.demo.base.BaseEntity;
import com.example.demo.dto.PeopleNumDto;
import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class PeopleNum extends BaseEntity {
    Integer peoplenum;
    public PeopleNumDto convertToDto(){
        PeopleNumDto peopleNumDto =new PeopleNumDto();
        peopleNumDto.setPeoplenum(this.getPeoplenum());
        peopleNumDto.setUpdateTime(this.getUpdateTime());
        return peopleNumDto;
    }
}
