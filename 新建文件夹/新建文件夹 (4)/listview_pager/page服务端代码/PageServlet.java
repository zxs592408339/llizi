package com.scxh.java1503.page;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

public class PageServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=utf-8");
		
		String pageNo = req.getParameter("pageNo");//页码
		String pageSize = req.getParameter("pageSize");//每页大小
		
		int totalRecord = 102;
		ArrayList<String> list = new ArrayList<String>();
		for(int i = 0; i< totalRecord; i++){
			list.add("第"+i+" 条 土耳其击落俄战机");
		}
		
		PageUtil pageUtil = new PageUtil(totalRecord,Integer.parseInt(pageSize),Integer.parseInt(pageNo));
		int fromIndex = pageUtil.getFormIndex();
		int endIndex = pageUtil.getEndIndex();
		System.out.println("fromIndex :"+fromIndex+ ", endIndex :"+endIndex);
		
		
		List<String> sublist = list.subList(fromIndex, endIndex);
		
		/**
		 *    {code:1,
		 *     message:"成功！",
		 *     content:[
		 *       {msg:"第1 条 土耳其击落俄战机 俄确认一名飞行员遇害"}
		 *       {msg:"第2 条 土耳其击落俄战机 俄确认一名飞行员遇害"}
		 *     ]
		 *    }
		 *    -------------------------------------------
		 *     [
		 *       {msg:"第1 条 土耳其击落俄战机 俄确认一名飞行员遇害"}
		 *       {msg:"第2 条 土耳其击落俄战机 俄确认一名飞行员遇害"}
		 *     ]
		 *    
		 */
		
		JSONArray array = new JSONArray();
		for(int i = 0; i<sublist.size(); i++){
			array.put(i, new JSONObject().put("msg", sublist.get(i)));
		}
		
		JSONObject jsongObject = new JSONObject();
		jsongObject.put("code", 1);
		jsongObject.put("message", "成功!");
		jsongObject.put("content", array);
		jsongObject.put("pagecount", pageUtil.getPageCount());
		
		String jsonStr = jsongObject.toString();
		
		PrintWriter writer = resp.getWriter();
		writer.print(jsonStr);
		writer.flush();
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
