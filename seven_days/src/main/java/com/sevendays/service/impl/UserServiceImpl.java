package com.sevendays.service.impl;

import com.sevendays.mapper.UserTableMapper;
import com.sevendays.pojo.UserTable;
import com.sevendays.pojo.UserTableExample;
import com.sevendays.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liwen
 * @Title: UserServiceImpl
 * @Description:
 * @date 2019/10/25 / 20:47
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserTableMapper userTableMapper;

    @Override
    public List<UserTable> findAllList() {
        return userTableMapper.selectByExample(null);
    }

    @Override
    public List<UserTable> findinfo(UserTable userInfo) {
        UserTableExample example = new UserTableExample();
        UserTableExample.Criteria criteria = example.createCriteria();
        criteria.andUserNameLike(userInfo.getUserName());
        return userTableMapper.selectByExample(example);
    }
}
