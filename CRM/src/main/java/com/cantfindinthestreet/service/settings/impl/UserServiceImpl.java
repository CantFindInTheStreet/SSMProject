package com.cantfindinthestreet.service.settings.impl;

import com.cantfindinthestreet.bean.settings.User;
import com.cantfindinthestreet.dao.settings.UserMapper;
import com.cantfindinthestreet.service.settings.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public User selectUserBynp(Map<String, Object> map) {
        return userMapper.selectUserBynp(map);
    }

    @Override
    public List<User> selectAllUser() {
        return userMapper.selectAllUser();
    }
}
