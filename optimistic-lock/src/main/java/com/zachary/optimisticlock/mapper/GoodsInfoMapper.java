package com.zachary.optimisticlock.mapper;


import com.zachary.optimisticlock.entity.GoodsInfo;
import org.apache.ibatis.annotations.Param;

public interface GoodsInfoMapper {

    //   查询库存
    GoodsInfo findNumByGoodsId(String goodsId);

    //    进行更新库存

    int updateNumber(@Param("goodsId") String goodsId, @Param("buys") int buys);

    int updateNumberByVersion(@Param("goodsId") String goodsId, @Param("buys") int buys, @Param("version") int version);

    int updateNumberByStat(@Param("goodsId") String goodsId, @Param("buys") int buys);

}
