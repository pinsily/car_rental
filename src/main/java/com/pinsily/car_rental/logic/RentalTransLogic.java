package com.pinsily.car_rental.logic;

import com.pinsily.car_rental.OrderStatusEnum;
import com.pinsily.car_rental.dao.domain.Car;
import com.pinsily.car_rental.dao.domain.Order;
import com.pinsily.car_rental.daologic.CarDaoLogic;
import com.pinsily.car_rental.service.request.CarRentalReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
public class RentalTransLogic {

    @Autowired
    private CarDaoLogic carDaoLogic;

    @Transactional
    public boolean rentalCarTrans(CarRentalReq req, Car car) {

        try {
            // update stock
            car.setStock(car.getStock() - 1);

            boolean updateRes = carDaoLogic.update(car);
            if (!updateRes) {
                throw new RuntimeException("update car fail!");
            }

            // insert record
            Order order = new Order();
            order.setCarId(car.getIndex());
            order.setCarModel(car.getModel());
            order.setUid(req.getUid());
            order.setStartTime(new Date());
            order.setStatus(OrderStatusEnum.RENTING.getStatus());

            boolean insertRes = carDaoLogic.insertOrder(order);
            if (!insertRes) {
                throw new RuntimeException("insert order fail!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Transactional
    public boolean returnCarTrans(Order existOrder, Car car) {

        try {
            // update stock
            car.setStock(car.getStock() + 1);
            boolean updateRes = carDaoLogic.update(car);
            if (!updateRes) {
                throw new RuntimeException("update stock fail");
            }
            // update order state
            existOrder.setEndTime(new Date());
            existOrder.setStatus(OrderStatusEnum.RETURN.getStatus());

            updateRes = carDaoLogic.updateOrder(existOrder);
            if (!updateRes) {
                throw new RuntimeException("update order status fail");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
