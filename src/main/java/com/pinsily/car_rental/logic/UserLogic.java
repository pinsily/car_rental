package com.pinsily.car_rental.logic;

import com.pinsily.car_rental.dao.domain.User;
import com.pinsily.car_rental.daologic.UserDaoLogic;
import com.pinsily.car_rental.service.bean.ResBean;
import com.pinsily.car_rental.service.request.UserLoginReq;
import com.pinsily.car_rental.service.request.UserRegistReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Slf4j
@Component
public class UserLogic {

    @Autowired
    private UserDaoLogic userDaoLogic;

    /**
     * user regist
     *
     * @param req
     * @return
     */
    public ResBean regist(UserRegistReq req) {

        log.info("regist: " + req);

        // param checking
        try {
            Assert.notNull(req.getUsername(), "username can not be empty!");
            Assert.notNull(req.getPassword(), "password can not be empty!");
            Assert.notNull(req.getRepassword(), "repassword can not be empty!");
            Assert.isTrue(req.getPassword().equals(req.getRepassword()), "The passwords are not the same!");

        } catch (IllegalArgumentException e) {
            return ResBean.buildFailRes(e.getMessage());
        }

        User existUser = userDaoLogic.queryUserByName(req.getUsername());
        if (null != existUser) {
            return ResBean.buildFailRes("The user name is already in use!");
        }

        User register = new User();
        register.setUsername(req.getUsername());
        register.setPassword(req.getPassword());

        boolean registRes = userDaoLogic.insert(register);

        return registRes ? ResBean.buildSuccRes("regist success!")
                : ResBean.buildFailRes("regist fail, please try again later!");

    }

    /**
     * user login
     *
     * @param req
     * @return
     */
    public ResBean login(UserLoginReq req) {

        log.info("login: " + req);

        // param checking
        try {
            Assert.notNull(req.getUsername(), "username can not be empty!");
            Assert.notNull(req.getPassword(), "password can not be empty!");

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResBean.buildFailRes(e.getMessage());
        }

        User existUser = userDaoLogic.queryUserByName(req.getUsername());
        if (null == existUser) {
            return ResBean.buildFailRes("The account does not exist!");
        }

        if (!req.getPassword().equals(existUser.getPassword())) {
            return ResBean.buildFailRes("The account or password is incorrect!");
        }
        ResBean succRes = ResBean.buildSuccRes("login success!");
        succRes.setUser(existUser);
        return succRes;
    }

}
