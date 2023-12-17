package com.example.mapper;

import com.example.pojo.Goods;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface GoodsMapper {

    /**
     * 根据id选择商品
     */
    @Select("SELECT * FROM goods WHERE id = #{id}")
    Goods getGoodsById(int id);

    /**
     * 选择所有商品
     */
    @Select("SELECT * FROM goods")
    List<Goods> getAllGoods();

    /**
     * 向数据库中插入商品
     */
    @Insert("INSERT INTO goods (name, description, price, stock) " +
            "VALUES (#{name}, #{description}, #{price}, #{stock})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertGoods(Goods goods);

    /**
     * 更新商品情况（根据id）
     */
    @Update("UPDATE goods SET name = #{name}, description = #{description}, " +
            "price = #{price}, stock = #{stock} WHERE id = #{id}")
    void updateGoods(Goods goods);

    @Update("UPDATE goods SET stock = #{new_stock} WHERE id = #{id}")
    void updateGoodsStock(int id, int new_stock);
    /**
     * 删除商品
     */
    @Delete("DELETE FROM goods WHERE id = #{id}")
    void deleteGoods(@Param("id") Integer id);
}
