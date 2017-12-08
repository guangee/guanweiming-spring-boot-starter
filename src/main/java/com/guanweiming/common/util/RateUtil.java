package com.guanweiming.common.util;

import java.util.List;
import java.util.Random;

/**
 * @author https://github.com/zziaguan/
 */
public class RateUtil<T> {
    public static class RateItem<T> {
        private int rate;
        private T item;

        public RateItem(int rate, T item) {
            this.rate = rate;
            this.item = item;
        }

        public int getRate() {
            return rate;
        }

        public void setRate(int rate) {
            this.rate = rate;
        }

        public T getItem() {
            return item;
        }

        public void setItem(T item) {
            this.item = item;
        }
    }

    public T getRate(List<RateItem<T>> list) throws Exception {
        if (list == null || list.size() == 0) {
            throw new Exception("列表数据不能为空");
        }
        int sumRates = 0;
        for (RateItem<T> item : list) {
            if (item.getRate() < 0) {
                throw new Exception("概率值不能为空");
            }
            sumRates += item.getRate();
        }
        int randomNum=new Random().nextInt(sumRates)+1;
        for (int i = 0; i < list.size(); i++) {
            if(randomNum<=list.get(i).getRate()){
                return list.get(i).getItem();
            }
            randomNum = randomNum - list.get(i).getRate();
        }
        /*永远不会走到这一步*/
        throw new Exception("算法出错了");
    }
}
