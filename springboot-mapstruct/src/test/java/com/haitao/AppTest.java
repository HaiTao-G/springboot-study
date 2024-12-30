package com.haitao;

import com.alibaba.fastjson.JSON;
import com.haitao.pojo.entity.SysUserEntity;
import com.haitao.pojo.vo.SysUserVo;
import io.github.linpeilie.Converter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.activation.DataHandler;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AppTest {

    @Autowired
    private Converter converter;

    @Test
    public void test() {
        SysUserEntity source = SysUserEntity.builder().userId("2").userName("laoma").build();
        SysUserVo target = converter.convert(source, SysUserVo.class);
        System.out.println(JSON.toJSONString(target));
    }
}