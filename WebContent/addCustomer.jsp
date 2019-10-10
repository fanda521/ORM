<%@page import="entity.CustomerInfo"%>
<%@page import="service.CustomServiceImpl"%>
<%@page import="service.CustomService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="isLogin.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<link rel="stylesheet" type="text/css" href="css/add.css" />
 
<body>
<jsp:include page="header.jsp"></jsp:include>
	<div id="all">
		<form action="addCustomerInfo" method="post"  >
			<h2>添加用户信息</h2>
			<p><pre>姓名</pre><input type="text" name="in_name"/><label></label></p>
			<p><pre>学历</pre><input type="text" name="in_degree"/><label></label></p>
			<p><pre>性别</pre><input type="text" name="in_sex" /><label></label></p>
			<p><pre>职业</pre><input type="text" name="in_career"/><label></label></p>
			<p><pre>QQ</pre><input type="text"  name="in_qq"/><label></label></p>
			<p><pre>微信</pre><input type="text" name="in_wechat"/><label></label></p>
			<p><pre>电话</pre><input type="text" name="in_phone"/><label></label></p>
			<p><pre>来源</pre><input type="text" name="in_source"/><label></label></p>
			<p><pre>城市</pre><input type="text" name="in_city"/><label></label></p>
			<p><pre>目的</pre><input type="text" name="in_purpose"/><label></label></p>
			<p><pre>客户</pre><input type="text" name="in_iscus"/><label></label></p>
			<p><pre>跟踪</pre><input type="text" name="in_service"/><label></label></p>
			<p><pre>更新时间</pre><input type="text" name="in_updateTime"/><label></label></p>
			<p><pre>创建时间</pre><input type="text" name="in_time"/><label></label></p>
			
			<input type="submit" value="提交" class="bt"/>
			<input type="reset" class="bt" value="重置" />
		</form>
		
	</div>
</body>
</html>