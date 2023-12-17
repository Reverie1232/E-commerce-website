package com.example.service;

import com.example.mapper.OrderItemsMapper;
import com.example.mapper.OrderMapper;
import com.example.pojo.Order;
import com.example.pojo.OrderItem;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    @Resource
    OrderMapper orderMapper;
    OrderItemsMapper orderItemsMapper;

    @Autowired
    public OrderService(OrderMapper orderMapper, OrderItemsMapper orderItemsMapper){
        this.orderMapper = orderMapper;
        this.orderItemsMapper = orderItemsMapper;
    }

    public List<Order> getOrdersByUserId(int user_id){
        return orderMapper.selectOrdersByUserId(user_id);
    }

    public Order getOrdersByOrderId(int order_id){
        return orderMapper.selectOrdersByOrderId(order_id);
    }
    //新增订单数据
    public int addOrder(int user_id, int address_id, double totalPrice){
        Order order = new Order();
        order.setUser_id(user_id);
        order.setTotalPrice(totalPrice);
        order.setShipping_address_id(address_id);
        order.setOrder_date(LocalDateTime.now());
        orderMapper.insertOrder(order);
        return orderMapper.getCurrentOrderId();
    }
    //新增订单商品数据
    public void insertOrderItem(int order_id, int goods_id, int quantity){
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder_id(order_id);
        orderItem.setGoods_id(goods_id);
        orderItem.setQuantity(quantity);
        orderItemsMapper.insertOrderItem(orderItem);
    }
    //获取订单详细信息
    public List<OrderItem> getItemsByOrderId(int order_id){
        return orderItemsMapper.getItemsByOrderId(order_id);
    }

    public List<Order> getAllOrders() {
        return orderMapper.getAllOrders();
    }

    public int getCurrentOrderId() {
        return orderMapper.getCurrentOrderId();
    }
}
