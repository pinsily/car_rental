package com.pinsily.car_rental.controller;

import com.alibaba.fastjson.JSONObject;
import com.pinsily.car_rental.service.CarRentalService;
import com.pinsily.car_rental.service.bean.ResBean;
import com.pinsily.car_rental.service.request.CarListQueryReq;
import com.pinsily.car_rental.service.request.CarRentalReq;
import com.pinsily.car_rental.service.request.RentalRecordQueryReq;
import com.pinsily.car_rental.service.request.ReturnCarReq;
import com.pinsily.car_rental.service.response.CarListQueryResp;
import com.pinsily.car_rental.service.response.RentalRecordQueryResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/car")
public class CarRentalController {

    @Autowired
    private CarRentalService carRentalService;

    /**
     * query car model list
     */
    @ResponseBody
    @RequestMapping(value = "/queryCarList.json", method = RequestMethod.GET)
    public CarListQueryResp queryCarList(CarListQueryReq req) {
        return carRentalService.queryCarList(req);
    }

    /**
     * user rental car
     */
    @ResponseBody
    @RequestMapping(value = "/rentalCar.json", method = RequestMethod.POST)
    public ResBean rentalCar(@RequestBody CarRentalReq req, HttpServletRequest request) {
        log.info("CarRentalReq: {}", JSONObject.toJSONString(req));

        Integer uid = (Integer) request.getSession().getAttribute("uid");
        if (null == uid) {
            return ResBean.buildFailRes("You are not login, please login first");
        }
        req.setUid(uid);
        return carRentalService.rentalCar(req);
    }

    /**
     * query user rental record list
     */
    @ResponseBody
    @RequestMapping(value = "/queryRentalRecord.json", method = RequestMethod.GET)
    public RentalRecordQueryResp queryRentalRecord(RentalRecordQueryReq req, HttpServletRequest request) {

        log.info("RentalRecordQueryReq: {}", JSONObject.toJSONString(req));

        Integer uid = (Integer) request.getSession().getAttribute("uid");
        if (null == uid) {
            return new RentalRecordQueryResp();
        }
        req.setUid(uid);
        return carRentalService.queryRentalRecord(req);
    }


    /**
     * return car
     */
    @ResponseBody
    @RequestMapping(value = "/returnCar.json", method = RequestMethod.POST)
    public ResBean returnCar(@RequestBody ReturnCarReq req, HttpServletRequest request) {

        log.info("ReturnCarReq: {}", JSONObject.toJSONString(req));

        Integer uid = (Integer) request.getSession().getAttribute("uid");
        if (null == uid) {
            return ResBean.buildFailRes("You are not login, please login first");
        }
        req.setUid(uid);
        return carRentalService.returnCar(req);
    }

}
