package com.oak.wxshop.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kevinsawicki.http.HttpRequest;
import com.oak.wxshop.entity.LoginResponse;
import com.oak.wxshop.generate.User;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Map;

import static com.oak.wxshop.service.TelVerificationServiceTest.VALID_PARAMETER;
import static com.oak.wxshop.service.TelVerificationServiceTest.VALID_PARAMETER_CODE;
import static java.net.HttpURLConnection.HTTP_OK;

public class AbstractIntegrationTest {
    @Autowired
    Environment environment;

    @Value("${spring.datasource.url}")
    private String databaseUrl;
    @Value("${spring.datasource.username}")
    private String databaseUsername;
    @Value("${spring.datasource.password}")
    private String databasePassword;

    @BeforeEach
    public void initDatabase() {
        // 在每个测试开始前，执行一次flyway:clean flyway:migrate
        ClassicConfiguration conf = new ClassicConfiguration();
        conf.setDataSource(databaseUrl, databaseUsername, databasePassword);
        Flyway flyway = new Flyway(conf);
        flyway.clean();
        flyway.migrate();
    }

    public ObjectMapper objectMapper = new ObjectMapper();

    public String getUrl(String apiName) {
        // 获取集成测试的端口号
        return "http://localhost:" + environment.getProperty("local.server.port") + apiName;
    }

    public UserLoginResponse loginAndGetCookie() throws JsonProcessingException {
        // 最开始默认情况下，访问/api/status 处于未登录状态
        String statusResponse = doHttpRequest("/api/v1/status", "GET", null, null).body;
        LoginResponse statusResponseData = objectMapper.readValue(statusResponse, LoginResponse.class);
        Assertions.assertFalse(statusResponseData.isLogin());

        // 发送验证码
        int responseCode = doHttpRequest("/api/v1/code", "POST", VALID_PARAMETER, null).code;
        Assertions.assertEquals(HTTP_OK, responseCode);

        // 带着验证码进行登录，得到Cookie
        HttpResponse loginResponse = doHttpRequest("/api/v1/login", "POST", VALID_PARAMETER_CODE, null);
        List<String> setCookie = loginResponse.headers.get("Set-Cookie");
        String cookie = getSessionIdFromSetCookie(setCookie.stream().filter(c -> c.contains("JSESSIONID"))
                .findFirst()
                .get());

        statusResponse = doHttpRequest("/api/v1/status", "GET", null, cookie).body;
        statusResponseData = objectMapper.readValue(statusResponse, LoginResponse.class);

        return new UserLoginResponse(cookie, statusResponseData.getUser());
    }

    protected String getSessionIdFromSetCookie(String setCookie) {
        // JSESSIONID=7c647c1a-373e-49e4-9412-9bd998bcfa1b; Path=/; HttpOnly; SameSite=lax -> JSESSIONID=7c647c1a-373e-49e4-9412-9bd998bcfa1b
        int semiColonIndex = setCookie.indexOf(";");
        return setCookie.substring(0, semiColonIndex);
    }

    public static class UserLoginResponse {
        String cookie;
        User user;

        public UserLoginResponse(String cookie, User user) {
            this.cookie = cookie;
            this.user = user;
        }
    }

    public static class HttpResponse {
        int code;
        String body;
        Map<String, List<String>> headers;

        HttpResponse(int code, String body, Map<String, List<String>> headers) {
            this.code = code;
            this.body = body;
            this.headers = headers;
        }
    }

    public HttpResponse doHttpRequest(String apiName, String httpMethod, Object requestBody, String cookie) throws JsonProcessingException {
        HttpRequest request = new HttpRequest(getUrl(apiName), httpMethod);
        if (cookie != null) {
            request.header("Cookie", cookie);
        }
        request.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE);

        if (requestBody != null) {
            request.send(objectMapper.writeValueAsString(requestBody));
        }

        return new HttpResponse(request.code(), request.body(), request.headers());
    }
}

