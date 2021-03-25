package controller.admin;

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
import utils.StringUtil;

/**
 * Servlet implementation class index
 */
@WebServlet("/login")
public class AuthLoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserDAO userDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthLoginController() {
        super();
        userDAO = new UserDAO();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		
		if(userLogin != null) {
			response.sendRedirect(request.getContextPath()+ "/index");
			return;
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("views/auth/login.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		
		if(userLogin != null) {
			response.sendRedirect(request.getContextPath()+ "/index");
			return;
		}
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		password = StringUtil.md5(password);
		User user = userDAO.existUser(username,password);
		System.out.println(username +""+password);
		if(user != null) {
			session.setAttribute("userLogin", user);
			
			response.sendRedirect(request.getContextPath()+ "/index");
			return;
		}else {
			response.sendRedirect(request.getContextPath()+ "/login?msg="+GlobalConstant.ERROR);
			return;
		}
	}

}
