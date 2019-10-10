<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

	session.setAttribute("loginUser", null);//注销
	
	session.invalidate();//注销二
	
	
	//session.setMaxInactiveInterval(0);//不可以
	
	response.sendRedirect("index.jsp");
%>