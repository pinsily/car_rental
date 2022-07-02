package com.pinsily.car_rental.service.impl;

import com.pinsily.car_rental.logic.UserLogic;
import com.pinsily.car_rental.service.UserService;
import com.pinsily.car_rental.service.bean.ResBean;
import com.pinsily.car_rental.service.request.UserLoginReq;
import com.pinsily.car_rental.service.request.UserRegistReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserLogic userLogic;

    /**
     * user regist
     *
     * @param req
     * @return
     */

    @Override
    public ResBean regist(UserRegistReq req) {
        return userLogic.regist(req);
    }

    /**
     * user login
     *
     * @param req
     * @return
     */
    @Override
    public ResBean login(UserLoginReq req) {
        return userLogic.login(req);
    }
}
