package com.example.demo.entity;

import com.example.demo.base.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Data
@Entity
@Table(indexes={@Index(name="url_index",columnList ="url" )})
public class Photo extends BaseEntity {
    String url;
    //0未完成 1已完成
    Integer status;

}
