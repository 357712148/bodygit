package com.websocket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author liwen
 * @Title: WebSockController
 * @Description: mywebsocker
 * @date 2019/12/9 / 13:43
 */
@Controller
@RequestMapping("/checkcenter")
public class WebSockController {

    @GetMapping("/")
    public String index() {
        return "index";
    }


}
