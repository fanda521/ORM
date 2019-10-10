<%@page import="service.CustomServiceImpl"%>
<%@page import="service.CustomService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 处理登录 --%>
<%
	//获取参数
	String name = request.getParameter("user_name");
	String pwd = request.getParameter("user_pwd");

	CustomService service = new CustomServiceImpl();
	if(service.isLogin(name, pwd)){
		//用户存在
		out.write("用户存在");
		//如果客户存在就把客户的登入信息保存下来
		session.setAttribute("loginUser", name);
		//判断是否记住了密码
		String rem=request.getParameter("rem");
		String time=request.getParameter("time");
		Cookie cookie_name=new Cookie("user_name",name);
		Cookie cookie_pwd=new Cookie("user_pwd",pwd);
		
		if(rem!=null&&rem.equals("1")){
			//将用户名和密码保存在cookie里面
			
			int t=60*60;
			int expiry=t;
			try{
				t=Integer.parseInt(time);
				expiry=60*60*24*t;
			}
			catch(Exception e){
				System.out.println("time error!");
			}
			cookie_name.setMaxAge(expiry);
			cookie_pwd.setMaxAge(expiry);
		}
		else {
			cookie_name.setMaxAge(0);
			cookie_pwd.setMaxAge(0);
		}
		response.addCookie(cookie_name);
		response.addCookie(cookie_pwd);
		//如果用户存在转入到客户信息列表页面
		response.sendRedirect("cusList.jsp");
	} else {
		//用户不存在
		out.write("用户不存在");
		//如果用户不存在，转入到登录页面，并且将错误信息带回登录页面
		request.setAttribute("error", "用户名或者密码错误");
		//将原来填写的值保存到登录页面
		request.setAttribute("user_name", name);
		request.setAttribute("user_pwd", pwd);
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}
%>