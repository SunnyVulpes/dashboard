package com.example.demo.entity;
import com.example.demo.base.BaseEntity;
import com.example.demo.dto.LogsDto;
import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Logs extends BaseEntity {
    private String content;
    private String level;
    public LogsDto convertToDto(){
        LogsDto logsDto=new LogsDto();
        logsDto.setComment(this.getContent());
        logsDto.setLevel(this.getLevel());
        logsDto.setUpdateTime(this.getUpdateTime());
        return logsDto;
    }
}
