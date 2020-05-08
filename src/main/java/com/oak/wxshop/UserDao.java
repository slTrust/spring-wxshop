package com.oak.wxshop;

import com.oak.wxshop.generate.User;
import com.oak.wxshop.generate.UserExample;
import com.oak.wxshop.generate.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDao {
    private final SqlSessionFactory sqlSessionFactory;
    /*
    sqlSessionFactory 是从哪里来的？
    答案是 它是从 mybatis-spring-boot-starter 里自动引入进来的
     */

    @Autowired
    public UserDao(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void insertUser(User user) {
        try(SqlSession sqlSession = sqlSessionFactory.openSession(true)){
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            int a = mapper.insert(user);
            System.out.println(a);
        }
    }

    public User getUserByTel(String tel) {
        try(SqlSession sqlSession = sqlSessionFactory.openSession(true)){
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            UserExample example = new UserExample();
            example.createCriteria().andTelEqualTo(tel);
            return mapper.selectByExample(example).get(0);
        }
    }
}
