package com.haitao.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haitao.entity.UserInfo;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTest {

    @BeforeAll
    static void setup() {
        System.out.println("Running setup before all tests...");
    }

    @AfterAll
    static void tearDown() {
        System.out.println("Running teardown after all tests...");
    }

    @BeforeEach
    void init() {
        System.out.println("Running setup before each test...");
        //使用上下文构建MockMvc
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    void cleanup() {
        System.out.println("Running cleanup after each test...");
    }


    @DisplayName("哈哈test")
    @Test
    void test() {
        System.out.println("Running test...");
        assertEquals(5, 2 + 3);
    }

    @Disabled
    @Test
    void disabledTest() {
        System.out.println("Running test...");
    }

    @RepeatedTest(5)
    void repeatedTest() {
        System.out.println("1");
    }

    @Test
    void assertTest() {
        assertEquals(10, 2 + 3);
        assertNotEquals(10, 2 + 3);
    }

    @Test
    void failTest() {
        if (true) {
            fail("Unexpected condition occurred");
        }
    }

    //启用Web上下文
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;


    @Test
    void getUserInfo() throws Exception {
        //执行请求（使用GET请求，RESTful接口）
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/hello/{name}", "haha").accept(MediaType.APPLICATION_JSON_UTF8)).andReturn();

        //获取返回编码
        int status = mvcResult.getResponse().getStatus();

        //获取返回结果
        String content = mvcResult.getResponse().getContentAsString();

        //断言，判断返回编码是否正确
        assertEquals(200, status);

        //将JSON转换为对象
        ObjectMapper mapper = new ObjectMapper();
        UserInfo userInfo = mapper.readValue(content, UserInfo.class);

        //打印结果
        System.out.println("用户ID：" + userInfo.getId());
        System.out.println("用户姓名：" + userInfo.getUserName());
        System.out.println("博客地址：" + userInfo.getBlogUrl());
        System.out.println("博客信息：" + userInfo.getBlogRemark());
    }

    @Test
    void getUserInfoById() {
    }

    @Test
    void addUserByEntity() {
    }

    @Test
    void addUserByParam() {
    }
}