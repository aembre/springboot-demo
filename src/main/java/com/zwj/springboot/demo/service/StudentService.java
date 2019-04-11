package com.zwj.springboot.demo.service;

import com.zwj.springboot.demo.model.Student;

public interface StudentService {
    int add(Student student);
    int update(Student student);
    int deleteBysno(String sno);
    Student queryStudentBySno(String sno);
}
