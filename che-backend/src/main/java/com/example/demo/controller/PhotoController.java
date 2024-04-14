package com.example.demo.controller;

import com.example.demo.base.BackendException;
import com.example.demo.dao.PhotoDao;
import com.example.demo.dto.PhotoDto;
import com.example.demo.entity.Photo;
import com.example.demo.util.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/photo")
public class PhotoController {
    @Autowired
    PhotoDao photoDao;
    String currentUrl;
    @Autowired
    private MessageSender messageSender;
    @PostConstruct
    public void init(){
        if(photoDao.findFirstByOrderByIdDesc().isPresent()){
            currentUrl=photoDao.findFirstByOrderByIdDesc().get().getUrl();
        }
    }

//    @GetMapping("/get")
//    public ResponseEntity<byte[]> getImg(String filePath) throws IOException {//通过自己写的http工具类获取到图片输入流
//        MultipartFile file = this.getMulipartFiles2(filePath);
//        byte[] bytesByStream = file.getBytes();
//        final HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.parseMediaType(file.getContentType()));
//        return new ResponseEntity<>(bytesByStream, headers, HttpStatus.OK);
//    }
    @GetMapping("/get")
    public ResponseEntity<byte[]> getImg() throws IOException {//通过自己写的http工具类获取到图片输入流
        if(currentUrl==null)throw new BackendException("还没有收到图片");
        MultipartFile file = this.getMulipartFiles2(currentUrl);
//        MultipartFile file1 = this.getMulipartFiles2(url1);
//        MultipartFile file2 =this.getMulipartFiles2(url2);
//        MultipartFile file3 =this.getMulipartFiles2(url3);
        byte[] bytesByStream = file.getBytes();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(file.getContentType()));
        return new ResponseEntity<>(bytesByStream, headers, HttpStatus.OK);
    }
    @GetMapping("/geturl")
    public List<String> geturl(){
        List<Photo> photos=photoDao.findTop4PhotosByStatusOrderByIdDesc(1);
        List<String> urls=new ArrayList<>(4);
        for(Photo photo:photos){
            urls.add(photo.getUrl());
        }
        return urls;
    }
    @GetMapping("/getbyurl")
    public ResponseEntity<byte[]> getImgByUrl(String url) throws IOException {//通过自己写的http工具类获取到图片输入流
        MultipartFile file = this.getMulipartFiles2(url);
//        MultipartFile file1 = this.getMulipartFiles2(url1);
//        MultipartFile file2 =this.getMulipartFiles2(url2);
//        MultipartFile file3 =this.getMulipartFiles2(url3);
        byte[] bytesByStream = file.getBytes();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(file.getContentType()));
        return new ResponseEntity<>(bytesByStream, headers, HttpStatus.OK);
    }
    private MultipartFile getMulipartFiles2(String filePath) throws IOException {
        System.out.println(filePath);
        File file = new File(filePath);
        FileInputStream fileInputStream = new FileInputStream(file);
        String filename = file.getName();
        String extension = filename.substring(filename.lastIndexOf(".") + 1);
        String contentType;
        switch (extension.toLowerCase()) {
            case "jpg":
            case "jpeg":
                contentType = "image/jpeg";
                break;
            case "png":
                contentType = "image/png";
                break;
            case "gif":
                contentType = "image/gif";
                break;
            default:
                contentType = "application/octet-stream";
        }
        MultipartFile multipartFile = new MockMultipartFile(filename, filename, contentType, fileInputStream);
        return multipartFile;
    }
    @PostMapping("/upload")
    public String upload(MultipartFile photo, HttpServletRequest request) throws IOException {
        System.out.println(photo.getOriginalFilename());
        System.out.println(photo.getContentType());
//        String path =request.getServletContext().getRealPath("/upload/");
        String path="C:/upload/car/";
        System.out.println(path+photo.getOriginalFilename());
        String filename = photo.getOriginalFilename();
        String contentType;
        if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
            contentType = "image/jpeg";
        } else if (filename.endsWith(".png")) {
            contentType = "image/png";
        } else if (filename.endsWith(".gif")) {
            contentType = "image/gif";
        } else {
            throw new BackendException("不支持上传该类型的文件");
        }
//      存图片
        saveFile(photo,path);
        Photo photo1=new Photo();
        photo1.setUrl(path+photo.getOriginalFilename());
        photo1.setStatus(0);
        photoDao.saveAndFlush(photo1);
        currentUrl=photo1.getUrl();
        PhotoDto photoDto=new PhotoDto();
        photoDto.setUrl(photo1.getUrl());
        photoDto.setCallback("http://localhost:8081/photo/callback/"+photo1.getId());
        messageSender.sendPhoto(photoDto);
        return currentUrl;
    }
    private void saveFile(MultipartFile photo,String path) throws IOException {
        File dir = new File(path);
        if(!dir.exists()){
            dir.mkdir();
        }
        File file =new File(path+photo.getOriginalFilename());
        photo.transferTo(file);
    }
    @RequestMapping("/callback/{id}")
    public void callback(@PathVariable long id,MultipartFile photo) throws IOException {
        Photo photo1=photoDao.findById(id).get();
        photo1.setStatus(1);
        photoDao.saveAndFlush(photo1);
        String url=photo1.getUrl();
        File file=new File(url);
        photo.transferTo(file);
    }
}
