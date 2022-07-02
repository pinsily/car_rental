package com.pinsily.car_rental.daologic;

import com.pinsily.car_rental.dao.domain.Car;
import com.pinsily.car_rental.dao.domain.CarExample;
import com.pinsily.car_rental.dao.mappers.CarMapper;
import com.pinsily.car_rental.service.request.CarListQueryReq;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CarDaoLogic {

    @Autowired
    private CarMapper carMapper;


    public List<Car> queryCarList(CarListQueryReq req) {

        CarExample example = new CarExample();
        CarExample.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotEmpty(req.getModel())) {
            criteria.andModelLike("%" + req.getModel() + "%");
        }
        if (null != req.getStockStart()) {
            criteria.andStockGreaterThanOrEqualTo(req.getStockStart());
        }
        if (null != req.getStockEnd()) {
            criteria.andStockLessThanOrEqualTo(req.getStockEnd());
        }

        example.setOrderByClause("`index` desc");

        return carMapper.selectByExample(example);
    }
}
