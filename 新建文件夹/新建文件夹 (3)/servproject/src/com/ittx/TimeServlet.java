package com.ittx;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 
 * 接收并响应客户端请求
 *
 */
public class TimeServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("TimeServlet  doGet >>>>>>");
		
		String name = req.getParameter("userName");
		String password = req.getParameter("passWord");
		System.out.println("name >>>>>>"+name + " password :"+password);
		
		PrintWriter pw = resp.getWriter();
		if(name.equals("admin") && password.equals("123456")){
			pw.print("hello TimeServlet Login success ");
		}else{
			pw.print("hello TimeServlet Login fail ");
		}
		
		pw.flush();
		pw.close();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("TimeServlet  doPost >>>>>>");
//		doGet(req,resp);
		resp.sendRedirect("http://it.warmtel.com");
		
	}
}
