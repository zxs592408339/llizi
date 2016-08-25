package com.ittx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=utf-8");
		String str = readFile();
		System.out.println("doGet :"+str);
		PrintWriter pw = resp.getWriter();
		pw.print(str);
		pw.flush();
		pw.close();
	}

	public String readFile() {
		StringBuilder sb = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			String fileDir = "E:/test.txt";
			File file = new File(fileDir);
			FileInputStream fis = new FileInputStream(file);
			isr = new InputStreamReader(fis,"UTF-8");
//			fr = new FileReader(file);
			br = new BufferedReader(isr);

			sb = new StringBuilder();
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				br.close();
				isr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return sb.toString();
	}
}
