package com.myshop.model;

import java.util.ArrayList;

public interface WineDAO extends BaseDAO {
    /**
     * 实现酒的数据库DAO层
     */
    
    /**
     * 取得全部酒的数据
     * @return wine集合
     */
    public ArrayList<Wine> getAllWine();
    
    /*
     * 取得n个酒的数据
     */
    public ArrayList<Wine> getWine(int n);
    
    /*
     * 取得某id酒的数据
     */
    public Wine getIDWine(int id);
    
    /*
     * 搜索酒的数据
     */
    public ArrayList<Wine> searchName(String str);
    
    /*
     * 搜索酒的数量
     */
    public int searchKindNum(String str);
    
    /*
     * 酒类 分页管理
     */
    public ArrayList<Wine> searchKindPage(String str,int page,int count);
    

    
}
