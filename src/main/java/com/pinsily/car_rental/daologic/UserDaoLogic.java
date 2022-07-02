package com.pinsily.car_rental.daologic;

import com.pinsily.car_rental.dao.domain.User;
import com.pinsily.car_rental.dao.domain.UserExample;
import com.pinsily.car_rental.dao.mappers.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Component
public class UserDaoLogic {

    @Autowired
    private UserMapper userMapper;

    public User queryUserByName(String username) {

        if (StringUtils.isEmpty(username)) {
            return null;
        }

        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(username);

        List<User> userList = userMapper.selectByExample(example);

        return CollectionUtils.isEmpty(userList) ? null : userList.get(0);

    }

    public boolean insert(User register) {
        Date now = new Date();
        register.setCreateTime(now);
        register.setModifyTime(now);
        register.setVersion(0);

        int result = userMapper.insertSelective(register);

        return result > 0;

    }
}
