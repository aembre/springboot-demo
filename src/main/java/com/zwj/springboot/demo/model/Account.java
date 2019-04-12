package com.zwj.springboot.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Account {
    private String account;
    private String name;
    private String password;
    private String accountType;
    private String tel;
}
