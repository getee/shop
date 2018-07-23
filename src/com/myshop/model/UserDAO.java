package com.myshop.model;

public interface UserDAO extends BaseDAO{
    /**
     * 获取用户数据
     */
    public User getIDUser(int id);
    
    public User getNameUser(String name);
    
    public int getSize();
    
    /**
     * 添加用户数据
     */
    public boolean addUser(String name,String password);
}
