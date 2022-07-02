package com.pinsily.car_rental.service.impl;

import com.pinsily.car_rental.logic.CarRentalLogic;
import com.pinsily.car_rental.service.CarRentalService;
import com.pinsily.car_rental.service.bean.ResBean;
import com.pinsily.car_rental.service.request.CarListQueryReq;
import com.pinsily.car_rental.service.request.CarRentalReq;
import com.pinsily.car_rental.service.request.RentalRecordQueryReq;
import com.pinsily.car_rental.service.response.CarListQueryResp;
import com.pinsily.car_rental.service.response.RentalRecordQueryResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("carRentalService")
public class CarRentalServiceImpl implements CarRentalService {

    @Autowired
    private CarRentalLogic carRentalLogic;

    /**
     * query car model list
     *
     * @param req
     */
    @Override
    public CarListQueryResp queryCarList(CarListQueryReq req) {
        return carRentalLogic.queryCarList(req);
    }

    /**
     * user rental car
     *
     * @param req
     */
    @Override
    public ResBean rentalCar(CarRentalReq req) {
        return carRentalLogic.rentalCar(req);
    }

    /**
     * query user rental record list
     *
     * @param req
     */
    @Override
    public RentalRecordQueryResp queryRentalRecord(RentalRecordQueryReq req) {
        return carRentalLogic.queryRentalRecord(req);
    }
}
