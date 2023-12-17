package com.example.mapper;

import com.example.pojo.Address;
import com.example.pojo.Order;
import com.example.pojo.OrderItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface OrderItemsMapper {
    @Select("SELECT * FROM order_items WHERE order_id = #{order_id}")
    @Results({
            @Result(property = "order_id", column = "order_id"),
            @Result(property = "goods_id", column = "goods_id"),
            @Result(property = "quantity", column = "quantity"),
    })
    List<OrderItem> getItemsByOrderId(@Param("order_id") int order_id);

    @Insert("INSERT INTO order_items (order_id, goods_id, quantity) VALUE (" +
            "#{order_id}, #{goods_id}, #{quantity})")
    void insertOrderItem(OrderItem orderItem);
}
