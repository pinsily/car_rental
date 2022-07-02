package com.pinsily.car_rental.service.response;

import com.google.common.collect.Lists;
import com.pinsily.car_rental.dao.domain.Order;
import lombok.Data;

import java.util.List;

@Data
public class RentalRecordQueryResp {

    private List<Order> recordList = Lists.newArrayList();

    private Long total = 0L;

}
