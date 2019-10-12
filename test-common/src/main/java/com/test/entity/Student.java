package com.test.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;


/*
@Null  被注释的元素必须为null
@NotNull  被注释的元素不能为null
@AssertTrue  被注释的元素必须为true
@AssertFalse  被注释的元素必须为false
@Min(value)  被注释的元素必须是一个数字，其值必须大于等于指定的最小值
@Max(value)  被注释的元素必须是一个数字，其值必须小于等于指定的最大值
@DecimalMin(value)  被注释的元素必须是一个数字，其值必须大于等于指定的最小值
@DecimalMax(value)  被注释的元素必须是一个数字，其值必须小于等于指定的最大值
@Size(max,min)  被注释的元素的大小必须在指定的范围内。
@Digits(integer,fraction)  被注释的元素必须是一个数字，其值必须在可接受的范围内
@Past  被注释的元素必须是一个过去的日期
@Future  被注释的元素必须是一个将来的日期
@Pattern(value) 被注释的元素必须符合指定的正则表达式。
@Email 被注释的元素必须是电子邮件地址
@Length 被注释的字符串的大小必须在指定的范围内
@NotEmpty  被注释的字符串必须非空
@Range  被注释的元素必须在合适的范围内
* */
@Data
@Entity
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private int id;
    @Column(nullable = false)
    private int age;
    @Column(nullable = false)
    @NotBlank(message = "name is null")
    private String name;
    @Column(nullable = false)
    private int grade;
    @Column(nullable = false)
    private String address;

    public Student() {
    }

    public Student(int id, int age, String name, int grade, String address) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.grade = grade;
        this.address = address;
    }
}
