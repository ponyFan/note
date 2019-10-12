package com.test;

import com.test.dao.StudentRepository;
import com.test.entity.Student;
import com.test.spring.aop.TargetTestImpl;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.io.File;
import java.io.IOException;

@RestController/*相当于controller+@ResponseBody*/
/*隐式的定义了基础的包扫描路径如@service，@component*/
/*@EnableAutoConfiguration
@ComponentScan*/
@EnableScheduling
@SpringBootApplication/*等价于@Configuration，@EnableAutoConfiguration和@ComponentScan*/
public class Application {

    @Value("${person.age}")
    private int age;

    @Value("${person.name}")
    private String name;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TargetTestImpl targetTest;

    @RequestMapping("/")
    String home(@RequestBody @Valid Student student, BindingResult result) {
        boolean b = result.hasErrors();
        if (b){
            return result.getFieldError().getDefaultMessage();
        }
        try {
            studentRepository.save( new Student(10, age, "sss", 3, "us"));
        }catch (ValidationException e){
            e.printStackTrace();
            return e.getCause().getMessage();
        }
        return "success";
    }

    @RequestMapping("/select")
    Student select(){
        return studentRepository.selectById(10);
    }

    @RequestMapping("/delete")
    String delete(){
        try {
            studentRepository.delete();
        }catch (Exception e){
            e.printStackTrace();
            return "fail in delete";
        }
        return "success";
    }

    /**
     * @return 切面测试
     */
    @RequestMapping("/test")
    String testAspect(){
        return targetTest.testInsert(new Student());
    }

    @RequestMapping("/download")
    public ResponseEntity upload1(HttpServletRequest request, HttpServletResponse response )  throws IOException {
        byte[] bytes = new byte[10240];
        try {
            bytes = FileUtils.readFileToByteArray(new File("C:\\Users\\fanzelei\\Downloads\\access.log"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileSystemResource file = new FileSystemResource("C:\\Users\\fanzelei\\Downloads\\access.log");
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

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

}
