package com.myshop.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myshop.model.ShoppingCarDAOImp;
import com.myshop.model.User;
import com.myshop.model.UserDAOImp;
import com.myshop.model.Wine;
import com.myshop.model.WineDAOImp;

/**
 * Servlet implementation class ShoppingCarServlet
 */
@WebServlet("/ShoppingCarServlet")
public class ShoppingCarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAOImp uDAO;
	private WineDAOImp wDAO;
	private ShoppingCarDAOImp carDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShoppingCarServlet() {
        super();
        uDAO=new UserDAOImp();
        wDAO=new WineDAOImp();
        carDAO=new ShoppingCarDAOImp();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method=request.getParameter("method");
		switch(method){
		case "add":add(request,response);break;
		case "getCarSQL":getCarSQL(request,response);break;
		case "delAll":delAll(request,response);break;
		case "delOne":delOne(request,response);break;
		}
	}
    protected void delOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int wid=Integer.parseInt(request.getParameter("wineid"));
        HashMap<Wine,Integer> wines=(HashMap) request.getSession().getAttribute("ShoppingCar");
        
        //对session进行相同的操作
        Wine w=wDAO.getIDWine(wid);
        wines.remove(w);
        request.getSession().setAttribute("ShoppingCar",wines);

        if(request.getSession().getAttribute("User")==null){
            response.sendRedirect("ddtj.jsp");//在一个请求中不能同时重定向和转发
            return ;
        }else{
            User u=(User)request.getSession().getAttribute("User");
            carDAO.delOne(u.getId(),wid);

            //用户已登录的跳转
            request.setAttribute("isGet", "ok");
            //除了这里，其它地方跳到ddtj.jsp都是采用重定向
            //所以可以设置request级别回馈，每次重定向都能访问用户购物数据库
            request.getRequestDispatcher("ddtj.jsp").forward(request, response);
        }
    }
    protected void delAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute("User")==null){
            request.getSession().setAttribute("ShoppingCar", null);
            response.sendRedirect("ddtj.jsp");//在一个请求中不能同时重定向和转发
            return ;
        }else{
            User u=(User)request.getSession().getAttribute("User");
            carDAO.delAll(u.getId());

            //同步SQL到session
            request.getSession().setAttribute("ShoppingCar", null);//将SQL传到session
            request.setAttribute("isGet", "ok");
            
            //除了这里，其它地方跳到ddtj.jsp都是采用重定向
            //所以可以设置request级别回馈，每次重定向都能访问用户购物数据库
            request.getRequestDispatcher("ddtj.jsp").forward(request, response);
        }
    }
    protected void getCarSQL(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("getCarSQL to Session");
        if(request.getSession().getAttribute("User")==null){
            System.out.println("重定向");
            response.sendRedirect("ddtj.jsp");//在一个请求中不能同时重定向和转发
            return ;

        }else{
            User u=(User)request.getSession().getAttribute("User");
            HashMap<Wine,Integer> shopcar=carDAO.get(u);
            
            //同步SQL到session
            request.getSession().setAttribute("ShoppingCar", shopcar);//将SQL传到session
            request.setAttribute("isGet", "ok");
            
            //除了这里，其它地方跳到ddtj.jsp都是采用重定向
            //所以可以设置request级别回馈，每次重定向都能访问用户购物数据库
            request.getRequestDispatcher("ddtj.jsp").forward(request, response);
        }

    }

    protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int wineid=Integer.parseInt(request.getParameter("wineid"));
        int num=Integer.parseInt(request.getParameter("number"));//获取购买数量
        User u=(User)request.getSession().getAttribute("User");
        if(u==null){//用户还未登录，购物信息写在Session
            if(request.getSession().getAttribute("ShoppingCar")==null){//确定之前添加过购物车没
                HashMap<Wine,Integer> shoppingCar=new HashMap<>();
                Wine w=wDAO.getIDWine(wineid);//商品和购买数量--》再发起请求会占用用户网速，牺牲内存换速度《Wine,munber》
                
                //购买数量和商品是这个servlet的工作
                shoppingCar.put(w, num);
                request.getSession().setAttribute("ShoppingCar", shoppingCar);
            }else{//已经有商品存在购物车里,先取出购物车物品
                HashMap<Wine,Integer> shoppingCar=(HashMap)request.getSession().getAttribute("ShoppingCar");
                //加入购物车，不进行界面跳转，商品id和购买数量

                int oldnum=0;
                Wine w=wDAO.getIDWine(wineid);//商品和购买数量--》再发起请求会占用用户网速，牺牲内存换速度《Wine,munber》
                if(shoppingCar.get(w)!=null)//存在旧的酒
                    oldnum=shoppingCar.get(w);
                System.out.println(num+"dd"+oldnum+"dd"+""+wineid);
                shoppingCar.put(w, num+oldnum);
                request.getSession().setAttribute("ShoppingCar", shoppingCar);
            }
            //传到其他WineServlet上
            request.getRequestDispatcher("WineServlet?method=detail&wine_id="+wineid).forward(request, response);
            return ;
            
        }else{//用户登录,购物信息写在SQL中
            int uid=u.getId();
            /**
             * 是否存在SQL中的购物车
             */
            if(!carDAO.isExist(uid, wineid)){//数据库中不存在
                carDAO.add(uid, wineid,num);
            }else{
                carDAO.addExist(uid, wineid, num);
            }

            request.getRequestDispatcher("WineServlet?method=detail&wine_id="+wineid).forward(request, response);
            return ;
        }
        
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
