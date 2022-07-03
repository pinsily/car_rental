package com.pinsily.car_rental.service;

import com.google.common.collect.Lists;
import com.pinsily.car_rental.OrderStatusEnum;
import com.pinsily.car_rental.dao.domain.Car;
import com.pinsily.car_rental.dao.domain.Order;
import com.pinsily.car_rental.daologic.CarDaoLogic;
import com.pinsily.car_rental.logic.CarRentalLogic;
import com.pinsily.car_rental.logic.RentalTransLogic;
import com.pinsily.car_rental.service.bean.ResBean;
import com.pinsily.car_rental.service.request.CarListQueryReq;
import com.pinsily.car_rental.service.request.CarRentalReq;
import com.pinsily.car_rental.service.request.RentalRecordQueryReq;
import com.pinsily.car_rental.service.request.ReturnCarReq;
import com.pinsily.car_rental.service.response.CarListQueryResp;
import com.pinsily.car_rental.service.response.RentalRecordQueryResp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarRentalServiceTest {

    @InjectMocks
    private CarRentalLogic carRentalLogic;

    @Mock
    private CarDaoLogic carDaoLogic;
    @Mock
    private RentalTransLogic rentalTransLogic;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void queryCarList() {

        when(carDaoLogic.queryCarList(any())).thenReturn(Lists.newArrayList(buildCarInfo()));

        CarListQueryResp resp = carRentalLogic.queryCarList(new CarListQueryReq());

        assertEquals(1, resp.getCarList().size());

    }

    @Test
    void rentalCarWithInvalidParam() {

        CarRentalReq req = new CarRentalReq();
        req.setUid(1);

        ResBean resBean = carRentalLogic.rentalCar(req);

        assertEquals(Integer.valueOf(-1), resBean.getRetCode());
        assertEquals("car id should exist!", resBean.getRetMsg());

    }

    @Test
    void rentalCarWithCarNotExist() {

        CarRentalReq req = new CarRentalReq();
        req.setUid(1);
        req.setCarIndex(1);

        when(carDaoLogic.queryCarById(any())).thenReturn(null);

        ResBean resBean = carRentalLogic.rentalCar(req);

        assertEquals(Integer.valueOf(-1), resBean.getRetCode());
        assertEquals("car not exist!", resBean.getRetMsg());

    }

    @Test
    void rentalCarWithStockNotEnough() {

        CarRentalReq req = new CarRentalReq();
        req.setUid(1);
        req.setCarIndex(1);

        Car car = buildCarInfo();
        car.setStock(0);
        when(carDaoLogic.queryCarById(any())).thenReturn(car);

        ResBean resBean = carRentalLogic.rentalCar(req);

        assertEquals(Integer.valueOf(-1), resBean.getRetCode());
        assertEquals("car stock not enough!", resBean.getRetMsg());

    }

    @Test
    void rentalCarFail() {

        CarRentalReq req = new CarRentalReq();
        req.setUid(1);
        req.setCarIndex(1);

        when(carDaoLogic.queryCarById(any())).thenReturn(buildCarInfo());
        when(rentalTransLogic.rentalCarTrans(any(), any())).thenReturn(false);

        ResBean resBean = carRentalLogic.rentalCar(req);

        assertEquals(Integer.valueOf(-1), resBean.getRetCode());
        assertEquals("rent car fail, please try again!", resBean.getRetMsg());

    }

    @Test
    void rentalCarSucc() {

        CarRentalReq req = new CarRentalReq();
        req.setUid(1);
        req.setCarIndex(1);

        when(carDaoLogic.queryCarById(any())).thenReturn(buildCarInfo());
        when(rentalTransLogic.rentalCarTrans(any(), any())).thenReturn(true);

        ResBean resBean = carRentalLogic.rentalCar(req);

        assertEquals(Integer.valueOf(0), resBean.getRetCode());
        assertEquals("rental car success", resBean.getRetMsg());

    }

    @Test
    void queryRentalRecord() {

        when(carDaoLogic.queryOrderList(any())).thenReturn(Lists.newArrayList(buildOrder()));

        RentalRecordQueryResp resp = carRentalLogic.queryRentalRecord(new RentalRecordQueryReq());

        assertEquals(1, resp.getRecordList().size());

    }

    @Test
    void returnCarWithInvalidParam() {

        ReturnCarReq req = new ReturnCarReq();
        req.setUid(1);
        req.setRecordId(null);

        ResBean resBean = carRentalLogic.returnCar(req);

        assertEquals(Integer.valueOf(-1), resBean.getRetCode());
        assertEquals("order id should exist!", resBean.getRetMsg());

    }

    @Test
    void returnCarWithOrderNotExist() {

        ReturnCarReq req = new ReturnCarReq();
        req.setUid(1);
        req.setRecordId(1);

        when(carDaoLogic.queryOrderById(any())).thenReturn(null);

        ResBean resBean = carRentalLogic.returnCar(req);

        assertEquals(Integer.valueOf(-1), resBean.getRetCode());
        assertEquals("order not exist!", resBean.getRetMsg());

    }

    @Test
    void returnCarWithOrderUidNotEqual() {

        ReturnCarReq req = new ReturnCarReq();
        req.setUid(1);
        req.setRecordId(1);

        Order order = buildOrder();
        order.setUid(2);
        when(carDaoLogic.queryOrderById(any())).thenReturn(order);

        ResBean resBean = carRentalLogic.returnCar(req);

        assertEquals(Integer.valueOf(-1), resBean.getRetCode());
        assertEquals("this order does not belong to you!", resBean.getRetMsg());

    }

    @Test
    void returnCarWithCarNotExist() {

        ReturnCarReq req = new ReturnCarReq();
        req.setUid(1);
        req.setRecordId(1);

        Order order = buildOrder();
        order.setUid(1);
        when(carDaoLogic.queryOrderById(any())).thenReturn(order);
        when(carDaoLogic.queryCarById(any())).thenReturn(null);

        ResBean resBean = carRentalLogic.returnCar(req);

        assertEquals(Integer.valueOf(-1), resBean.getRetCode());
        assertEquals("car info not exist!", resBean.getRetMsg());

    }

    @Test
    void returnCarFail() {

        ReturnCarReq req = new ReturnCarReq();
        req.setUid(1);
        req.setRecordId(1);

        Order order = buildOrder();
        order.setUid(1);
        when(carDaoLogic.queryOrderById(any())).thenReturn(order);
        when(carDaoLogic.queryCarById(any())).thenReturn(buildCarInfo());
        when(rentalTransLogic.returnCarTrans(any(), any())).thenReturn(false);

        ResBean resBean = carRentalLogic.returnCar(req);

        assertEquals(Integer.valueOf(-1), resBean.getRetCode());
        assertEquals("return car fail, please try again!", resBean.getRetMsg());

    }

    @Test
    void returnCarSucc() {

        ReturnCarReq req = new ReturnCarReq();
        req.setUid(1);
        req.setRecordId(1);

        Order order = buildOrder();
        order.setUid(1);
        when(carDaoLogic.queryOrderById(any())).thenReturn(order);
        when(carDaoLogic.queryCarById(any())).thenReturn(buildCarInfo());
        when(rentalTransLogic.returnCarTrans(any(), any())).thenReturn(true);

        ResBean resBean = carRentalLogic.returnCar(req);

        assertEquals(Integer.valueOf(0), resBean.getRetCode());
        assertEquals("return car success!", resBean.getRetMsg());

    }


    private Car buildCarInfo() {
        Car car = new Car();
        car.setIndex(1);
        car.setVersion(0);
        car.setModel("BMM 256");
        car.setStock(10);
        return car;
    }

    private Order buildOrder() {

        Order order = new Order();
        order.setStatus(OrderStatusEnum.RENTING.getStatus());
        order.setCarModel("BMM 256");
        order.setUid(1);
        order.setCarId(1);
        order.setIndex(1);
        order.setStartTime(new Date());
        return order;

    }
}