<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="service.CustomServiceImpl"%>
<%@page import="service.CustomService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="isLogin.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>客户详情</title>
<style type="text/css">
	div {
		/*  border: 1px solid red;*/
	}
	.info {width: 20%;float:left;text-align: center;}
	.event{width: 75%;float:left;}
	.event ul li{list-style: none;float:left;width: 15%}
	.event ul{clear:both;}
</style>
</head>
<script src="js/addCustomerEvent.js" type="text/javascript" charset="utf-8"></script>
<body>
<jsp:include page="header.jsp"></jsp:include><!--无效  -->
<!-- 获取你点击的客户的信息  客户信息-跟踪的记录 链接会传递id和name参数-->
	<%
		//获取传递的参数
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		
		CustomService service = new CustomServiceImpl();
		//key = map集合其实就是info表的信息        value = info_event的信息
		Map<Map<String, Object>, List<Map<String, Object>>> datas = service.getCustomInfo(id, name);
		
		if(null != datas){
			for(Map<String, Object> data : datas.keySet()){
		%>		<div class="info">
					<p><%=data.get("info_id") %></p>
					<p> <a href="cusInfo.jsp?name=<%=data.get("info_name") %>&id=<%=data.get("info_id") %>"><%=data.get("info_name") %></a>  </p>
					<p><%=data.get("info_degree") %></p>
					<p><%=data.get("info_sex") %></p>
					<p><%=data.get("info_career") %></p>
					<p><%=data.get("info_qq") %></p>
					<p><%=data.get("info_wechat") %></p>
					<p><%=data.get("info_phone") %></p>
					<p><%=data.get("info_source") %></p>
					<p><%=data.get("info_purpose") %></p>
					<p><%= (int)data.get("info_iscus") == 0 ? "潜在客户" : "客户" %></p> 
					<p><%=(int)data.get("info_service")  == 1 ? "需跟进" : "不需要跟进" %></p>
				</div>
				<div class="event">
				<ul>
						<li>id<li>
						<li>回访内容<li>
						<li>回复内容<li>
						<li>下一次跟踪内容<li>
						<li>更新时间<li>
						<li>回访时间<li>
					</ul>
	<%
				List<Map<String, Object>> events = datas.get(data);//event表的信息
				for(Map<String, Object> event : events){
	%>			
					<ul>
						<li><%=event.get("event_id") %> <li>
						<li><%=event.get("event_content") %><li>
						<li><%=event.get("event_apply") %><li>
						<li><%=event.get("event_next") %><li>
						<li><%=event.get("update_time") %><li>
						<li><%=event.get("event_time") %><li>
					</ul>
	<%			
				}
			}
		} else {
			out.write("数据不存在!!!");
		}
	%>
			</div>
			
			<input type="button" value="添加回访记录" id="bt_event">
</body>
</html>