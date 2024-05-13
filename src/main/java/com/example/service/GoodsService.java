package com.example.service;

import com.example.mapper.GoodsMapper;
import com.example.pojo.Goods;
import com.example.pojo.MonthSales;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoodsService {
    @Resource
    private final GoodsMapper goodsMapper;

    @Autowired
    public GoodsService(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }

    public Goods getGoodsById(Integer id) {
        return goodsMapper.getGoodsById(id);
    }

    public List<Goods> getAllGoods() {
        return goodsMapper.getAllGoods();
    }

    public void updateGoodsStock(int id,int new_stock){
        goodsMapper.updateGoodsStock(id, new_stock);
    }
    public void addGoods(Goods goods) {
        goodsMapper.insertGoods(goods);
    }

    public void updateGoods(Goods goods) {
        goodsMapper.updateGoods(goods);
    }

    public void deleteGoods(Integer id) {
        goodsMapper.deleteGoods(id);
    }

    public Map<String, Object> updateProduct(int productId, String productName, String productDescription, double productPrice, int productStock) {
        Goods goods = goodsMapper.getGoodsById(productId);
        goods.setName(productName);
        goods.setDescription(productDescription);
        goods.setPrice(productPrice);
        goods.setStock(productStock);
        goodsMapper.updateGoods(goods);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", 200);
        resultMap.put("message", "Edit Successfully");
        return resultMap;
    }

    public List<Goods> getGoodsBySeller(int seller) {
        return goodsMapper.getGoodsBySeller(seller);
    }

    public List<MonthSales> getMonthSalesById(int id) {
        return goodsMapper.getMonthSalesById(id);
    }
}
