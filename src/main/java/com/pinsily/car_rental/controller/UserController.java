package com.pinsily.car_rental.controller;

import com.alibaba.fastjson.JSONObject;
import com.pinsily.car_rental.service.UserService;
import com.pinsily.car_rental.service.bean.ResBean;
import com.pinsily.car_rental.service.request.UserLoginReq;
import com.pinsily.car_rental.service.request.UserRegistReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * register
     *
     * @param req regist req
     * @return code and msg
     */
    @ResponseBody
    @RequestMapping(value = "/regist.json", method = RequestMethod.POST)
    public ResBean regist(@RequestBody UserRegistReq req, HttpServletRequest request) {
        log.info("request: " + JSONObject.toJSONString(request.getSession()));
        return userService.regist(req);
    }

    /**
     * 登录
     *
     * @param req login req
     * @return Recode and msg
     */
    @ResponseBody
    @RequestMapping(value = "/login.json", method = RequestMethod.POST)
    public ResBean login(@RequestBody UserLoginReq req, HttpServletRequest request) {
        ResBean resBean = userService.login(req);
        log.info("login res: {}", JSONObject.toJSONString(resBean));
        // add uid username in session
        if (Integer.valueOf(0).equals(resBean.getRetCode())) {
            request.getSession().setAttribute("uid", resBean.getUser().getIndex());
            request.getSession().setAttribute("username", resBean.getUser().getUsername());
        }

        log.info("uid: " + JSONObject.toJSONString(request.getSession().getAttribute("uid")));
        log.info("username: " + JSONObject.toJSONString(request.getSession().getAttribute("username")));

        resBean.setUser(null);
        return resBean;
    }

    @ResponseBody
    @RequestMapping(value = "/isLogin.json", method = RequestMethod.GET)
    public ResBean isLogin(HttpServletRequest request) {
        log.info("login status request: " + JSONObject.toJSONString(request.getSession().getAttributeNames()));

        Integer uid = (Integer) request.getSession().getAttribute("uid");
        if (null == uid) {
            return ResBean.buildFailRes("not login");
        }
        return ResBean.buildSuccRes("logged in");
    }

    @ResponseBody
    @RequestMapping(value = "/logout.json", method = RequestMethod.POST)
    public ResBean logout(HttpServletRequest request) {
        log.info("logout status request: " + JSONObject.toJSONString(request.getSession().getAttributeNames()));

        Integer uid = (Integer) request.getSession().getAttribute("uid");
        if (null == uid) {
            return ResBean.buildFailRes("You are not login");
        }

        request.getSession().setAttribute("uid", null);
        request.getSession().setAttribute("username", null);

        return ResBean.buildSuccRes("logout success");
    }
}
