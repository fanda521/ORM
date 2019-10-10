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
<script src="js/add.js" type="text/javascript" charset="utf-8"></script>
<body>
<jsp:include page="header.jsp"></jsp:include>
	<div id="all">
		<form action="addCustomerEvent" method="post"  >
			<h2>添加记录信息</h2>
			<p><pre>内容</pre><input type="text" name="event_content"/><label></label></p>
			<p><pre>反馈</pre><input type="text" name="event_apply"/><label></label></p>
			<p><pre>下次回访建议</pre><input type="text" name="event_next" /><label></label></p>
			<p><pre>更新时间</pre><input type="text" name="update_time"/><label></label></p>
			<p><pre>回访时间</pre><input type="text"  name="event_time"/><label></label></p>
			<p><pre>客户ID</pre><input type="text" name="info_id"/><label></label></p>
			
			
			<input type="submit" value="提交" class="bt"/>
			<input type="reset" class="bt" value="重置" />
		</form>
		
	</div>
</body>
</html>