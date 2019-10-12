package com.test.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.test.entity.Student;

import javax.transaction.Transactional;

public interface StudentRepository extends JpaRepository<Student, Integer>{
    /**
     * 自定义sql，更新删除操作jpa要求加上事务
     */
    @Transactional
    @Modifying
    @Query("delete from Student ")
    void delete();

    @Query("select s from Student s where id = ?1")
    Student selectById(int id);
}
