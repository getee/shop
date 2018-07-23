package com.myshop.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.myshop.model.*;

/**
 * Servlet implementation class WineServlet
 */
@WebServlet("/WineServlet")
public class WineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private WineDAOImp dao;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WineServlet() {
        super();
        dao=new WineDAOImp();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String str=request.getParameter("method");
	    switch (str){
	    case "index": index(request,response); break;
	    case "search":search(request,response);break;
	    case "detail":detail(request,response);break;
	    case "nextPage":nextPage(request,response);break;

	    }
	}
	protected void nextPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String str=request.getParameter("searchKey");
        //确定是不是其它编码，进行转码
        if(str.equals( new String(str.getBytes("ISO-8859-1"), "ISO-8859-1" ))){
            str=new String(request.getParameter("searchKey").getBytes("ISO-8859-1"),"UTF-8");
        }

        int page=Integer.parseInt(request.getParameter("page"));
        int count=Integer.parseInt(request.getParameter("count"));
        System.out.println(page+" "+count);
        ArrayList<Wine> searchWine=dao.searchKindPage(str,page,count);
        response.setContentType("text/json;charset=utf-8");
        PrintWriter out=response.getWriter();//打印输出流
        JSONArray wineArray=new JSONArray();//json数组
        
        for(Wine w:searchWine){
            JSONObject winObj=new JSONObject();

            try {
                winObj.put("id", w.getId());
                winObj.put("name", w.getName());
                winObj.put("price", w.getPrice());
                winObj.put("picture", w.getPicture());
                //winObj.put("kind", w.getKind());//不在界面显示，不用存
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            wineArray.put(winObj);
        }
        out.write(wineArray.toString());
        out.flush();
        out.close();

	}
	protected void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Wine> topWine=dao.getWine(4);//推广酒信息
        request.setAttribute("banner", topWine);//首页大横幅4个
        ArrayList<Wine> wines=dao.getWine(25);//接下来的全部酒
        request.setAttribute("wines", wines);
        // include是自身跳转自身
        // forward是跳转其他界面
        request.getRequestDispatcher("index.jsp?method=index").forward(request, response);
    }
	
	/*
	 * 酒类的 分页搜索 ,返回集合以及 数量
	 */
	protected void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String str=null;
        if(request.getParameter("searchKey")==null){
            return ;
        }
        str=request.getParameter("searchKey");
        //确定是不是其它编码，进行转码
        if(str.equals( new String(str.getBytes("ISO-8859-1"), "ISO-8859-1" ))){
            str=new String(request.getParameter("searchKey").getBytes("ISO-8859-1"),"UTF-8");
        }
        
        int page=Integer.parseInt(request.getParameter("page"));
        int count=Integer.parseInt(request.getParameter("count"));
        System.out.println(page+" "+count);
        ArrayList<Wine> searchWine=dao.searchKindPage(str,page,count);
        request.setAttribute("wines", searchWine);
        
        int num =dao.searchKindNum(str);
        PageBean pg=new PageBean();
        pg.setNowPage(page);
        pg.setPreviousPage(page-1);
        pg.setNextPage(page+1);
        pg.setAllCount(num);
        int a=(num%count==0)?(num/count):(num/count+1);
        pg.setLastPage(a);
        request.setAttribute("pageBean", pg);
        request.setAttribute("searchKey", str);
        request.getRequestDispatcher("tplist.jsp").forward(request, response);
    }
	protected void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id=Integer.parseInt(request.getParameter("wine_id"));
        Wine w=dao.getIDWine(id);
        request.setAttribute("onewine", w);
        ArrayList<Wine> wines=dao.getWine(5);
        request.setAttribute("paywine", wines);
        request.getRequestDispatcher("onexq.jsp").forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    doGet(request,response);
	}

}
