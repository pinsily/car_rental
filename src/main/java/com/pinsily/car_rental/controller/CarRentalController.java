package com.pinsily.car_rental.controller;

import com.alibaba.fastjson.JSONObject;
import com.pinsily.car_rental.service.CarRentalService;
import com.pinsily.car_rental.service.bean.ResBean;
import com.pinsily.car_rental.service.request.CarListQueryReq;
import com.pinsily.car_rental.service.request.CarRentalReq;
import com.pinsily.car_rental.service.request.RentalRecordQueryReq;
import com.pinsily.car_rental.service.response.CarListQueryResp;
import com.pinsily.car_rental.service.response.RentalRecordQueryResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ResBean rentalCar(@RequestBody CarRentalReq req) {
        log.info("CarRentalReq: {}", JSONObject.toJSONString(req));
        return carRentalService.rentalCar(req);
    }

    /**
     * query user rental record list
     */
    @ResponseBody
    @RequestMapping(value = "/queryRentalRecord.json", method = RequestMethod.GET)
    public RentalRecordQueryResp queryRentalRecord(RentalRecordQueryReq req) {
        return carRentalService.queryRentalRecord(req);
    }


    /**
     * return car
     */
    @ResponseBody
    @RequestMapping(value = "/returnCar.json", method = RequestMethod.POST)
    public RentalRecordQueryResp returnCar(RentalRecordQueryReq req) {
        return carRentalService.queryRentalRecord(req);
    }

}
