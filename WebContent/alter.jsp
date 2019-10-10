<%@page import="java.util.HashMap"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.ArrayList"%>
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

<%
	String id=request.getParameter("id");
	CustomerInfo info=new CustomerInfo();
	info.setId(Long.parseLong(id));
	CustomService cs=new CustomServiceImpl();
	ArrayList list=(ArrayList)cs.selectOne(info);
	System.out.println(info.toString());
	HashMap map=null;
   for(int i=0;i<list.size();i++){
	   System.out.println(list.get(i)+"----");
	   map=(HashMap)list.get(i);
	   /* for(Object obj:map.keySet()){
		   System.out.println(obj+"----"+map.get(obj));
	   } */
   }
	
%>
<body>
	<div id="all">
		<form action="alterCustomerInfo?id=<%=map.get(0)%>" method="post"  >
			<h2 >修改用户信息</h2>
			<p><pre>姓名</pre><input type="text" name="in_name" value="<%=map.get(1)%>"/><label></label></p>
			<p><pre>学历</pre><input type="text" name="in_degree" value="<%=map.get(2)%>"/><label></label></p>
			<p><pre>性别</pre><input type="text" name="in_sex" value="<%=map.get(3)%>"/><label></label></p>
			<p><pre>职业</pre><input type="text" name="in_career" value="<%=map.get(4)%>"/><label></label></p>
			<p><pre>QQ</pre><input type="text"  name="in_qq" value="<%=map.get(5)%>"/><label></label></p>
			<p><pre>微信</pre><input type="text" name="in_wechat" value="<%=map.get(6)%>"/><label></label></p>
			<p><pre>电话</pre><input type="text" name="in_phone" value="<%=map.get(7)%>"/><label></label></p>
			<p><pre>来源</pre><input type="text" name="in_source" value="<%=map.get(8)%>"/><label></label></p>
			<p><pre>城市</pre><input type="text" name="in_city" value="<%=map.get(9)%>"/><label></label></p>
			<p><pre>目的</pre><input type="text" name="in_purpose" value="<%=map.get(10)%>"/><label></label></p>
			<p><pre>客户</pre><input type="text" name="in_iscus" value="<%=map.get(11)%>"/><label></label></p>
			<p><pre>跟踪</pre><input type="text" name="in_service" value="<%=map.get(12)%>"/><label></label></p>
			<p><pre>更新时间</pre><input type="text" name="in_updateTime" value="<%=map.get(13)%>"/><label></label></p>
			<p><pre>创建时间</pre><input type="text" name="in_time" value="<%=map.get(14)%>"/><label></label></p>
			
			<input type="submit" value="提交" class="bt"/>
			<input type="reset" class="bt" value="重置" />
		</form>
		
	</div>
</body>
</html>