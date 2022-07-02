package com.pinsily.car_rental.service.request;

import lombok.Data;

@Data
public class CarRentalReq {

    /**
     * car id
     * */
    private Integer carIndex;

    /**
     * user id
     * */
    private Integer uid;

}
