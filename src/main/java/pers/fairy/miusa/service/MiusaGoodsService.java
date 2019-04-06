package pers.fairy.miusa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.fairy.miusa.mapper.MiusaGoodsMapper;
import pers.fairy.miusa.vo.GoodsVO;

import java.util.List;

/**
 * @author ：DZGodly
 * @date ：Created in 2019/4/6 13:22
 */
@Service
public class MiusaGoodsService {
    @Autowired
    MiusaGoodsMapper miusaGoodsMapper;

    public List<GoodsVO> listMiusaGoods() {
        return miusaGoodsMapper.selectMiusaGoods();
    }
}
