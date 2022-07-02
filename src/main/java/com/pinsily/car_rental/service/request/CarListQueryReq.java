package com.pinsily.car_rental.service.request;

import lombok.Data;

@Data
public class CarListQueryReq {

    /**
     * model condition
     */
    private String model;

    /**
     * stock condition
     */
    private Integer stockStart;

    /**
     * stock condition
     */
    private Integer stockEnd;

    /**
     * page condition
     */
    private Integer offset = 0;

    /**
     * page condition
     * */
    private Integer limit = 10;

}
