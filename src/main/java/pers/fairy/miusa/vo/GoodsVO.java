package pers.fairy.miusa.vo;

import pers.fairy.miusa.entity.Goods;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ：DZGodly
 * @date ：Created in 2019/4/6 13:17
 */
public class GoodsVO extends Goods {
    private BigDecimal miusaPrice;

    private Integer stockCount;

    private Date startDate;

    private Date endDate;

    public BigDecimal getMiusaPrice() {
        return miusaPrice;
    }

    public void setMiusaPrice(BigDecimal miusaPrice) {
        this.miusaPrice = miusaPrice;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
