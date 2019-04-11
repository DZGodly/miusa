package pers.fairy.miusa.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pers.fairy.miusa.entity.MiusaOrder;
import pers.fairy.miusa.entity.MiusaOrderExample;

public interface MiusaOrderMapper {
    long countByExample(MiusaOrderExample example);

    int deleteByExample(MiusaOrderExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MiusaOrder record);

    int insertSelective(MiusaOrder record);

    List<MiusaOrder> selectByExample(MiusaOrderExample example);

    MiusaOrder selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MiusaOrder record, @Param("example") MiusaOrderExample example);

    int updateByExample(@Param("record") MiusaOrder record, @Param("example") MiusaOrderExample example);

    int updateByPrimaryKeySelective(MiusaOrder record);

    int updateByPrimaryKey(MiusaOrder record);
}