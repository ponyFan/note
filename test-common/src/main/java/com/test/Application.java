package com.test;

import com.test.anotation.NotEmpty;
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

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

}
