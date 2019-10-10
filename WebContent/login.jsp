<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>登录页面</title>
<script type="text/javascript">
	function login() {
		var user_name = document.getElementById('user_name').value;
		var user_pwd = document.getElementById('user_pwd').value;
		//只要用户名和密码有一个空的不能提交
		if('' == user_name.trim() || '' == user_pwd.trim()){
			alert('用户名和密码都必须填写');
			return ;
		}	
		document.getElementById('loginForm').submit();	
	}
</script>
</head>
<%
	boolean flag = false;
	if(request.getAttribute("error")==null){
		
	
		// 读取cookie
		Cookie[] cookies =  request.getCookies();
		for(Cookie c : cookies){
			if(c.getName().equals("user_name")){
				//需要记住用户名
				String user_name =  c.getValue();
				pageContext.setAttribute("user_name", user_name);
				flag = true;
			}
			if(c.getName().equals("user_pwd")){
				//需要记住密码
				String user_pwd = c.getValue();
				pageContext.setAttribute("user_pwd", user_pwd);
			}
		}
		//用户名和密码都存储情况下，直接登录
	}
%>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div>
		<p>${error }</p>
		<form id="loginForm" action="dealLogin.jsp"  method="post">
			<p>用户名：<input type="text" name="user_name" value="${user_name == null ? 'admin' : user_name}" id="user_name"></p>
			<p>密　码：<input type="password" name="user_pwd" value="${user_pwd }" id="user_pwd"></p>
			<p>
					<p>  <input type="checkbox" name="rem" 
				<%
					if(flag){
				%>	
						checked="checked" 
				<%	
					}
				%>
				 
				
			value="1">
				记住密码
				<select name="time">
					<option value="1">一天</option>
					<option value="7">一周</option>
					<option value="30">一个月</option>
				</select>
				
			</p>
			<p>  <input type="button" onclick="login()" value="登录">  </p>
		</form>
	</div>
	
</body>
</html>