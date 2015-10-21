package com.togogo.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.togogo.bean.Book;
import com.togogo.bean.User;
import com.togogo.common.BookStoreConstants;
import com.togogo.common.CommUtil;

/**
 * 
 * 用户相关servlet
 * 
 * @author Zhanhao Wong
 *
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 8087866488739298925L;
	UserDao userDao = new UserDao();

	public UserServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uri = request.getRequestURI();
		PrintWriter out = response.getWriter();
		if (uri.endsWith("login.do")) {
			doLogin(request, response, out);
		} else if (uri.endsWith("reg.do")) {
			doReg(request, response, out);
		} else if (uri.endsWith("index")) {
			doIndex(request, response, out);
		} else if (uri.endsWith("logout")) {
			doLogout(request, response, out);
		}
	}

	
	/**
	 * 个人中心
	 * 
	 * @param request
	 * @param response
	 * @param out
	 */
	private void doIndex(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		
		// 获得用户信息
		User user = (User) request.getSession().getAttribute(BookStoreConstants.USER_SESSION_KEY);
				
				
		// 建立Stringbuild拼接html代码
		StringBuilder html = new StringBuilder();
		html.append("<!DOCTYPE html>");
		html.append("<html>");
		html.append("<head>");
		html.append("	<title>Togogo 书店</title>");
		html.append("</head>");
		html.append("<body>");
		html.append("<a href=\""+request.getContextPath()+"/book/index\">主页</a> ");
		html.append("<a href=\""+request.getContextPath()+"/book/cart\">查看购物车</a>");
		html.append("<h1>个人中心</h1>");
		html.append("<p>您好 "+user.getUsername() +" <a href='logout'>退出</a></p>");
		html.append("</body>");
		html.append("</html>");

		out.println(html.toString());

	}

	/**
	 * 注册
	 * 
	 * @param request
	 * @param response
	 * @param out
	 */
	private void doReg(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");

		if (!password.equals(password2)) {
			out.println("<h1>两次密码不一致</h1>");
		}

		if (CommUtil.checkBlank(password)) {
			out.println("<h1>密码不能为空</h1>");
		}

		if (CommUtil.checkBlank(username)) {
			out.println("<h1>用户名不能为空</h1>");
		}

		User user = new User();
		user.setUsername(username);
		user.setId(CommUtil.UUID());
		user.setPassword(CommUtil.MD5(password));

		userDao.save(user);

		out.println("<h1>注册成功!</h1> <a href=\"login.html\">登陆</a>");

	}

	/**
	 * 登陆
	 * 
	 * @param request
	 * @param response
	 * @param writer
	 * @throws IOException
	 */
	private void doLogin(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		if (CommUtil.checkBlank(password)) {
			out.println("<h1>密码不能为空</h1>");
		}

		if (CommUtil.checkBlank(username)) {
			out.println("<h1>用户名不能为空</h1>");
		}

		User user = userDao.findByUsername(username);

		if (user != null && CommUtil.MD5(password).equals(user.getPassword())) { // 如果查找成功并且密码匹配，登陆成功

			// 把user存进session
			request.getSession().setAttribute(BookStoreConstants.USER_SESSION_KEY, user);

			// 重定向到书店主页
			response.sendRedirect(request.getContextPath() + "/book/index");

		} else { // 登陆失败
			out.println("<h1>登陆失败，用户名或者密码错误！</h1>");
		}

	}
	
	/**
	 * 
	 * 退出登录
	 * @param request
	 * @param response
	 * @param out
	 * @throws IOException 
	 */
	private void doLogout(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
		
		// 把session中保存的user清空
		request.getSession().setAttribute(BookStoreConstants.USER_SESSION_KEY, null);
		
		// 重定向到书店主页
		response.sendRedirect(request.getContextPath() + "/book/index");
		
	}


}
