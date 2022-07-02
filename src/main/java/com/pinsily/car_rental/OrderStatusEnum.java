package com.pinsily.car_rental;

/**
 * record status enum
 * */
public enum OrderStatusEnum {
    RENTING(1, "renting"),
    RETURN(2, "return"),
    ;

    private Integer status;
    private String desc;

    OrderStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
