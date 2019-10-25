package com.sevendays.controller;

import com.github.pagehelper.PageHelper;
import com.sevendays.pojo.ItemTable;
import com.sevendays.pojo.Msg;
import com.sevendays.pojo.UserTable;
import com.sevendays.service.ItemTableService;
import com.sevendays.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

/**
 * @author liwen
 * @Title: UserController
 * @Description: 用户调用接口
 * @date 2019/10/25 / 20:43
 */

@Controller
public class UserController {

    private static Logger LOG = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;
    @Autowired
    ItemTableService itemTableService;


    /**
     * 分页查询
     *
     * @param pn
     * @return
     */
    @RequestMapping("/userfind")
    @ResponseBody
    public List<UserTable> findUser(@RequestParam(value = "page", defaultValue = "1") Integer pn) {
        PageHelper.startPage(pn, 5);
        List<UserTable> UserInfo = userService.findAllList();
        return UserInfo;
    }


    @GetMapping("/findinfo")
    @ResponseBody
    public List<UserTable> findUser(UserTable userInfo) {
        List<UserTable> UserInfo = userService.findinfo(userInfo);
        return UserInfo;
    }


    @PostMapping("/findinfo")
    @ResponseBody
    public List<UserTable> findUserPost(UserTable userInfo) {
        List<UserTable> UserInfo = userService.findinfo(userInfo);
        return UserInfo;
    }

    /**
     * json请求
     *
     * @param userInfo
     * @return
     */
    @PostMapping("/findinfoJson")
    @ResponseBody
    public List<UserTable> findUserPostJson(@RequestBody UserTable userInfo) {
        List<UserTable> UserInfo = userService.findinfo(userInfo);
        return UserInfo;
    }


    /**
     * 根据相关人员名称查询项目信息
     *
     * @param userInfo
     * @return
     */
    @GetMapping("/findName")
    @ResponseBody
    public Msg findItem(UserTable userInfo) {
        String userName = userInfo.getUserName();
        if (!StringUtils.isEmpty(userName)) {
            List<ItemTable> UserInfo = itemTableService.findItemInfo(userName.trim());
            return Msg.success().add("user", UserInfo);
        }
        return Msg.fail().add("userName", "请求失败缺失参数");
    }


}
