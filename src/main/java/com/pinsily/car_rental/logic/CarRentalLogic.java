package com.pinsily.car_rental.logic;

import com.pinsily.car_rental.dao.domain.Car;
import com.pinsily.car_rental.daologic.CarDaoLogic;
import com.pinsily.car_rental.service.bean.ResBean;
import com.pinsily.car_rental.service.request.CarListQueryReq;
import com.pinsily.car_rental.service.request.CarRentalReq;
import com.pinsily.car_rental.service.request.RentalRecordQueryReq;
import com.pinsily.car_rental.service.response.CarListQueryResp;
import com.pinsily.car_rental.service.response.RentalRecordQueryResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
public class CarRentalLogic {

    @Autowired
    private CarDaoLogic carDaoLogic;

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
        return ResBean.buildSuccRes("rental car success");
    }

    /**
     * query user rental record list
     */
    public RentalRecordQueryResp queryRentalRecord(RentalRecordQueryReq req) {
        return null;
    }

}
