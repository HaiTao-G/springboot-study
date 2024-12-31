package com.haitao.controller;

import cn.idev.excel.util.FileUtils;
import com.alibaba.fastjson.JSON;
import com.haitao.entity.User;
import com.haitao.excel.ExcelUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class ExcelController {

    @GetMapping("/test")
    public String test(){
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setAge(i);
            user.setName("name"+i);
            users.add(user);
        }
        ExcelUtils.exoprtByPath(users,User.class,"E:/1.xlsx");
        return "success";
    }

    @GetMapping("/test1")
    @SneakyThrows
    public String test1(){
        FileInputStream fileInputStream = FileUtils.openInputStream(new File("E:/1.xlsx"));
        List<User> read = ExcelUtils.read(fileInputStream, User.class);
        log.info("read:{}", JSON.toJSONString(read));
        return "success";
    }
}
