package com.oak.wxshop.entity;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

public class Response<T> {
    private String message;
    private T data;

    public static <T> Response<T> of(String message, T data) {
        return new Response<T>(message, data);
    }

    public static <T> Response<T> of(T data) {
        return new Response<T>(null, data);
    }

    public Response() {
    }

    @SuppressFBWarnings("URF_UNREAD_FIELD")
    public Response(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
