package com.oak.wxshop.generate;

import com.oak.wxshop.generate.Goods;
import com.oak.wxshop.generate.GoodsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GoodsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GOODS
     *
     * @mbg.generated Mon May 11 14:22:38 CST 2020
     */
    long countByExample(GoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GOODS
     *
     * @mbg.generated Mon May 11 14:22:38 CST 2020
     */
    int deleteByExample(GoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GOODS
     *
     * @mbg.generated Mon May 11 14:22:38 CST 2020
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GOODS
     *
     * @mbg.generated Mon May 11 14:22:38 CST 2020
     */
    int insert(Goods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GOODS
     *
     * @mbg.generated Mon May 11 14:22:38 CST 2020
     */
    int insertSelective(Goods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GOODS
     *
     * @mbg.generated Mon May 11 14:22:38 CST 2020
     */
    List<Goods> selectByExampleWithBLOBs(GoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GOODS
     *
     * @mbg.generated Mon May 11 14:22:38 CST 2020
     */
    List<Goods> selectByExample(GoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GOODS
     *
     * @mbg.generated Mon May 11 14:22:38 CST 2020
     */
    Goods selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GOODS
     *
     * @mbg.generated Mon May 11 14:22:38 CST 2020
     */
    int updateByExampleSelective(@Param("record") Goods record, @Param("example") GoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GOODS
     *
     * @mbg.generated Mon May 11 14:22:38 CST 2020
     */
    int updateByExampleWithBLOBs(@Param("record") Goods record, @Param("example") GoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GOODS
     *
     * @mbg.generated Mon May 11 14:22:38 CST 2020
     */
    int updateByExample(@Param("record") Goods record, @Param("example") GoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GOODS
     *
     * @mbg.generated Mon May 11 14:22:38 CST 2020
     */
    int updateByPrimaryKeySelective(Goods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GOODS
     *
     * @mbg.generated Mon May 11 14:22:38 CST 2020
     */
    int updateByPrimaryKeyWithBLOBs(Goods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GOODS
     *
     * @mbg.generated Mon May 11 14:22:38 CST 2020
     */
    int updateByPrimaryKey(Goods record);
}