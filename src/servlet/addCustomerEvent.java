package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.CustomerEvent;
import entity.CustomerInfo;
import orm.MyException;
import service.CustomService;
import service.CustomServiceImpl;

/**
 * Servlet implementation class addCustomerInfo
 */
@WebServlet("/addCustomerEvent")
public class addCustomerEvent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public addCustomerEvent() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		writer.append("Served at: ").append(request.getContextPath());
		String content = request.getParameter("event_content");
		String apply = request.getParameter("event_apply");
		String next= request.getParameter("event_next");
		String updateTime = request.getParameter("update_time");
		String time = request.getParameter("event_time");
		String infoId = request.getParameter("info_id");
		
		
		CustomerEvent event=new CustomerEvent();
		event.setContent(content);
		event.setApply(apply);
		event.setNext(next);
		event.setUpdateTime(new Timestamp(445566));
		event.setDate(new java.sql.Date(100,8,8));
		event.setInfoId(Integer.parseInt(infoId));
		
		
		
//		writer.println(name+"----");
		CustomService cs=new CustomServiceImpl();
		try {
			event.toString();
			cs.add(event);
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
			event.toString();
		}
		
//		request.getRequestDispatcher("cusList.jsp").forward(request, response);
		response.sendRedirect("cusInfo.jsp?id="+event.getInfoId());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
