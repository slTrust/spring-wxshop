package com.oak.wxshop.service;

import com.oak.wxshop.generate.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserLoginInterceptor implements HandlerInterceptor {

    private UserService userService;

    public UserLoginInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 返回false 请求就被拦住了
        Object tel = SecurityUtils.getSubject().getPrincipal();
        if (tel != null) {
            // 说明已经登录了,把这个User放入当前线程的上下文里，同时当请求结束的时候把这个上下文清除
            User user = userService.getUserByTel(tel.toString());
            UserContext.setCurrentUser(user);
        } else {

        }
        System.out.println("pre");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 非常非常重要，因为线程是会被复用的
        // 如果线程1中保存用户A的信息，且没有清理
        // 下次线程1再次处理别的请求的时候，就会出现"串号"的的情况
        UserContext.setCurrentUser(null);
        System.out.println("Post");
    }
}
