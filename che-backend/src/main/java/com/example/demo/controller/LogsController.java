package com.example.demo.controller;

import com.example.demo.base.BackendException;
import com.example.demo.base.HttpResponse;
import com.example.demo.dao.LogsDao;
import com.example.demo.dto.LogsDto;
import com.example.demo.entity.Logs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/log")
public class LogsController {
    @Autowired
    LogsDao logsDao;
    @RequestMapping("/get")
    public HttpResponse<List<LogsDto>> getlist(Integer pagesize){
        if(pagesize==null)pagesize=10;
        List<Logs> logs=logsDao.findLogsByOrderByIdDesc(PageRequest.of(0, pagesize));
        return HttpResponse.success(logs.stream().map(Logs::convertToDto).collect(Collectors.toList()));
    }
    @RequestMapping("/set")
    public HttpResponse<LogsDto> setlog(String content,String level){
        if(content==null)throw new BackendException("内容不能为空");
        Logs logs=new Logs();
        logs.setLevel(level);
        logs.setContent(content);
        logsDao.saveAndFlush(logs);
        return HttpResponse.success(logs.convertToDto());
    }
}
