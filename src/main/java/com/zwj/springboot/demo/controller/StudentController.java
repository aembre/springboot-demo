package com.zwj.springboot.demo.controller;

import com.zwj.springboot.demo.annotation.Log;
import com.zwj.springboot.demo.dao.StudentDao;
import com.zwj.springboot.demo.model.Student;
import com.zwj.springboot.demo.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * Swagger常用注解
 * @Api：修饰整个类，描述Controller的作用；
 *
 * @ApiOperation：描述一个类的一个方法，或者说一个接口；
 *
 * @ApiParam：单个参数描述；
 *
 * @ApiModel：用对象来接收参数；
 *
 * @ApiProperty：用对象接收参数时，描述对象的一个字段；
 *
 * @ApiResponse：HTTP响应其中1个描述；
 *
 * @ApiResponses：HTTP响应整体描述；
 *
 * @ApiIgnore：使用该注解忽略这个API；
 *
 * @ApiError ：发生错误返回的信息；
 *
 * @ApiImplicitParam：一个请求参数；
 *
 * @ApiImplicitParams：多个请求参数。
 */
@Api("学生Controller")
@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentDao studentDao;

    @ApiOperation(value = "获取用户信息", notes = "根据用户id获取用户信息")
    @Log("查询用户信息")
    @RequestMapping( value = "/student/{sno}", method = RequestMethod.GET)
    public Student queryStudentBySno(@PathVariable String sno) {
        return this.studentService.queryStudentBySno(sno);
//        return studentDao.queryStudentBySno(sno);
    }

}
