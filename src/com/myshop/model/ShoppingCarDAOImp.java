package com.myshop.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ShoppingCarDAOImp extends BaseDAOImp implements ShoppingCarDAO {

    /*
     * 将用户信息，以及购物车信息存储到SQL
     */
    @Override
    public boolean add(User u, HashMap<Wine, Integer> shoppingCar) {
        Statement sta=getSta();
        for(Wine w:shoppingCar.keySet()){
            String sql="insert into shopcar(user_id,wine_id,number) values("+u.getId()+","+w.getId()+","+shoppingCar.get(w)+")";
            
            try {
                sta.executeUpdate(sql);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            }
        }
        disposeResource(sta);
        return true;
    }
    /**
     * 添加新的商品
     * @param uid
     * @param wid
     * @param number
     * @return
     */
    public boolean add(int uid, int wid, int number) {
        Statement sta=getSta();
        String sql="insert into shopcar(user_id,wine_id,number) values("+uid+","+wid+","+number+")";
        try {
            sta.executeUpdate(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        disposeResource(sta);
        return true;
    }

    /*
     * 获取某个用户对应的购物车信息
     */
    @Override
    public HashMap<Wine, Integer> get(User u) {
        HashMap<Integer,Integer> widANDnum=new HashMap<>();
        HashMap<Wine,Integer> shoppingCar=new HashMap<>();
        Statement sta=getSta();
        ResultSet rsc=null;
        ResultSet rsw=null;
        String carSQL="select * from shopcar where user_id="+u.getId()+"";
        try {
            
            //现获取id信息+数量
            rsc=sta.executeQuery(carSQL);
            while(rsc.next()){
                int id=rsc.getInt("wine_id");
                int n=rsc.getInt("number");
                widANDnum.put(id, n);
            }
            disposeResource(rsc);
            
            //获取id对应的酒信息
            for(int wid:widANDnum.keySet()){
                String wineSQL="select * from wine where wine_id="+wid+"";
                rsw=sta.executeQuery(wineSQL);
                Wine wine=new Wine();
                while(rsw.next()){
                    wine.setId(rsw.getInt("wine_id"));
                    wine.setName(rsw.getString("wine_name"));
                    wine.setKind(rsw.getString("kind"));
                    wine.setPrice(rsw.getFloat("price"));
                    wine.setDetail(rsw.getString("detail"));
                    wine.setPicture(rsw.getString("picture"));
                    
                    shoppingCar.put(wine, widANDnum.get(wid));
                }
            }
       

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        disposeResource(sta,rsw);
        
        return shoppingCar;
    }
    /**
     * 获取用户的购物车信息
     * @param uid
     * @return
     */
    public HashMap<Wine, Integer> get(int uid) {
        HashMap<Integer,Integer> widANDnum=new HashMap<>();
        HashMap<Wine,Integer> shoppingCar=new HashMap<>();
        /*Statement sta=getSta();
        ResultSet rsc=null;
        ResultSet rsw=null;
        String carSQL="select * from shopcar where user_id="+uid+"";
        try {
            
            //现获取id信息+数量
            rsc=sta.executeQuery(carSQL);
            while(rsc.next()){
                int id=rsc.getInt("wine_id");
                int n=rsc.getInt("number");
                widANDnum.put(id, n);
            }
            disposeResource(rsc);
            
            //获取id对应的酒信息
            for(int wid:widANDnum.keySet()){
                String wineSQL="select * from wine where wine_id="+wid+"";
                rsw=sta.executeQuery(wineSQL);
                Wine wine=new Wine();
                while(rsw.next()){
                    wine.setId(rsw.getInt("wine_id"));
                    wine.setName(rsw.getString("wine_name"));
                    wine.setKind(rsw.getString("kind"));
                    wine.setPrice(rsw.getDouble("price"));
                    wine.setDetail(rsw.getString("detail"));
                    wine.setPicture(rsw.getString("picture"));
                    
                    shoppingCar.put(wine, widANDnum.get(wid));
                }
            }
       

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        disposeResource(sta,rsw);*/
        
        return shoppingCar;
    }

    /*
     * 删除一件商品,返回其余商品
     */
    @Override
    public boolean delOne(int uid, int wid) {
        Statement sta=getSta();
        String sql="delete from shopcar where user_id="+uid+" and wine_id="+wid;
        try {
            sta.executeUpdate(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        disposeResource(sta);
        return true;
    }

    /*
     * 删除所有商品
     */
    @Override
    public boolean delAll(int uid) {
        Statement sta=getSta();
        String sql="delete from shopcar where user_id="+uid;
        try {
            sta.executeUpdate(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        disposeResource(sta);
        return true;
    }

    /*
     * 是否存在该用户以及商品
     * @see com.myshop.model.ShoppingCarDAO#isExist(int, int)
     */
    @Override
    public boolean isExist(int uid, int wid) {
        Statement sta=getSta();
        ResultSet rs=null;
        int n=0;
        String sql="select * from shopcar where wine_id="+wid+" and user_id="+uid;
        try {
            rs=sta.executeQuery(sql);
            while(rs.next()){
                n++;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        if(n==0)return false;
        return true;
    }

    /*
     * 传递参数即将要添加的数量
     * @see com.myshop.model.ShoppingCarDAO#addExist(int, int, int)
     */
    @Override
    public boolean addExist(int uid, int wid, int number) {
        if(!isExist(uid,wid)){//不存在该商品
            return false;
        }
        Statement sta=getSta();
        ResultSet rs=null;
        int n=0;
        String sql="select * from shopcar where wine_id="+wid+" and user_id="+uid;
        try {
            rs=sta.executeQuery(sql);
            if(rs.next()){
                n=rs.getInt("number");//获取原来数量
            }
            n=n+number;//新的数量
            sql="update shopcar set number="+n+" where user_id="+uid+" and wine_id="+wid;
            n=sta.executeUpdate(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        if(n==0)return false;
        return true;

    }

}
