package com.oak.wxshop.service;

import com.oak.wxshop.generate.User;

public class UserContext {
    // 每次请求就是一个线程，通过 ThreadLocal 保证每次请求的那个线程的上下文中的 User都是同一个
    private static ThreadLocal<User> currentUser = new ThreadLocal<>();

    public static User getCurrentUser() {
        return currentUser.get();
    }

    public static void setCurrentUser(User user) {
        currentUser.set(user);
    }

    public static void clearCurrentUser() {
        currentUser.remove();
    }
}
