package com.pinsily.car_rental.daologic;

import com.pinsily.car_rental.dao.domain.Car;
import com.pinsily.car_rental.dao.domain.CarExample;
import com.pinsily.car_rental.dao.domain.Order;
import com.pinsily.car_rental.dao.domain.OrderExample;
import com.pinsily.car_rental.dao.mappers.CarMapper;
import com.pinsily.car_rental.dao.mappers.OrderMapper;
import com.pinsily.car_rental.service.request.CarListQueryReq;
import com.pinsily.car_rental.service.request.RentalRecordQueryReq;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class CarDaoLogic {

    @Autowired
    private CarMapper carMapper;
    @Autowired
    private OrderMapper orderMapper;


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

        example.setOrderByClause("`findex` desc");

        return carMapper.selectByExample(example);
    }

    public Car queryCarById(Integer carIndex) {
        if (null == carIndex) {
            return null;
        }
        return carMapper.selectByPrimaryKey(carIndex);
    }

    /**
     * update car stock
     */
    public boolean update(Car car) {

        CarExample example = new CarExample();
        CarExample.Criteria criteria = example.createCriteria();

        criteria.andFindexEqualTo(car.getIndex());
        criteria.andVersionEqualTo(car.getVersion());

        car.setModifyTime(new Date());
        car.setVersion(car.getVersion() + 1);

        int res = carMapper.updateByExample(car, example);

        return res > 0;
    }

    /**
     * insert record
     */
    public boolean insertOrder(Order order) {

        int res = orderMapper.insertSelective(order);

        return res > 0;
    }

    /**
     * query order list
     */
    public List<Order> queryOrderList(RentalRecordQueryReq req) {

        OrderExample example = new OrderExample();
        OrderExample.Criteria criteria = example.createCriteria();

        if (null != req.getUid()) {
            criteria.andUidEqualTo(req.getUid());
        }
        if (null != req.getCarIndex()) {
            criteria.andCarIdEqualTo(req.getCarIndex());
        }

        example.setOrderByClause("`findex` desc");

        return orderMapper.selectByExample(example);
    }

    public Order queryOrderById(Integer recordId) {

        if (null == recordId) {
            return null;
        }

        return orderMapper.selectByPrimaryKey(recordId);

    }

    public boolean updateOrder(Order existOrder) {

        OrderExample example = new OrderExample();
        OrderExample.Criteria criteria = example.createCriteria();

        criteria.andFindexEqualTo(existOrder.getIndex());

        int res = orderMapper.updateByExample(existOrder, example);

        return res > 0;

    }
}
