package com.oak.wxshop.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.kevinsawicki.http.HttpRequest;
import com.oak.wxshop.WxshopApplication;
import com.oak.wxshop.entity.LoginResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static com.oak.wxshop.service.TelVerificationServiceTest.VALID_PARAMETER;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_OK;

@ExtendWith(SpringExtension.class) // 通过这个可以使用 spring相关的功能
@SpringBootTest(classes = WxshopApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 选择随机端口
@TestPropertySource(locations = "classpath:application.yml")
public class AuthIntegrationTest extends AbstractIntegrationTest{

    @Test
    public void loginLogoutTest() throws JsonProcessingException {
        String sessionId = loginAndGetCookie();

        // 带着cookie访问 /api/status 处于登录状态
        String statusResponse = doHttpRequest("/api/v1/status", "GET", null, sessionId).body;
        LoginResponse response = objectMapper.readValue(statusResponse, LoginResponse.class);
        Assertions.assertTrue(response.isLogin());
        Assertions.assertEquals(VALID_PARAMETER.getTel(), response.getUser().getTel());


        // 调用 /api/logout
        // 注销登录 ,此时需要带上cookie 不然不知道谁是注销的
        doHttpRequest("/api/v1/logout", "POST", null, sessionId);

        // 再次带着Cookie访问 /api/status 恢复成为未登录状态
        statusResponse = doHttpRequest("/api/v1/status", "GET", null, sessionId).body;
        response = objectMapper.readValue(statusResponse, LoginResponse.class);
        Assertions.assertFalse(response.isLogin());

    }

    @Test
    public void returnHttpOKWhenParameterIsCorrect() throws JsonProcessingException {
        int responseCode = HttpRequest.post(getUrl("/api/v1/code"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .send(objectMapper.writeValueAsString(VALID_PARAMETER))
                .code();
        Assertions.assertEquals(HTTP_OK, responseCode);
    }

    @Test
    public void returnHttpBadRequestWhenParameterIsCorrect() throws JsonProcessingException {
        int responseCode = HttpRequest.post(getUrl("/api/v1/code"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .send(objectMapper.writeValueAsString(TelVerificationServiceTest.EMPTY_TEL))
                .code();

        Assertions.assertEquals(HTTP_BAD_REQUEST, responseCode);
    }

}

