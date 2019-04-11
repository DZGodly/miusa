package pers.fairy.miusa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.fairy.miusa.mapper.GoodsMapper;
import pers.fairy.miusa.vo.GoodsVO;

/**
 * @author ：DZGodly
 * @date ：Created in 2019/4/7 15:39
 */
@Service
public class GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    public boolean reduceStock(Long goodsId) {
        return goodsMapper.reduceStock(goodsId) > 0;
    }

}
