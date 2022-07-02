package com.pinsily.car_rental.service;

import com.pinsily.car_rental.service.bean.ResBean;
import com.pinsily.car_rental.service.request.UserLoginReq;
import com.pinsily.car_rental.service.request.UserRegistReq;

public interface UserService {

    /**
     * user regist
     * @param req
     * @return
     */
    ResBean regist(UserRegistReq req);

    /**
     * user login
     * @param req
     * @return
     */
    ResBean login(UserLoginReq req);



}
