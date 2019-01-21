package com.zachary.optimisticlock.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class GoodsInfoServiceTest {

    //参与秒杀活动的用户人数
    private static final int USER_NUM = 1000;
    //产品唯一ID
    private static final String GOODS_ID = "1";
    //产品的库存数量
    private static final int GOODS_LEFT = 100;
    //发令枪，用于模拟高并发
    private static CountDownLatch countDownLatch = new CountDownLatch(USER_NUM);
    //计数器，用于记录成功购买客户人数
    private static int successPerson = 0;
    //计数器，用于记录卖出去的商品个数
    private static int saleOutNum = 0;

    //注入的产品服务接口
    @Resource(name = "goodsInfoService")
    private IGoodsInfoService goodsInfoService;

    //乐观锁实现秒杀测试方法，模拟高并发测试
    @Test
    public void testPurchase() throws InterruptedException {
        //循环实例化USER_NUM个并发请求（线程）
        for (int i = 0; i < USER_NUM; i++) {
            //模拟用户请求，每个人买3个商品
            new Thread(new UserRequst(GOODS_ID, 3)).start();
            if (i == USER_NUM) {
                Thread.currentThread().sleep(1000);
            }
            countDownLatch.countDown();//倒计时器减一
        }
        Thread.currentThread().sleep(3000);
        System.out.println("成功购买商品的顾客人数为：" + successPerson);
        System.out.println("已售出商品个数为：" + saleOutNum);
        System.out.println("剩余商品个数为：" + goodsInfoService.findNumByGoodsId(GOODS_ID));
    }

    //内部类继承线程接口，用于模拟用户请求
    public class UserRequst implements Runnable {
        private String id;//购买商品的ID
        private int buys;//购买数量

        public UserRequst(String id, int buys) {
            this.id = id;
            this.buys = buys;
        }

        @Override
        public void run() {
            try {
                countDownLatch.await();//所有子线程运行到这里休眠，等待发令枪指令
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//			//如果更新库存成功，同步修改购买成功的人数以及商品个数
            boolean updateGoodsAmount = goodsInfoService.updateNumber(id, buys);
            if (updateGoodsAmount) {
                synchronized (countDownLatch) {
                    successPerson++;
                    saleOutNum += buys;
                }
            }
        }
    }

}