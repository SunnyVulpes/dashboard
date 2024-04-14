package com.example.demo.controller;

import com.example.demo.base.BackendException;
import com.example.demo.base.HttpResponse;
import com.example.demo.dao.ShiduDao;
import com.example.demo.dto.ShiduDto;
import com.example.demo.entity.Shidu;
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
@RequestMapping("/shidu")
public class ShiduController {
    Double curr;
    @Autowired
    ShiduDao shiduDao;
    @PostConstruct
    public void init(){
        if(shiduDao.findFirstByOrderByIdDesc().isPresent()){
            curr=shiduDao.findFirstByOrderByIdDesc().get().getShidu();
        }
    }
    @GetMapping("/put")
    public HttpResponse<String> put(Double shidu){
        curr=shidu;
        Shidu shidu1=new Shidu();
        shidu1.setShidu(shidu);
        shiduDao.saveAndFlush(shidu1);
        return HttpResponse.success("当前湿度"+curr);
    }
    @GetMapping("/get")
    public HttpResponse<Double> get(){
        if(curr==null)throw new BackendException("还没有湿度记录");
        return HttpResponse.success(curr);
    }
    @GetMapping("/getlist")
    public HttpResponse<List<ShiduDto>> getlist(){
        return HttpResponse.success(shiduDao.findAverageShidulist(PageRequest.of(0, 20)));
    }

}
