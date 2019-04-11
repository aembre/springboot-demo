package com.zwj.springboot.demo.dao;

import com.zwj.springboot.demo.model.Student;

public interface StudentDao {

    int add(Student student);

    int update(Student student);

    int deleteBysno(String sno);

    Student queryStudentBySno(String sno);

}
