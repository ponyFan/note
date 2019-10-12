package com.test.tt;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zelei.fan
 * @date 2019/9/19 11:52
 */
public class Test {

    public static void main(String[] args) {
        Student student = Student.builder().id("1").name("test").build();
        Teacher teacher = Teacher.builder().id("2").build();
        School build = School.builder().student(student).teacher(teacher).build();
        School school = new School(student, null);
        BeanUtils.copyProperties(student, teacher);

        List<Teacher> list = Lists.newArrayList();
        list.add(Teacher.builder().id("1").build());
        list.add(Teacher.builder().id("2").build());
        list.add(Teacher.builder().id("3").build());
        list.add(Teacher.builder().id("4").build());
        list.add(Teacher.builder().id("5").build());
        list.add(Teacher.builder().id("6").build());
        list.add(Teacher.builder().id("7").build());
        ImmutableMap<String, Student> map = Maps.uniqueIndex(assembleObject(), Student::getId);
        list = list.stream().map(e -> {
            Student student1 = map.get(e.getId());
            if (!Objects.isNull(student1)){
                BeanUtils.copyProperties(student1, e);
            }
            return e;
        }).collect(Collectors.toList());
        System.out.println(list.toString());
    }

    /**
     * list转map
     */
    public static void convert(){
        ImmutableMap<String, Student> map = Maps.uniqueIndex(assembleObject(), Student::getId);
        System.out.println(map);
    }

    public static List<Student> assembleObject(){
        List<Student> list = Lists.newArrayList();
        list.add(new Student("1", "ha"));
        list.add(new Student("2", "he"));
        list.add(new Student("3", "hr"));
        list.add(new Student("4", "ht"));
        list.add(new Student("5", "hy"));
        return list;
    }
}
