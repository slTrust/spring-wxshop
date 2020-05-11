package com.oak.wxshop.dao;

import com.oak.wxshop.entity.DataStatus;
import com.oak.wxshop.generate.Goods;
import com.oak.wxshop.generate.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsDao {
    private final GoodsMapper goodsMapper;

    @Autowired
    public GoodsDao(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }

    public Goods insertGoods(Goods goods) {
        long id = goodsMapper.insert(goods);
        goods.setId(id);
        return goods;
    }

    public Goods deleteGoodsById(Long goodsId) {
        Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
        if (goods == null) {
            throw new ResourceNotFoundException("商品未找到！");
        }

        goods.setStatus(DataStatus.DELETE_STATUS);
        goodsMapper.updateByPrimaryKey(goods);
        return goods;
    }

    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }
}
