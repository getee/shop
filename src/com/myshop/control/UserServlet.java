package com.myshop.control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.catalina.core.ApplicationPart;







import com.myshop.model.ShoppingCarDAOImp;
import com.myshop.model.User;
import com.myshop.model.UserDAOImp;
import com.myshop.model.Wine;
import com.myshop.until.SHA;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
@MultipartConfig //处理文件上传的声明
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserDAOImp dao;
    private ShoppingCarDAOImp carDAO;
    
    private  ServletConfig  config;
    @Override
    public void init(ServletConfig config) throws ServletException {
        this.config=config;
    }
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        dao=new UserDAOImp();
        carDAO=new ShoppingCarDAOImp();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    String method=request.getParameter("method");
	    
	    /*if(method==null) {//如果是上传文件的方法，request获取不到方法,则会进入到这个分支
            update(request,response);
            return ;
        }*/
	    
	    switch(method){
	    case "index":index(request,response);break;
	    case "zhuce":zhuce(request,response);break;
	    case "denglu":denglu(request,response);break;
	    case "signout":signOut(request,response);break;
	    case "update":update(request,response);break;
	    }
	}
	/*
	 * 更新用户信息，上传文件
	 */
    protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        Enumeration<String> heads = request.getHeaderNames();
        //获取浏览器信息
        while (heads.hasMoreElements()) {  
            String header = heads.nextElement();  
            System.out.println(header + "=" + request.getHeader(header));  
        }
        */
        User u=(User)request.getSession().getAttribute("User");
        String username=u.getName();
        String address=request.getParameter("address");
        String phone=request.getParameter("phone");
        if(!username.equals("")){
            u.setName(username);
        }
        if(!address.equals("")){
            u.setAddress(address);
        }
        if(!phone.equals("")){
            u.setPhone(Integer.parseInt(phone));
        }
        

        Part part = request.getPart("fileToUpload");  
        Collection<String> headerNames = part.getHeaderNames();  
        //获取图片信息
        for (String header : headerNames) {  
            System.out.println(header + "=" + part.getHeader(header));  
        }
 
        String filename = new String(getFilename(part).getBytes(), "UTF-8");  
        

        if(!filename.equals("")){//上传文件的操作
            InputStream is = part.getInputStream(); 
            String  uuidName=UUID.randomUUID().toString();
            //获得项目的根目录,并设计文件保存路径
            String filePath=request.getServletContext().getRealPath("/images/upload");
            System.out.println(request.getServletContext().getRealPath("/images/upload"));
            String uPath="images/upload";

            for(int n=0;n<3;n++)//uuidName.length()
            {
                filePath+="/"+uuidName.charAt(n);
                uPath+="/"+uuidName.charAt(n);
            }
            System.out.println(filePath);
            
            //图片路径保存
            u.setPicture(uPath+"/"+filename);
            System.out.println(":"+u.getPicture());


            File file = new File(filePath);  
            if (!file.exists() || !file.isDirectory()) {//是否存在，存在判断是否目录。不存在直接建立目录
                file.mkdirs();  
            }  

            FileOutputStream fos = new FileOutputStream(new File(file + File.separator + filename));  
            byte[] buf = new byte[1024];  
            while (is.read(buf) != -1) {  
                fos.write(buf);  
            }  
            fos.flush();  
            fos.close();  
            is.close(); 
        }
        boolean isOK=dao.update(u);
        response.sendRedirect("hyzx.jsp");
        
        
        
    }
    public String getFilename(Part part) {  
        String contentDispositionHeader = part.getHeader("content-disposition");  
        String[] elements = contentDispositionHeader.split(";");  
        for (String element : elements) {  
            if (element.trim().startsWith("filename")) {  
                return element.substring(element.indexOf('=') + 1).trim().replace("\"", "");  
            }  
        }  
        return null;  
    } 
	/*
	 * 利用session，打开首页时直接登录
	 */
    protected void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] allCookies=request.getCookies();
        String username=null;
        String password=null;
        for(Cookie c:allCookies){
            if(c.getName().equals("username")){
                username=c.getValue();
            }
            if(c.getName().equals("password")){
                password=c.getValue();
            }
        }
        if(username==null || password==null){
            signOut(request,response);
            return ;
        }
        System.out.println(username+password);
        User u=dao.getNameUser(username);
        if(!u.getPassword().equals(password)){//cookie密码不正确
            signOut(request,response);
            return ;
        }else{
            request.getSession().setAttribute("User", u);
            response.sendRedirect("index.jsp");// 重定向
            return ;
        }
        
    }

    protected void signOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession hs=request.getSession(false);//防止创建Session 
        if(hs==null){
            response.sendRedirect("index.jsp");
            return ;
        }
        hs.removeAttribute("User");
        hs.removeAttribute("ShoppingCar");
        
        Cookie username=new Cookie("username",null);  
        username.setMaxAge(0);  
        response.addCookie(username);
        Cookie password=new Cookie("password",null);  
        password.setMaxAge(0);  
        response.addCookie(password);
        response.sendRedirect("index.jsp");
        return ;
    }

    /**
     * 用户登录，以及处理session的购物车数据，
     * 将session ShoppingCar存放在SQL中
     */
    protected void denglu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName=new String(request.getParameter("username").getBytes("ISO-8859-1"),"UTF-8");
        String passWord=new String(request.getParameter("password").getBytes("ISO-8859-1"),"UTF-8");
        passWord=SHA.SHA(passWord);
        User u=dao.getNameUser(userName);
        if(u==null){
            request.setAttribute("denglu", "用户名不存在！");
            request.getRequestDispatcher("denglu.jsp").forward(request, response);
        }else if(!u.getPassword().equals(passWord)){
            request.setAttribute("denglu", "用户名或密码错误");
            request.getRequestDispatcher("denglu.jsp").forward(request, response);
        }else{
            Cookie usernameCookie=new Cookie("username",userName);
            Cookie passwordCookie=new Cookie("password",passWord);
            String remember=request.getParameter("remember");
            
            if(remember!=null){
                usernameCookie.setMaxAge(60*60*24);
                passwordCookie.setMaxAge(60*60*24);
                response.addCookie(usernameCookie);//将cookie存起来
                response.addCookie(passwordCookie);//将cookie存起来
            }else{
                usernameCookie.setMaxAge(0);
                passwordCookie.setMaxAge(0);
            }
            
            //当用户登录时候购物车session存在,则将购物车商品写入用户数据库
            /**
             * 当session和SQL中起冲突时如何合并？
             * 在这里同步session和SQL
             * 当登录时优先写入SQL,在从SQL读取到session，当添加修改时考虑SQL
             * 未登录写进session
             */
            if(request.getSession().getAttribute("ShoppingCar")!=null){
                HashMap<Wine,Integer> sessionCar=(HashMap)request.getSession().getAttribute("ShoppingCar");
                HashMap<Wine,Integer> sqlCar=carDAO.get(u.getId());
                for(Wine w:sqlCar.keySet()){
                    if(sessionCar.get(w)==null){//session中没有该商品
                        sessionCar.put(w, sqlCar.get(w));//将SQL传到sessionCar中
                    }else{//具有重复商品
                        int ssnum=sessionCar.get(w);
                        int sqlnum=sqlCar.get(w);
                        sessionCar.put(w, ssnum+sqlnum);
                    }
                }
                //保存到SQL
                boolean isOK=carDAO.add(u, sessionCar);
                //在登录状态下，设置新的Car session,可省略，在跳入时会自动读取
                //request.getSession().setAttribute("ShoppingCar",sessionCar);
            }

            request.getSession().setAttribute("User", u);
            response.sendRedirect("hyzx.jsp");// 重定向
        }
        
    }
	protected void zhuce(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName=new String(request.getParameter("userName").getBytes("ISO-8859-1"),"UTF-8");
        String passWord=new String(request.getParameter("passWord").getBytes("ISO-8859-1"),"UTF-8");
        passWord=SHA.SHA(passWord);
        boolean isOK=dao.addUser(userName, passWord);
        if(isOK==false){
            request.setAttribute("zhuce", "用户名被占用了！");
            request.getRequestDispatcher("zhuce.jsp").forward(request, response);;
        }else // 成功跳到一个用户信息界面，询问是否修改信息
        {
            Cookie usernameCookie=new Cookie("username",userName);
            Cookie passwordCookie=new Cookie("password",passWord);
            response.addCookie(usernameCookie);//讲cookie存起来
            response.addCookie(passwordCookie);//讲cookie存起来
            
            request.getSession().setAttribute("User", dao.getNameUser(userName));
            System.out.println("SS:"+dao.getNameUser(userName).getPicture());
            response.sendRedirect("hyzx.jsp");// 重定向
        }
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    doGet(request,response);
	}

}
