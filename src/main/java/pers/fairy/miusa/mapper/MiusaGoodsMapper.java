package pers.fairy.miusa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pers.fairy.miusa.entity.Goods;
import pers.fairy.miusa.entity.MiusaGoods;
import pers.fairy.miusa.entity.MiusaGoodsExample;
import pers.fairy.miusa.vo.GoodsVO;

public interface MiusaGoodsMapper {

    @Select("select g.*, mg.miusa_price, mg.stock_count, mg.start_date, mg.end_date from " +
            "goods g left join miusa_goods mg on g.id = mg.goods_id")
    List<GoodsVO> selectMiusaGoods();

    @Select("select g.*, mg.miusa_price, mg.stock_count, mg.start_date, mg.end_date from " +
            "goods g left join miusa_goods mg on g.id = mg.goods_id where g.id = #{goodsId}")
    GoodsVO selectMiusaGoodsByGoodsId(Long goodsId);

    @Select("select id from miusa_goods  mg where mg.goods_id = #{goodsId}")
    MiusaGoods verifyMiusaGoodsByGoodsId(Long goodsId);

    long countByExample(MiusaGoodsExample example);

    int deleteByExample(MiusaGoodsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MiusaGoods record);

    int insertSelective(MiusaGoods record);

    List<MiusaGoods> selectByExample(MiusaGoodsExample example);

    MiusaGoods selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MiusaGoods record, @Param("example") MiusaGoodsExample example);

    int updateByExample(@Param("record") MiusaGoods record, @Param("example") MiusaGoodsExample example);

    int updateByPrimaryKeySelective(MiusaGoods record);

    int updateByPrimaryKey(MiusaGoods record);

}