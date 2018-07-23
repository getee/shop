package com.myshop.model;

import java.util.HashMap;

public interface ShoppingCarDAO extends BaseDAO{
    /*
     * 将用户信息，以及购物车信息存储到SQL
     */
    public boolean add(User u,HashMap<Wine,Integer> shoppingCar);
    /*
     * 获取某个用户对应的购物车信息
     */
    public HashMap<Wine,Integer> get(User u);
    /*
     * 获取商品是否已存在
     */
    public boolean isExist(int uid,int wid);
    /*
     * 存在商品再添加
     */
    public boolean addExist(int uid,int wid,int number);
    /*
     * 删除一件商品,servlet中get返回其余商品
     */
    public boolean delOne(int uid, int wid);
    /*
     *  删除所有商品
     */
    public boolean delAll(int uid);

}
