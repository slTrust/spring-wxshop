package com.oak.wxshop.service;

import com.oak.wxshop.dao.UserDao;
import com.oak.wxshop.generate.User;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User createUserIfNotExist(String tel) {
        User user = new User();
        user.setTel(tel);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        try {
            userDao.insertUser(user);
        } catch (DuplicateKeyException e) {
            return userDao.getUserByTel(tel);
        }
        return user;
        /*
        // 不能这样，要考虑并发情况
        if(user不存在){
            insertUser
        }
         */
    }

    /**
     * 根据电话返回用户，如果用户不存在返回 null
     *
     * @param tel
     * @return 返回用户
     */
    public Optional<User> getUserByTel(String tel) {
        return Optional.ofNullable(userDao.getUserByTel(tel));
    }
}
