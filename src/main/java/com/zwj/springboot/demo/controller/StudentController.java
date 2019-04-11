package com.zwj.springboot.demo.controller;

import com.zwj.springboot.demo.annotation.Log;
import com.zwj.springboot.demo.dao.StudentDao;
import com.zwj.springboot.demo.model.Student;
import com.zwj.springboot.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentDao studentDao;

    @Log("查询用户信息")
    @RequestMapping( value = "/student/{sno}", method = RequestMethod.GET)
    public Student queryStudentBySno(@PathVariable String sno) {
        return this.studentService.queryStudentBySno(sno);
//        return studentDao.queryStudentBySno(sno);
    }

}
