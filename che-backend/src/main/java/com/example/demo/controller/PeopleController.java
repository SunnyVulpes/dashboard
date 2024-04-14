package com.example.demo.controller;

import com.example.demo.base.BackendException;
import com.example.demo.base.HttpResponse;
import com.example.demo.dao.LogsDao;
import com.example.demo.dao.PeopleDao;
import com.example.demo.dao.PeopleNumDao;
import com.example.demo.dto.LogsDto;
import com.example.demo.dto.PeopleDto;
import com.example.demo.dto.PeopleNumDto;
import com.example.demo.entity.Logs;
import com.example.demo.entity.People;
import com.example.demo.entity.PeopleNum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/people")
public class PeopleController {
    @Autowired
    PeopleNumDao peopleNumDao;
    @Autowired
    LogsDao logsDao;

    Integer peoplenum;
    @Autowired
    PeopleDao peopleDao;
    @PostConstruct
    public void init(){
        if(peopleNumDao.findFirstByOrderByIdDesc().isPresent()){
            peoplenum= peopleNumDao.findFirstByOrderByIdDesc().get().getPeoplenum();
        }
        else{
            peoplenum=0;
        }
    }
    @GetMapping("/in")
    public HttpResponse<LogsDto> in(String peopleid){
        if(!peopleDao.findPeopleByPeopleIdAndDeleteTimeNull(peopleid).isPresent()){
            People people=new People();
            people.setStatus(1);
            people.setPeopleId(peopleid);
            peopleDao.save(people);
        }
        else{
            People people=peopleDao.findPeopleByPeopleIdAndDeleteTimeNull(peopleid).get();
            if(people.getStatus().equals(1))throw new BackendException("这人已经进来过了，又进来一次？？？让他先出去吧");
            people.setStatus(1);
            peopleDao.saveAndFlush(people);
        }
        peoplenum++;
        PeopleNum peopleNum =new PeopleNum();
        peopleNum.setPeoplenum(peoplenum);
        Logs logs=new Logs();
        logs.setLevel("info");
        logs.setContent("编号："+peopleid+" 进入，当前人数："+peoplenum);
        logsDao.saveAndFlush(logs);
        peopleNumDao.saveAndFlush(peopleNum);

        return HttpResponse.success(logs.convertToDto());
    }
    @GetMapping("/out")
    public HttpResponse<LogsDto> out(String peopleid){
        if(!peopleDao.findPeopleByPeopleIdAndDeleteTimeNull(peopleid).isPresent()){
            throw new BackendException("这人还没注册呢，先让他进来注册一下");
        }
        else{
            People people=peopleDao.findPeopleByPeopleIdAndDeleteTimeNull(peopleid).get();
            if(people.getStatus().equals(0))throw new BackendException("这人还没进来呢，咋就出去了");
            people.setStatus(0);
            peopleDao.saveAndFlush(people);
        }
        if(peoplenum<=0)throw new BackendException("人数已经为0");
        peoplenum--;
        PeopleNum peopleNum =new PeopleNum();
        peopleNum.setPeoplenum(peoplenum);
        peopleNumDao.saveAndFlush(peopleNum);
        Logs logs=new Logs();
        logs.setLevel("info");
        logs.setContent("编号："+peopleid+" 离开，当前人数："+peoplenum);
        logsDao.saveAndFlush(logs);
        return HttpResponse.success(logs.convertToDto());
    }
    @GetMapping("/get")
    public HttpResponse<Integer> get(){
        return HttpResponse.success(peoplenum);
    }
    @GetMapping("/getlist")
    public HttpResponse<List<PeopleNumDto>> getlist(){
        return HttpResponse.success(peopleNumDao.findAveragePeoplenumlist(PageRequest.of(0, 20)));
    }
    @GetMapping("/getAllPeople")
    public HttpResponse<List<PeopleDto>> getPeopleList(){
        return HttpResponse.success(peopleDao.findAllByDeleteTimeNull().stream().map(People::convertToDto).collect(Collectors.toList()));
    }
}
