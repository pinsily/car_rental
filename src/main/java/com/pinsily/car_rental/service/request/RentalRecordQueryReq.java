package com.pinsily.car_rental.service.request;

import lombok.Data;

@Data
public class RentalRecordQueryReq {

    /**
     * user id
     * */
    private Integer uid;

    /**
     * car index
     * */
    private Integer carIndex;

}
