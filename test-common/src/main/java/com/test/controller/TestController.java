package com.test.controller;

import com.test.anotation.NotEmpty;
import com.test.dao.StudentRepository;
import com.test.entity.Student;
import com.test.spring.aop.TargetTestImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

/**
 * @author zelei.fan
 * @date 2020/3/26 11:34
 * @description
 */
@RestController
@Api
public class TestController {

    @Value("${person.age}")
    private int age;

    @Value("${person.name}")
    private String name;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TargetTestImpl targetTest;

    @PutMapping("/insert")
    @ApiOperation("插入数据")
    public void insert(Student student){
        studentRepository.save(student);
    }

    @GetMapping("/select")
    @ApiOperation("查询")
    Student select(@NotEmpty String name){
        return studentRepository.selectById(10);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除")
    String delete(){
        try {
            studentRepository.delete();
        }catch (Exception e){
            e.printStackTrace();
            return "fail in delete";
        }
        return "success";
    }

    @PostMapping("/test")
    @ApiOperation("切面测试")
    String testAspect(){
        return targetTest.testInsert(new Student());
    }

    @GetMapping("/download")
    @ApiOperation("下载")
    public ResponseEntity upload1(String path)  throws IOException {
        byte[] bytes = new byte[10240];
        try {
            bytes = FileUtils.readFileToByteArray(new File("C:\\Users\\fanzelei\\Downloads\\access.log"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileSystemResource file = new FileSystemResource(path);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getFilename()));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(file.getInputStream()));
    }
}
