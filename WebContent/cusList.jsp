<%@page import="entity.CustomerInfo"%>
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
<title>客户信息的列表页面</title>

<!-- <script src="js/alter.js" type="text/javascript" charset="utf-8"></script> -->
<script src="js/addCustomer.js" type="text/javascript" charset="utf-8"></script>
</head>
<style type="text/css" >
body{
	margin:0 auto;
}
#all{
/*  border:1px solid red;*/
margin:0 auto;
text-align: center;
}
#tb{
margin: auto ;
margin-left:20%;
text-align: center;
}
th,td{
width:80px;

}
a{
	text-align: center;
	text-decoration: none;
	display: inline-block;
	margin: 10px;
}
</style>
<body>
<jsp:include page="header.jsp"></jsp:include>
	<div id="all">
		<%
			CustomService service = new CustomServiceImpl();
			long totals=0l;//总的记录条数
			long currentPage=1l;//当前页面号,默认的值
			long pagesPer=4l;//每页记录条数
			String temp=request.getParameter("currentPage");
			long pageNum=0l;
			if(temp!=null){
				pageNum=Long.parseLong(temp);
			}
			
			currentPage=pageNum;
			
			/*  List<Map<String, Object>> datas = service.getCustoms();
			分页前的做法
			*/
			String sqlTotals="select count(*) num from cus_info where info_phone!='' or info_wechat!=null or info_qq!=''";
			String sqlLimt="select * from cus_info limit ?,?";
			totals=service.getTotals(sqlTotals);
			long pages=(totals+pagesPer-1)/pagesPer;//总共分多少页
			if(pageNum>pages){
					currentPage=pages ;		
			}
			
			if(pageNum<1){
				currentPage=1;		
		}
			
			List<CustomerInfo> datas=service.getDatasLimt(CustomerInfo.class, sqlLimt, 
					new Object[]{(currentPage-1)*pagesPer,pagesPer});
		%>
		<p>
		<input type="button" value="添加客户信息"  id="bt_addCus"></p>
		
		<p>
		<input type="text" placeholder="输入手机号或者qq或者微信">
		<input type="button" value="查询"></p>
		<p><a href="cusList.jsp?currentPage=<%=currentPage-1%> ">上一页：<%=currentPage-1 %>　　</a><a href="cusList.jsp?currentPage=<%=currentPage+1%>">下一页：<%=currentPage+1 %>　　</a>总页数:　<%=pages %>　　当前页：<%=currentPage %>　　</p>
		<div id="tb">
			<table border="1" style="border: 1px solid black;border-collapse: collapse;width: 80%">
			<tr>
				<th>全选</th>
				<th>id</th>
				<th>姓名</th>
				<th>学历</th>
				<th>性别</th>
				<th>职业</th>
				<th>qq</th>
				<th>微信</th>
				<th>电话</th>
				<th>信息来源</th>
				<th>城市</th><!-- ///// -->
				<th>目的</th>
				<th>是否是客户</th>
				<th>是否跟踪</th>
				<th>更新时间</th><!-- ///// -->
				<th>录入时间</th>
				<th>操作</th>
			</tr>
			<% 
				for(CustomerInfo data : datas){
			%>	
					<tr>
						<td> <input type="checkbox" value=""> </td>
						<td id="user_id"><%=data.getId() %></td>
						<td> <a href="cusInfo.jsp?name=<%=data.getName() %>&id=<%=data.getId() %>"><%=data.getName() %></a>  </td>
						<td><%=data.getDegree() %></td>
						<td><%=data.getSex() %></td>
						<td><%=data.getCareer() %></td>
						<td><%=data.getQq() %></td>
						<td><%=data.getWeChat() %></td>
						<td><%=data.getPhone() %></td>
						<td><%=data.getSource() %></td>
						<td><%=data.getCity() %></td>
						<td><%=data.getPurpose() %></td>
						<td><%= data.getIsCus() == 0 ? "潜在客户" : "客户" %></td> 
						<td><%=data.getService()  == 1 ? "需跟进" : "不需要跟进" %></td>
						<td><%=data.getUpdateTime() == null ? "" :  data.getUpdateTime()%></td>
						<td><%=data.getDate() == null ? "" :  data.getDate()%></td>
						<td><input type="button" value="删除" name="aa" class="delete"> <input type="button" value="修改" class="alter"></td>
					</tr>
			<%
				}
			%>
			
		</table>
		 <%-- <%System.out.println(request.getParameter("aa")+"pppp"); %> --%> 
		</div>
	</div>
</body>
</html>