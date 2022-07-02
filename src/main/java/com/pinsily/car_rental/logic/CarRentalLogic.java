package com.pinsily.car_rental.logic;

import com.alibaba.fastjson.JSONObject;
import com.pinsily.car_rental.dao.domain.Car;
import com.pinsily.car_rental.dao.domain.Order;
import com.pinsily.car_rental.daologic.CarDaoLogic;
import com.pinsily.car_rental.service.bean.ResBean;
import com.pinsily.car_rental.service.request.CarListQueryReq;
import com.pinsily.car_rental.service.request.CarRentalReq;
import com.pinsily.car_rental.service.request.RentalRecordQueryReq;
import com.pinsily.car_rental.service.request.ReturnCarReq;
import com.pinsily.car_rental.service.response.CarListQueryResp;
import com.pinsily.car_rental.service.response.RentalRecordQueryResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Component
public class CarRentalLogic {

    @Autowired
    private CarDaoLogic carDaoLogic;
    @Autowired
    private RentalTransLogic rentalTransLogic;

    /**
     * query car model list
     */
    public CarListQueryResp queryCarList(CarListQueryReq req) {

        CarListQueryResp resp = new CarListQueryResp();

        // todo page
        List<Car> carList = carDaoLogic.queryCarList(req);

        if (CollectionUtils.isEmpty(carList)) {
            return resp;
        }

        resp.setCarList(carList);
        return resp;
    }

    /**
     * user rental car
     */
    public ResBean rentalCar(CarRentalReq req) {

        log.info("rental car req: {}", JSONObject.toJSONString(req));

        try {
            Assert.notNull(req.getUid(), "should login first!");
            Assert.notNull(req.getCarIndex(), "car id should exist!");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResBean.buildFailRes(e.getMessage());
        }

        // query car by id
        Car car = carDaoLogic.queryCarById(req.getCarIndex());
        if (null == car) {
            return ResBean.buildFailRes("car not exist!");
        }
        // stock verify
        if (car.getStock().compareTo(0) <= 0) {
            return ResBean.buildFailRes("car stock not enough!");
        }

        // transaction update
        boolean updateRes = rentalTransLogic.rentalCarTrans(req, car);
        if (!updateRes) {
            return ResBean.buildFailRes("rent car fail, please try again!");
        }

        return ResBean.buildSuccRes("rental car success");

    }


    /**
     * query user rental record list
     */
    public RentalRecordQueryResp queryRentalRecord(RentalRecordQueryReq req) {

        RentalRecordQueryResp resp = new RentalRecordQueryResp();

        // todo page
        List<Order> carList = carDaoLogic.queryOrderList(req);

        if (CollectionUtils.isEmpty(carList)) {
            return resp;
        }

        resp.setRecordList(carList);
        return resp;
    }

    public ResBean returnCar(ReturnCarReq req) {

        try {
            Assert.notNull(req.getUid(), "should login first!");
            Assert.notNull(req.getRecordId(), "order id should exist!");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResBean.buildFailRes(e.getMessage());
        }

        Order existOrder = carDaoLogic.queryOrderById(req.getRecordId());
        if (null == existOrder) {
            return ResBean.buildFailRes("order not exist!");
        }

        if (!existOrder.getUid().equals(req.getUid())) {
            return ResBean.buildFailRes("this order does not belong to you!");
        }

        Car car = carDaoLogic.queryCarById(existOrder.getCarId());
        if (null == car) {
            return ResBean.buildFailRes("car info not exist!");
        }

        boolean res = rentalTransLogic.returnCarTrans(existOrder, car);
        if (!res) {
            return ResBean.buildFailRes("return car fail, please try again!");
        }
        return ResBean.buildSuccRes("return car success!");
    }
}
