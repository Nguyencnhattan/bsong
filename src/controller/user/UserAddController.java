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
 * Servlet implementation class userAddController
 */
@WebServlet("/userAddController")
public class UserAddController extends HttpServlet {
	private static final long serialVersionUID = 1L;
      private UserDAO userDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserAddController() {
        super();
        userDAO = new UserDAO();
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
		
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		
		if(!"admin".equals(userLogin.getUsername())) {
				response.sendRedirect(request.getContextPath()+ "/userIndexController?msg="+GlobalConstant.ERR);
				return;
		}
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		//request.setCharacterEncoding("UTF-8");
		RequestDispatcher rd = request.getRequestDispatcher("views/admin/user/addUser.jsp");
		rd.forward(request, response);
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
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		if(!"admin".equals(userLogin.getUsername())) {
			response.sendRedirect(request.getContextPath()+ "/userIndexController?msg="+GlobalConstant.ERR);
			return;
		}
		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String fullname = request.getParameter("fullname");
		if(userDAO.hasUser(username)) {
			RequestDispatcher rd = request.getRequestDispatcher("/views/admin/user/addUser.jsp?msg="+GlobalConstant.HASURES);
			rd.forward(request, response);
			return;
		}
		password = StringUtil.md5(password);
		User item = new User(0,username,password,fullname);
		
		if(userDAO.addItem(item)>0) {
			response.sendRedirect(request.getContextPath()+ "/userIndexController?msg="+GlobalConstant.SUCCESS);
		}else {
			response.sendRedirect(request.getContextPath()+ "/userAddController?msg="+GlobalConstant.ERROR);
		}
		
	}
}
