package com.zachary.optimisticlock.service;

import com.zachary.optimisticlock.entity.GoodsInfo;

public interface IGoodsInfoService {
    //   查询库存
    long findNumByGoodsId(String goodsId);

    //    进行更新库存
    boolean updateNumber(String goodsId, int buys);
}
