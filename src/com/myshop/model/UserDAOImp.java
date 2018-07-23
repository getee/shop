package com.myshop.model;

import java.sql.*;

public class UserDAOImp extends BaseDAOImp implements UserDAO  {

    /**
     * 获取用户数据
     */
    @Override
    public User getIDUser(int id) {
        User u=null;
        ResultSet rs=null;
        Statement sta=getSta();
        try {
            rs=sta.executeQuery("select * from user where user_id="+id);
            while(rs.next()){
                u=new User();
                u.setId(rs.getInt("user_id"));
                u.setName(rs.getString("user_name"));
                u.setPassword(rs.getString("password"));
                u.setAddress(rs.getString("address"));
                u.setPhone(rs.getInt("phone"));
                u.setPicture(rs.getString("picture"));//新增用户图片
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        disposeResource(sta,rs);
        return u;
    }
    
    /**
     * 获取用户数据
     */
    
    public User getNameUser(String name) {    //如果返回值为null则允许用户注册
        User u=null;
        ResultSet rs=null;
        Statement sta=getSta();
        try {
            rs=sta.executeQuery("select * from user where user_name='"+name+"'");
            while(rs.next()){
                u=new User();
                u.setId(rs.getInt("user_id"));
                u.setName(rs.getString("user_name"));
                u.setPassword(rs.getString("password"));
                u.setAddress(rs.getString("address"));
                u.setPhone(rs.getInt("phone"));
                u.setPicture(rs.getString("picture"));//新增用户图片
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        disposeResource(sta,rs);
        return u;
    }
    
    /**
     * 获取数据库中的记录条数
     * @return
     */
    public int getSize() {
        ResultSet rs=null;
        Statement sta=getSta();
        int top=0;//指向第一行
        String sql="select count(user_name) from user";
        try {
            rs=sta.executeQuery(sql);
            top= rs.getInt(0);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        disposeResource(sta,rs);
        return top;
    }
    
    /**
     * 修改用户信息
     * @param name
     * @param password
     * @return
     */
    public boolean update(User u) {
        User uu=getNameUser(u.getName());//确定存在同名项
        if(uu!=null)
            if(uu.getId()!=u.getId()) return false;//且用户id不同，则用户名已被占用
        if(u.getName()==null){//未初始化
            u.setName("");//赋初始值，显示空
        }
        if(u.getAddress()==null){//未初始化
            u.setAddress("");//赋初始值，显示空
        }
        if(u.getPhone()==000){//未初始化
            u.setPhone(000);//赋初始值，显示空
        }
        Statement sta=getSta();
        try {
            String sql="update user set user_name='"+u.getName()+"', address='"+u.getAddress()+"', phone="+u.getPhone()+", picture='"+u.getPicture()+"' where user_id="+u.getId();
            int i=sta.executeUpdate(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        disposeResource(sta);
        return true;
    }
    
    /**
     * 起始注册，传入用户名和密码
     * @param name
     * @param password
     * @return
     */
    public boolean addUser(String name,String password) {
        if(getNameUser(name)!=null) return false;//用户名已被占用
        Statement sta=getSta();
        try {
            String sql="insert into user(user_name,password,picture) values('"+name+"','"+password+"','images/undefine.jpg')";
            int i=sta.executeUpdate(sql);
            System.out.println(i+":"+sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        disposeResource(sta);
        return true;
    }

    
    
}
