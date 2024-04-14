package com.example.demo.controller;

import com.example.demo.base.BackendException;
import com.example.demo.base.HttpResponse;
import com.example.demo.dao.LogsDao;
import com.example.demo.dao.PhoneDao;
import com.example.demo.dao.PhotoDao;
import com.example.demo.dto.PhoneDto;
import com.example.demo.entity.Logs;
import com.example.demo.entity.Phone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/phone")
public class PhoneController {
    Boolean curr;
    @Autowired
    PhoneDao phoneDao;
    @Autowired
    LogsDao logsDao;
    @PostConstruct
    public void init(){
        if(phoneDao.findFirstByOrderByIdDesc().isPresent())
            curr=phoneDao.findFirstByOrderByIdDesc().get().getIsexist();
    }
    @RequestMapping("/put")
    public HttpResponse<String> put(boolean isexist){
        curr=isexist;
        Phone phone=new Phone();
        phone.setIsexist(isexist);
        phoneDao.saveAndFlush(phone);
        if(isexist){
            Logs logs=new Logs();
            logs.setLevel("warn");
            logs.setContent("发现手机");
            logsDao.saveAndFlush(logs);
        }
        return HttpResponse.success("上传成功，当前手机状态："+curr);
    }
    @RequestMapping("/get")
    public HttpResponse<Boolean> get(){
        if(curr==null)throw new BackendException("还没有手机记录");
        return HttpResponse.success(curr);
    }
    @GetMapping("/getlist")
    public HttpResponse<List<PhoneDto>> getlist(){
        return HttpResponse.success(phoneDao.findPhonelist(PageRequest.of(0,20)));
    }
}
