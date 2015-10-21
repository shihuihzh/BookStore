package com.togogo.book;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.togogo.bean.Book;
import com.togogo.bean.User;
import com.togogo.common.BookStoreConstants;

/**
 * 书本相关处理sevlet
 */
public class BookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	BookDao dao = new BookDao();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookServlet() {
        super();
    }
    
    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		PrintWriter out = response.getWriter();
		if(uri.endsWith("cart")) {
			doCart(request, response, out);
		} else if(uri.endsWith("addCart")) {
			doAddCart(request, response, out);
		} else if(uri.endsWith("delCart")) {
			doDelCart(request, response, out);
		} else if(uri.endsWith("index"))  {
			doIndex(request,response,out);
		}
	}


	/**
	 * 首页
	 * @param request
	 * @param response
	 * @param out
	 */
	private void doIndex(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		// 获得用户信息
		User user = (User) request.getSession().getAttribute(BookStoreConstants.USER_SESSION_KEY);
		
		
		// 获得所有书本
		List<Book> books = dao.getAllBook();

		// 建立Stringbuild拼接html代码
		StringBuilder html = new StringBuilder();
		html.append("<!DOCTYPE html>");
		html.append("<html>");
		html.append("<head>");
		html.append("	<title>Togogo 书店</title>");
		html.append("</head>");
		html.append("<body>");
		html.append("<a href=\"index\">主页</a> ");
		html.append("<a href=\"cart\">查看购物车</a>");
		if(user != null) {		// 如果用户已登录，显示用户信息
			html.append("<p>您好 "+user.getUsername() + " <a href='"+request.getContextPath()+"/user/index'>个人中心</a> <a href='"+request.getContextPath()+"/user/logout'>退出</a></p>");
		} else {
			html.append(" <a href=\""+request.getContextPath() + "/login.html\">登陆</a>");
		}
		html.append("<table border=\"1\" align=\"center\">");
		html.append("<caption>所有书本</caption>");
		html.append("<tr>");
		html.append("<td width='20%'>图片</td>");
		html.append("<td width='20%'>书名</td>");
		html.append("<td width='20%'>价格</td>");
		html.append("<td width='20%'>数量</td>");
		html.append("<td width='20%'>操作</td>");
		html.append("</tr>");
		for (Book b : books) {
			html.append("<tr>");
			html.append("<td width='20%'><image width='100' src='"+request.getContextPath()+b.getPic()+"'/></td>");
			html.append("<td width='20%'>" + b.getName() + "</td>");
			html.append("<td width='20%'>" + b.getPrice() + "</td>");
			html.append("<form action='addCart'>");
			html.append("<td width='20%'><input name='quntity' value='1'></td>");
			html.append("<td width='20%'><input type='hidden' name='bookId' value='"+b.getId()+"'><input type='submit' value='加入购物车' ></td></td>");
			html.append("</form>");
			html.append("</tr>");
		}

		html.append("</table><br>");
		html.append("</body>");
		html.append("</html>");

		out.println(html.toString());
		
	}

	/**
	 * 添加到购物车
	 * @param request
	 * @param response
	 * @param out
	 */
	private void doAddCart(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		// 获得id
		String id = request.getParameter("bookId");
		
		// 获得加入购物车数量
		Integer quntity = 1;
		try {
			quntity = Integer.parseInt(request.getParameter("quntity"));
		} catch (NumberFormatException e) {	// 转换失败，不处理
		}
		
		if(quntity < 1) {		// 不能为负数
			quntity = 1;
		}

		// 获得之前的cookie
		Cookie cookie = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) { // 判断空
			for (Cookie c : cookies) {
				if (c.getName().equals("cart")) {
					cookie = c;
				}
			}

		}

		// 建立一个cookie
		if (cookie != null) { //  格式(ID:数量,ID2:数量) 例如(1:1,2:1,3:1,4:1,5:1 )
			String value = cookie.getValue();
			String newValue = null;
			Map<String, String> cartMap = cartStringToMap(value);
			
			// 设置值
			cartMap.put(id, quntity+"");
			
			// map变回string
			newValue = mapToCartString(cartMap);
			

			// 设置新值到cookie
			cookie.setValue(newValue);

		} else {

			// 如过不存在老cookie，新建一个cookie
			cookie = new Cookie("cart", id+":"+quntity);
		}

		// 把cookie返回
		response.addCookie(cookie);
		// 返回页面给浏览器
		out.println("添加到商品购物车成功！<a href=\"cart\">查看购物车</a>");

	}

	/**
	 * map转换购物车字符串
	 *  格式(ID:数量,ID2:数量) 例如(1:1,2:1,3:1,4:1,5:1 )
	 * @param value
	 * @return 
	 */
	private String mapToCartString(Map<String, String> cart) {
		if(cart.isEmpty()) {		// 如果<a href=\"index\">主页</a><br>购物车为空直接返回空
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		
		// 迭代
		Set<Entry<String, String>> entrys = cart.entrySet();
		for(Entry<String, String> e : entrys) {
			sb.append(","+e.getKey() + ":" + e.getValue());
		}
	
		return sb.substring(1);
	}

	/**
	 * 购物车字符串转换map
	 *  格式(ID:数量,ID2:数量) 例如(1:1,2:1,3:1,4:1,5:1 )
	 * @param value
	 * @return
	 */
	private Map<String, String> cartStringToMap(String value) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		if(!value.trim().isEmpty()) {	// 判断value有值
			for(String str : value.split(",")) {	// 逗号分割每一个商品
				String[] b = str.split(":");		// 冒号分割，下标0为id，1为数量
				map.put(b[0], b[1]);
			}
		}
		return map;
	}
	
	

	/**
	 * 删除购物车
	 * @param request
	 * @param response
	 * @param out
	 * @throws IOException 
	 */
	private void doDelCart(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
		// 拿到名称为cart的cookie
		Cookie cookie = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) { // 判断空
			for (Cookie c : cookies) {
				if (c.getName().equals("cart")) {
					cookie = c;
				}
			}
		} else { // 客户端完全没有cookie
			out.print("<a href=\"index\">主页</a> <br>购物车为空");
			return;
		}
		
		// 获得bookId
		String bookId = request.getParameter("bookId");
		if("a".equals(bookId)) {  // 清空购物车
			cookie.setValue("");
			
		} else {
			// 转换
			Map<String, String> cartMap = cartStringToMap(cookie.getValue());
			
			// 删除
			cartMap.remove(bookId);
			
			// 转换回string
			cookie.setValue(mapToCartString(cartMap));
		}
		
		// 返回行cookie
		response.addCookie(cookie);
		response.sendRedirect(request.getContextPath()+"/book/cart");
		
		
	}

	/**
	 * 购物车页面
	 * @param request
	 * @param response
	 * @param out
	 */
	private void doCart(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		// 获得用户信息
		User user = (User) request.getSession().getAttribute(BookStoreConstants.USER_SESSION_KEY);
		
		
		// 拿到名称为cart的cookie
		Cookie cookie = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) { // 判断空
			for (Cookie c : cookies) {
				if (c.getName().equals("cart")) {
					cookie = c;
				}
			}
		} else { // 客户端完全没有cookie
			out.print("<a href=\"index\">主页</a> <br>购物车为空");
			return;
		}

		if (cookie == null || cookie.getValue().trim().equals("")) { // 不存在名为cart的cookie
			out.print("<a href=\"index\">主页</a> <br>购物车为空");
		} else {
			// 通过cookie的值拿到所有的book
			Map<String, String> cartMap = cartStringToMap(cookie.getValue());
			Map<Book, String> cartBookMap = new HashMap<Book, String>();

			// 通过数据库拿到所有的书
			for (String id : cartMap.keySet()) {
				Book book = dao.findByIds(id);
				if (book != null) {
					cartBookMap.put(book, cartMap.get(id));
				}
			}

			// 建立Stringbuild拼接html代码
			StringBuilder html = new StringBuilder();
			html.append("<!DOCTYPE html>");
			html.append("<html>");
			html.append("<head>");
			html.append("	<title>Togogo 书店</title>");
			html.append("</head>");
			html.append("<body>");
			html.append("<a href=\"index\">主页</a> ");
			html.append("<a href=\"cart\">查看购物车</a>");
			if(user != null) {		// 如果用户已登录，显示用户信息
				html.append("<p>您好 "+user.getUsername() + " <a href='"+request.getContextPath()+"/user/index'>个人中心</a> <a href='"+request.getContextPath()+"/user/logout'>退出</a></p>");
			} else {
				html.append(" <a href=\""+request.getContextPath() + "/login.html\">登陆</a>");
			}
			html.append("	<table border=\"1\" align=\"center\">");
			html.append("<caption>购物车</caption>");
			html.append("<tr>");
			html.append("<td width='20%'>图片</td>");
			html.append("<td width='20%'>书名</td>");
			html.append("<td width='20%'>价格</td>");
			html.append("<td width='20%'>数量</td>");
			html.append("<td width='20%'>操作</td>");
			html.append("</tr>");
			for (Book b : cartBookMap.keySet()) {
				html.append("<tr>");
				html.append("<td width='20%'><image width='100' src='" + request.getContextPath() + b.getPic()
						+ "'/></td>");
				html.append("<td width='20%'>" + b.getName() + "</td>");
				html.append("<td width='20%'>" + b.getPrice() + "</td>");
				html.append("<td width='20%'>" + cartBookMap.get(b) + "</td>");
				html.append("<td width='20%'><form action='delCart'><input type='hidden' name='bookId' value='"
						+ b.getId() + "'><input type='submit' value='删除' ></td></form></td>");
				html.append("</tr>");
			}

			html.append("<tr>");
			html.append(
					"<td colspan='5'><form action='delCart'><input type='hidden' name='bookId' value='a'><input  style='float:right' type='submit' value='清空购物车' ></td></form></td>");
			html.append("</table>");
			html.append("</body>");
			html.append("</html>");

			out.println(html.toString());
		}

	}

	

}
