package controller.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import constants.GlobalConstant;
import daos.UserDAO;
import models.User;
import utils.AuthUtil;
import utils.StringUtil;



/**
 * Servlet implementation class userEditController
 */
@WebServlet("/userEditController")
public class UserEditController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	private UserDAO userDAO;
    public UserEditController() {
        super();
        userDAO = new  UserDAO();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath()+ "/login");
			return;
		}
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));	
		}catch (Exception e) {
			response.sendRedirect(request.getContextPath()+ "/userIndexController?msg="+GlobalConstant.ERRORID);
			return;
			// TODO: handle exception
		}
		
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		if("admin".equals(userDAO.getItem(userLogin.getId()).getUsername()) || (id == userLogin.getId())){
			User item = userDAO.getItem(id);
			if(item != null) {
			request.setAttribute("user", item);
			RequestDispatcher rd = request.getRequestDispatcher("views/admin/user/editUser.jsp");
			rd.forward(request, response);
			} else {
				
			}
		} else {
			response.sendRedirect(request.getContextPath()+ "/userIndexController?msg="+GlobalConstant.ERR);
			return;
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath()+ "/login");
			return;
		}
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));	
		}catch (Exception e) {
			response.sendRedirect(request.getContextPath()+ "/userIndexController?msg="+GlobalConstant.ERRORID);
			return;
			// TODO: handle exception
		}
		
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		if("admin".equals(userDAO.getItem(userLogin.getId()).getUsername()) || (id == userLogin.getId())){
			User item = userDAO.getItem(id);
			String password = request.getParameter("password");
			if("".equals(password)) {
				password=item.getPassword();
			}else {
				password=StringUtil.md5(password);
			}
			String fullname = request.getParameter("fullname");
			item.setPassword(password);
			item.setFullname(fullname);
			if(userDAO.editItem(item)>0) {
				response.sendRedirect(request.getContextPath()+ "/userIndexController?msg="+GlobalConstant.EDITSUCCESS);
				return;
			}else {
				RequestDispatcher rd = request.getRequestDispatcher("views/admin/user/editUser.jsp?msg="+GlobalConstant.EDITERROR);
				rd.forward(request, response);
				return;
			}
		}else {
			response.sendRedirect(request.getContextPath()+ "/userIndexController?msg="+GlobalConstant.ERR);
			return;
		}
		
		
	}

}
