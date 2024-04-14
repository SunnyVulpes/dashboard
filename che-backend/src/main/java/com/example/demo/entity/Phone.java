package com.example.demo.entity;

import com.example.demo.base.BaseEntity;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Phone extends BaseEntity {
    Boolean isexist;
}
