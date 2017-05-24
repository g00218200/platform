//package com.green.bsp.servlet;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.green.bsp.controller.SalaryInfoController;
//
//@WebServlet(urlPatterns="/cloudbsp/*",description="从微信入口登陆")
//public class WXLoginServlet extends HttpServlet{
//	
//	private static final Logger LOGGER = LoggerFactory.getLogger(SalaryInfoController.class);
//
//	@Override
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// 微信加密签名
//		String msg_signature = request.getParameter("msg_signature");
//		// 时间戳
//		String timestamp = request.getParameter("timestamp");
//		// 随机数
//		String nonce = request.getParameter("nonce");
//		// 随机字符串
//		String echostr = request.getParameter("echostr");
//		// 打印请求地址
//		LOGGER.info("[WXLoginServlet:doGet]:request=" + request.getRequestURL());  
//		super.doGet(request, response);
//	}
//
//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		
//		LOGGER.info("[WXLoginServlet:doPost]:request=>>>>>>>>>>doPost()<<<<<<<<<<<");
//
//        resp.setContentType("text/html"); 
//
//        PrintWriter out =resp.getWriter(); 
//
//         out.println("<html>"); 
//
//       out.println("<head>"); 
//
//       out.println("<title>Hello World</title>"); 
//
//       out.println("</head>"); 
//
//        out.println("<body>"); 
//
//       out.println("<h1>这是：myServlet2</h1>"); 
//
//       out.println("</body>"); 
//
//       out.println("</html>");
//	}
//
//}
