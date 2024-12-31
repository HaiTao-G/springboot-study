package com.haitao.entity;

import cn.idev.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class User {

    @ExcelProperty(value = "${user.name}")
    private String name;

    @ExcelProperty(value = "${user.age}")
    private int age;
}
