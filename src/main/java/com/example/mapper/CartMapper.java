package com.example.mapper;

import com.example.pojo.Cart;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.type.LocalDateTimeTypeHandler;

import java.util.List;

@Mapper
public interface CartMapper {
    @Insert("INSERT INTO cart (user_id, goods_id, quantity)" +
            "VALUES (#{user_id}, #{goods_id}, #{quantity})")
    int insertCart(Cart cart);

    @Update("UPDATE cart SET quantity=#{quantity} WHERE id=#{id}")
    void updateCart(@Param("cart_id") int cart_id,@Param("quantity") int quantity);

    @Delete("DELETE FROM cart WHERE id = #{id}")
    void deleteCart(int cart_id);

    @Delete("DELETE FROM cart WHERE user_id = #{user_id}")
    void deleteCartByUserId(int user_id);

    @Select("SELECT * FROM cart WHERE user_id = #{user_id}")
    @Results({
            @Result(property = "user_id", column = "user_id"),
            @Result(property = "goods_id", column = "goods_id"),
    })
    List<Cart> getCartByUserId(@Param("user_id") int user_id);
}
