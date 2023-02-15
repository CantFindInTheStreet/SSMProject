package com.cantfindinthestreet.service.settings;

import com.cantfindinthestreet.bean.settings.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User selectUserBynp(Map<String,Object> map);
    List<User> selectAllUser();
}
