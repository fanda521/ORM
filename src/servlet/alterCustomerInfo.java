package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.CustomerInfo;
import orm.MyException;
import service.CustomService;
import service.CustomServiceImpl;

/**
 * Servlet implementation class addCustomerInfo
 */
@WebServlet("/alterCustomerInfo")
public class alterCustomerInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public alterCustomerInfo() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		writer.append("Served at: ").append(request.getContextPath());
		
		int id=Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("in_name");
		String degree = request.getParameter("in_degree");
		String sex= request.getParameter("in_sex");
		String career = request.getParameter("in_career");
		String qq = request.getParameter("in_qq");
		String wechat = request.getParameter("in_wechat");
		String phone = request.getParameter("in_phone");
		String source = request.getParameter("in_source");
		String city = request.getParameter("in_city");
		String purpose = request.getParameter("in_purpose");
		String iscus = request.getParameter("in_iscus");
		String service = request.getParameter("in_service");
		String updateTime = request.getParameter("in_updateTime");
		String time = request.getParameter("in_time");
		String s="y-M-d h:m:s a E";
		String s3="y-M-d h";
		String s2="y年M月d日 晚上h时m分s秒 E";
		
		SimpleDateFormat sdf1=new SimpleDateFormat(s);
		SimpleDateFormat sdf2=new SimpleDateFormat(s3);
//		System.out.println(sdf1.format(new Date()));
//		System.out.println(sdf1.format(time));
		
		CustomerInfo info=new CustomerInfo();
		info.setId(id);
		info.setName(name);
		info.setDegree(degree);
		info.setSex(sex);
		info.setCareer(career);
		info.setQq(qq);
		info.setWeChat(wechat);
		info.setPhone(phone);
		info.setSource(source);
		info.setCity(city);
		info.setPurpose(purpose);
		info.setIsCus(Integer.parseInt(iscus));
		info.setService(Integer.parseInt(service));
		info.setUpdateTime(new Timestamp(22344555));
//		info.setUpdateTime(new Timestamp);
		info.setDate(new java.sql.Date(100,8,9 ));
		
		
//		writer.println(name+"----");
		CustomService cs=new CustomServiceImpl();
		try {
			info.toString();
			cs.update(info);
		} catch (MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			info.toString();
		}
		
//		request.getRequestDispatcher("cusList.jsp").forward(request, response);
		response.sendRedirect("cusList.jsp");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
