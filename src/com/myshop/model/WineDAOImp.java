package com.myshop.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import static com.myshop.until.HibernateSessionFactory.getSession;

public class WineDAOImp extends BaseDAOImp implements WineDAO {

    @Override
    public ArrayList<Wine> getAllWine() {
        ArrayList<Wine> wines=new ArrayList<>();
        /*ResultSet rs=null;
        Statement sta=getSta();
        try {
            rs=getSta().executeQuery("select * from wine");
            while(rs.next()){
                Wine wine=new Wine();
                wine.setId(rs.getInt("wine_id"));
                wine.setName(rs.getString("wine_name"));
                wine.setKind(rs.getString("kind"));
                wine.setPrice(rs.getDouble("price"));
                wine.setDetail(rs.getString("detail"));
                wine.setPicture(rs.getString("picture"));
                wines.add(wine);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        disposeResource(sta,rs);*/
        Session s=getSession();
        Transaction tr=s.beginTransaction();
        Criteria cri=s.createCriteria(Wine.class);
        try{
            wines=(ArrayList<Wine>) cri.list();
            tr.commit();
        }catch(Exception e){
            tr.rollback();
            e.printStackTrace();
        }
        
        s.close();
        return wines;
    }

    @Override
    public ArrayList<Wine> getWine(int n) {
        ArrayList<Wine> wines=new ArrayList<>();
        int i=(int)(Math.random()*(199-n));//随机抽取记录
        /*ResultSet rs=null;
        Statement sta=getSta();
        try {
            rs=getSta().executeQuery("select * from wine LIMIT "+i+","+n);
            while(rs.next()){
                Wine wine=new Wine();
                wine.setId(rs.getInt("wine_id"));
                wine.setName(rs.getString("wine_name"));
                wine.setKind(rs.getString("kind"));
                wine.setPrice(rs.getDouble("price"));
                wine.setDetail(rs.getString("detail"));
                wine.setPicture(rs.getString("picture"));
                wines.add(wine);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        disposeResource(sta,rs);*/
        Session s=getSession();
        //Transaction tr=s.beginTransaction();
        Criteria cri=s.createCriteria(Wine.class);
        cri.setFirstResult(i);
        cri.setMaxResults(n);
        wines=(ArrayList<Wine>) cri.list();
        System.out.println(wines);
        //tr.commit();
        s.close();
        return wines;
    }
    
    
    @Override
    public Wine getIDWine(int id) {
        Wine wine=null;
        /*ResultSet rs=null;
        Statement sta=getSta();
        try {
            rs=sta.executeQuery("select * from wine where wine_id="+id);
            while(rs.next()){
                wine=new Wine();
                wine.setId(rs.getInt("wine_id"));
                wine.setName(rs.getString("wine_name"));
                wine.setKind(rs.getString("kind"));
                wine.setPrice(rs.getDouble("price"));
                wine.setDetail(rs.getString("detail"));
                wine.setPicture(rs.getString("picture"));
                
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        disposeResource(sta,rs);*/
        Session s=getSession();
        //Transaction tr=s.beginTransaction();
        wine=(Wine) s.get(Wine.class, id);
        //tr.commit();
        s.close();
        return wine;
    }

    @Override
    public ArrayList<Wine> searchName(String str) {
        ArrayList<Wine> wines=new ArrayList<>();
        /*ResultSet rs=null;
        Statement sta=getSta();
        try {
            rs=sta.executeQuery("select * from wine where wine_name='"+str.trim()+"'");
            while(rs.next()){
                Wine wine=new Wine();
                wine.setId(rs.getInt("wine_id"));
                wine.setName(rs.getString("wine_name"));
                wine.setKind(rs.getString("kind"));
                wine.setPrice(rs.getDouble("price"));
                wine.setDetail(rs.getString("detail"));
                wine.setPicture(rs.getString("picture"));
                wines.add(wine);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        disposeResource(sta,rs);*/
        Session s=getSession();
        Transaction tr=s.beginTransaction();
        String hql="from Wine w where w.name='"+str.trim()+"'";
        Query query=s.createQuery(hql);
        wines=(ArrayList<Wine>) query.list();
        return wines;
    }
    
    @Override
    public int searchKindNum(String str) {
        //ArrayList<Wine> wines=new ArrayList<>();
        int n=0;
        /*ResultSet rs=null;
        Statement sta=getSta();
        try {
            rs=sta.executeQuery("select count(wine_id) from wine where kind='"+str.trim()+"'");
            rs.next();
            n=rs.getInt(1);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        disposeResource(sta,rs);*/
        Session s=getSession();
        Transaction tr=s.beginTransaction();
        String hql="select count(*) from Wine w where w.kind='"+str.trim()+"'";
        Query query=s.createQuery(hql);
        List<Integer> list= query.list();
        System.out.println(list);
        n=Integer.parseInt(String.valueOf(list.get(0)));//Long强转成int类型
        return n;
    }

    /*
     * 获取酒种类并且分页
     * @see com.myshop.model.WineDAO#searchKindpage(java.lang.String, int, int)
     */
    @Override
    public ArrayList<Wine> searchKindPage(String str,int page, int count) {
        ArrayList<Wine> wines=new ArrayList<>();
        /*ResultSet rs=null;
        Statement sta=getSta();
        try {
            rs=sta.executeQuery("select * from wine where kind='"+str.trim()+"' limit "+(page-1)*count+" , "+count);
            while(rs.next()){
                Wine wine=new Wine();
                wine.setId(rs.getInt("wine_id"));
                wine.setName(rs.getString("wine_name"));
                wine.setKind(rs.getString("kind"));
                wine.setPrice(rs.getDouble("price"));
                wine.setDetail(rs.getString("detail"));
                wine.setPicture(rs.getString("picture"));
                wines.add(wine);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        disposeResource(sta,rs);*/
        Session s=getSession();
        Transaction tr=s.beginTransaction();
        String hql="from Wine w where w.kind='"+str.trim()+"'";
        Query query=s.createQuery(hql);
        query.setFirstResult((page-1)*count);
        query.setMaxResults(count);
        Iterator list= query.iterate();
        System.out.println(list);
        while(list.hasNext()){
            Wine w=(Wine) list.next();
            wines.add(w);
        }
        return wines;
    }

}
