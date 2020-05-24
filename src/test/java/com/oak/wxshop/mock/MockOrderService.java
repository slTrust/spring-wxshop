package com.oak.wxshop.mock;

import com.oak.wxshop.api.OrderService;
import org.apache.dubbo.config.annotation.Service;

@Service(version = "${wxshop.orderservice.version}")
public class MockOrderService implements OrderService {

    @Override
    public void placeOrder(int goodsId, int number) {
        System.out.println("I'm mock!");
    }
}
