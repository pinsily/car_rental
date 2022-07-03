package com.pinsily.car_rental.service;

import com.pinsily.car_rental.dao.domain.User;
import com.pinsily.car_rental.daologic.UserDaoLogic;
import com.pinsily.car_rental.logic.UserLogic;
import com.pinsily.car_rental.service.bean.ResBean;
import com.pinsily.car_rental.service.request.UserLoginReq;
import com.pinsily.car_rental.service.request.UserRegistReq;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserLogic userLogic = new UserLogic();

    @Mock
    private UserDaoLogic userDaoLogic;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void registWithInvalidParams() {

        UserRegistReq req = new UserRegistReq();
        req.setUsername("pinsily");
        req.setPassword("123");
        req.setRepassword("1234");

        ResBean resBean = userLogic.regist(req);

        assertEquals(resBean.getRetCode(), Integer.valueOf(-1));
        assertEquals(resBean.getRetMsg(), "The passwords are not the same!");
    }

    @Test
    void registWithExistUser() {

        UserRegistReq req = new UserRegistReq();
        req.setUsername("pinsily");
        req.setPassword("123");
        req.setRepassword("123");

        when(userDaoLogic.queryUserByName(any())).thenReturn(new User());

        ResBean resBean = userLogic.regist(req);

        assertEquals(resBean.getRetCode(), Integer.valueOf(-1));
        assertEquals(resBean.getRetMsg(), "The user name is already in use!");

    }

    @Test
    void registFail() {

        UserRegistReq req = new UserRegistReq();
        req.setUsername("pinsily");
        req.setPassword("123");
        req.setRepassword("123");

        when(userDaoLogic.queryUserByName(any())).thenReturn(null);
        when(userDaoLogic.insert(any())).thenReturn(false);

        ResBean resBean = userLogic.regist(req);

        assertEquals(resBean.getRetCode(), Integer.valueOf(-1));
        assertEquals(resBean.getRetMsg(), "regist fail, please try again later!");

    }

    @Test
    void registSucc() {

        UserRegistReq req = new UserRegistReq();
        req.setUsername("pinsily");
        req.setPassword("123");
        req.setRepassword("123");

        when(userDaoLogic.queryUserByName(any())).thenReturn(null);
        when(userDaoLogic.insert(any())).thenReturn(true);

        ResBean resBean = userLogic.regist(req);

        assertEquals(resBean.getRetCode(), Integer.valueOf(0));
        assertEquals(resBean.getRetMsg(), "regist success!");

    }

    @Test
    void loginWithInvalidParams() {

        UserLoginReq req = new UserLoginReq();
        req.setUsername("pinsily");
        req.setPassword(null);

        ResBean resBean = userLogic.login(req);

        assertEquals(resBean.getRetCode(), Integer.valueOf(-1));
        assertEquals(resBean.getRetMsg(), "password can not be empty!");

    }

    @Test
    void loginWithUserNotExist() {

        UserLoginReq req = new UserLoginReq();
        req.setUsername("pinsily");
        req.setPassword("pinsily96");

        when(userDaoLogic.queryUserByName(any())).thenReturn(null);

        ResBean resBean = userLogic.login(req);

        assertEquals(resBean.getRetCode(), Integer.valueOf(-1));
        assertEquals(resBean.getRetMsg(), "The account does not exist!");

    }

    @Test
    void loginWithInvalidPwd() {

        UserLoginReq req = new UserLoginReq();
        req.setUsername("pinsily");
        req.setPassword("pinsily96");

        User user = new User();
        user.setPassword("pinsily");
        when(userDaoLogic.queryUserByName(any())).thenReturn(user);

        ResBean resBean = userLogic.login(req);

        assertEquals(resBean.getRetCode(), Integer.valueOf(-1));
        assertEquals(resBean.getRetMsg(), "The account or password is incorrect!");

    }

    @Test
    void login() {

        UserLoginReq req = new UserLoginReq();
        req.setUsername("pinsily");
        req.setPassword("pinsily96");

        User user = new User();
        user.setPassword("pinsily96");
        when(userDaoLogic.queryUserByName(any())).thenReturn(user);

        ResBean resBean = userLogic.login(req);

        assertEquals(resBean.getRetCode(), Integer.valueOf(0));
        assertEquals(resBean.getRetMsg(), "login success!");

    }

}