package com.zwj.springboot.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Student implements Serializable {
    private static final long serialVersionUID = -5287881841340590679L;
    private String sno;
    private String name;
    private String sex;
}
