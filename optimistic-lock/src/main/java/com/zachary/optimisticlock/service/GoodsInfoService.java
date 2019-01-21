package com.zachary.optimisticlock.service;

import com.zachary.optimisticlock.mapper.GoodsInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GoodsInfoService implements IGoodsInfoService {
    @Autowired
    private GoodsInfoMapper goodsInfoMapper;


    @Override
    public long findNumByGoodsId(String goodsId) {
        return goodsInfoMapper.findNumByGoodsId(goodsId).getNumber();
    }

    @Override
    public boolean updateNumber(String goodsId, int buys) {

        long number = findNumByGoodsId(goodsId);

        if (number < buys) {
            return false;
        }
        if (goodsInfoMapper.updateNumberByStat(goodsId, buys) > 0) {
            return true;
        }
        //等一下
        threadWait();

        return updateNumber(goodsId, buys);
    }

    public void threadWait() {
        Random random = new Random();
        try {
            Thread.sleep(random.nextInt(10) + 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
