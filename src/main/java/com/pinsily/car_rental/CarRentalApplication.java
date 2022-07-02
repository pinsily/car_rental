package com.pinsily.car_rental;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// exclude = {DataSourceAutoConfiguration.class} 不考虑数据库链接
@SpringBootApplication()
@MapperScan("com.pinsily.car_rental.dao.mappers")
public class CarRentalApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarRentalApplication.class, args);
    }

}
