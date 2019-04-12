package com.zwj.springboot.demo.service.impl;

import com.zwj.springboot.demo.cache.RedisService;
import com.zwj.springboot.demo.mapper.StudentMapper;
import com.zwj.springboot.demo.model.Student;
import com.zwj.springboot.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("studentService")
public class StudentServiceImp implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public int add(Student student) {
        return this.studentMapper.add(student);
    }

    @Override
    public int update(Student student) {
        return this.studentMapper.update(student);
    }

    @Override
    public int deleteBysno(String sno) {
        return this.studentMapper.deleteBysno(sno);
    }

    @Override
    public Student queryStudentBySno(String sno) {
        return this.studentMapper.queryStudentBySno(sno);
        //使用redis缓存
        /*String key = "queryStudentBySno" + ":" + sno;
        Object obj = redisService.get(key);
        if(obj != null){
            return (Student) obj;
        }else{
            Student student = this.studentMapper.queryStudentBySno(sno);
            redisService.set(key, student,60L);
            return student;
        }*/
    }
}
