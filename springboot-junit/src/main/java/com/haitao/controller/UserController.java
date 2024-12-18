package com.haitao.controller;


import com.haitao.entity.UserInfo;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    /**
     * 获取用户信息
     * RESTful接口
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public UserInfo getUserInfo(@PathVariable("id") int userId) {
        UserInfo userInfo = new UserInfo();
        return userInfo;
    }

    /**
     * 根据用户ID，获取用户信息
     */
    @RequestMapping("/getUserInfoById")
    public UserInfo getUserInfoById(@RequestParam(value = "user_id", defaultValue = "0") int userId) {
        UserInfo userInfo = new UserInfo();
        return userInfo;
    }

    /**
     * 新增用户信息
     * 参数：接收对象型参数
     */
    @RequestMapping(value = "/addUserByEntity", method = RequestMethod.POST)
    public UserInfo addUserByEntity(@RequestBody UserInfo userInfo) {
        userInfo = new UserInfo();
        return userInfo;
    }

    /**
     * 新增用户信息
     * 参数：接收多个参数
     */
    @RequestMapping(value = "/addUserByParam", method = RequestMethod.POST)
    public UserInfo addUserByParam(String userName, String blogUrl, String blogRemark) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(userName);
        userInfo.setBlogUrl(blogUrl);
        userInfo.setBlogRemark(blogRemark);
        return userInfo;
    }
}
