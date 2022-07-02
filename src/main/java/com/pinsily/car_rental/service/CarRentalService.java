package com.pinsily.car_rental.service;

import com.pinsily.car_rental.service.bean.ResBean;
import com.pinsily.car_rental.service.request.CarListQueryReq;
import com.pinsily.car_rental.service.request.CarRentalReq;
import com.pinsily.car_rental.service.request.RentalRecordQueryReq;
import com.pinsily.car_rental.service.response.CarListQueryResp;
import com.pinsily.car_rental.service.response.RentalRecordQueryResp;

public interface CarRentalService {

    /**
     * query car model list
     * */
    CarListQueryResp queryCarList(CarListQueryReq req);

    /**
     * user rental car
     * */
    ResBean rentalCar(CarRentalReq req);

    /**
     * query user rental record list
     * */
    RentalRecordQueryResp queryRentalRecord(RentalRecordQueryReq req);

}
