package com.pinsily.car_rental.service.response;

import com.pinsily.car_rental.dao.domain.Order;
import lombok.Data;

import java.util.List;

@Data
public class RentalRecordQueryResp {

    private List<Order> recordList;

    private Long total;

}
