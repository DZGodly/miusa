package pers.fairy.miusa.vo;


import pers.fairy.miusa.entity.User;

public class GoodsDetailVO {
    private long remainSeconds;
    private GoodsVO goods;
    private User user;

    public long getRemainSeconds() {
        return remainSeconds;
    }

    public void setRemainSeconds(long remainSeconds) {
        this.remainSeconds = remainSeconds;
    }

    public GoodsVO getGoods() {
        return goods;
    }

    public void setGoods(GoodsVO goods) {
        this.goods = goods;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
