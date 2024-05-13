package com.example.mapper;

import com.example.pojo.Merchant;
import com.example.pojo.Order;
import com.example.pojo.ProvinceSales;
import com.example.service.OrderService;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface OrderMapper {
    @Select("SELECT * FROM orders WHERE user_id = #{user_id}")
    @Results({
            @Result(property = "order_id", column = "order_id"),
            @Result(property = "user_id", column = "user_id"),
            @Result(property = "shipping_address_id", column = "shipping_address_id"),
            @Result(property = "order_date", column = "order_date"),
    })
    List<Order> selectOrdersByUserId(@Param("user_id") int user_id);

    @Insert("INSERT INTO orders (order_id, user_id, shipping_address_id, order_date, totalPrice) VALUE (" +
            "#{order_id}, #{user_id}, #{shipping_address_id}, #{order_date}, #{totalPrice})")
    void insertOrder(Order order);

    @Select("SELECT * FROM orders WHERE order_id = #{order_id}")
    @Results({
            @Result(property = "order_id", column = "order_id"),
            @Result(property = "user_id", column = "user_id"),
            @Result(property = "shipping_address_id", column = "shipping_address_id"),
            @Result(property = "order_date", column = "order_date"),
    })
    Order selectOrdersByOrderId(@Param("order_id") int order_id);

    @Select("SELECT MAX(order_id) FROM orders")
    int getCurrentOrderId();

    @Select("SELECT * FROM orders")
    @Results({
            @Result(property = "order_id", column = "order_id"),
            @Result(property = "user_id", column = "user_id"),
            @Result(property = "shipping_address_id", column = "shipping_address_id"),
            @Result(property = "order_date", column = "order_date"),
    })
    List<Order> getAllOrders();

    @Select("SELECT a.province, SUM(o.totalPrice) AS sales " +
            "FROM orders o " +
            "JOIN addresses a ON o.shipping_address_id = a.address_id " +
            "GROUP BY a.province")
    List<ProvinceSales> selectProvinceSales();

}
