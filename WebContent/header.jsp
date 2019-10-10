<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>头部</title>
</head>
<style type="text/css">
		body{
			margin: 0 auto;
			text-align: center;
			
		}
		#all{
			position: relative;
		}
		#head{
			
			
		}
		#loginOut{
			position: absolute;
			top:16px;
			right: 10%;
			font-size: 20px;
			text-decoration: none;
			color: black;
		}
		#loginOut:hover{
			color: blue;
		}
	</style>
	<body>
		<div id="all">
			<h1 id="head">客户管理系统</h1>
			<%
	if(session.getAttribute("loginUser") != null){
%>
		<a id="loginOut" href=loginOut.jsp>注销</a>
<%	
	}
%>
			
		</div>
		<hr>
	</body>
</html>