package com.example.service;

import com.example.mapper.CartMapper;
import com.example.pojo.Cart;
import com.example.pojo.Goods;
import com.example.pojo.User;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartService {
    @Resource
    private CartMapper cartMapper;

    @Autowired
    private GoodsService goodsService;

    public List<Cart> getCartByUserId(int user_id){
        return cartMapper.getCartByUserId(user_id);
    }

    public void deleteCart(int cart_id){
        cartMapper.deleteCart(cart_id);
    }


    public void addToCart(Integer user_id, Integer goods_id, Integer quantity){
        //调用Mapper将商品添加到购物车
        Cart cart = new Cart();
        cart.setUser_id(user_id);
        cart.setGoods_id(goods_id);
        cart.setQuantity(quantity);
        cartMapper.insertCart(cart);
    }

    //合并购物车项，返回合并后的购物车项列表
    public List<Cart> mergeCartItems(List<Cart> carts){
        Map<Integer, Cart> mergedItemsMap = new HashMap<>();
        for(Cart cart : carts){
            int goods_id = cart.getGoods_id();
            if(mergedItemsMap.containsKey(goods_id)){
                //合并购物车项
                Cart existingItem = mergedItemsMap.get(goods_id);
                existingItem.setQuantity(existingItem.getQuantity() + cart.getQuantity());
            }else{
                mergedItemsMap.put(goods_id, cart);
            }
        }
        return new ArrayList<>(mergedItemsMap.values());
    }

    /**
     * 结算购物车
     */
    public List<Goods> checkout(List<Cart> cartList){
        List<Goods> insufficientInventoryGoods = new ArrayList<>();
        List<Cart> newList = mergeCartItems(cartList);
        for(Cart cart : newList){
            int goods_id = cart.getGoods_id();
            int quantity_in_cart = cart.getQuantity();

            //获取商品库存信息
            Goods goods = goodsService.getGoodsById(goods_id);

            //检查库存是否充足
            if(goods != null && goods.getStock() < quantity_in_cart){
                insufficientInventoryGoods.add(goods);
            }
        }
        return insufficientInventoryGoods;
    }

    /**
     * 结算成功后清空购物车
     */
    public void clearCart(int user_id){
        cartMapper.deleteCartByUserId(user_id);
    }

    /**
     * 更新商品库存
     */
    public void updateGoodsStock(List<Cart> cartList){
        for(Cart cart : cartList){
            int goods_id = cart.getGoods_id();
            int quantity = cart.getQuantity();
            Goods goods = goodsService.getGoodsById(goods_id);
            int new_stock = goods.getStock() - quantity;
            goodsService.updateGoodsStock(goods_id, new_stock);
        }
    }
}
