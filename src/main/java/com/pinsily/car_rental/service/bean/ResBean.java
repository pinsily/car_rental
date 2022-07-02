package com.pinsily.car_rental.service.bean;

import com.pinsily.car_rental.dao.domain.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResBean {

    private Integer retCode = 0;

    private String retMsg = "OK";

    /**
     * userinfo
     * */
    private User user;

    public ResBean(Integer retCode, String retMsg) {
        this.retCode = retCode;
        this.retMsg = retMsg;
    }

    /**
     * fail res
     * */
    public static ResBean buildFailRes(String retMsg) {
        return new ResBean(-1, retMsg);
    }

    /**
     * success res
     * */
    public static ResBean buildSuccRes(String retMsg) {
        return new ResBean(0, retMsg);
    }

}
