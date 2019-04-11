package pers.fairy.miusa.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pers.fairy.miusa.entity.OrderInfo;
import pers.fairy.miusa.entity.OrderInfoExample;
import pers.fairy.miusa.vo.OrderDetailVO;

public interface OrderInfoMapper {
    @Select("select oi.*, g.goods_img from " +
            "order_info oi left join goods g on g.id = oi.goods_id")
    OrderDetailVO selectOrderDetailByOrderId(Long orderId);

    long countByExample(OrderInfoExample example);

    int deleteByExample(OrderInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OrderInfo record);

    int insertSelective(OrderInfo record);

    List<OrderInfo> selectByExample(OrderInfoExample example);

    OrderInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OrderInfo record, @Param("example") OrderInfoExample example);

    int updateByExample(@Param("record") OrderInfo record, @Param("example") OrderInfoExample example);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);

}