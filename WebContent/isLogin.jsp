<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
      Object user=session.getAttribute("loginUser");
		if(user==null){
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
%>