package com.pinsily.car_rental.service.request;

import lombok.Data;

@Data
public class UserRegistReq {

    private String username;

    private String password;

    private String repassword;

}
