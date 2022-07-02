package com.pinsily.car_rental.service.response;

import com.google.common.collect.Lists;
import com.pinsily.car_rental.dao.domain.Car;
import lombok.Data;

import java.util.List;

@Data
public class CarListQueryResp {

    /**
     * car list
     * */
    private List<Car> carList = Lists.newArrayList();

    /**
     * total car number
     * */
    private Long total = 0L;

}
