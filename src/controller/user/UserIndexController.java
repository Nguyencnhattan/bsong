package controller.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daos.ContactDAO;
import daos.UserDAO;
import models.Contact;
import models.User;
import utils.AuthUtil;
import utils.DefineUtil;

/**
 * Servlet implementation class userIndexController
 */
@WebServlet("/userIndexController")
public class UserIndexController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    	  
    private UserDAO userDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserIndexController() {
        super();
        userDAO = new UserDAO();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath()+ "/login");
			return;
		}
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		//request.setCharacterEncoding("UTF-8");
		
		String search = request.getParameter("search");
		int numberOfItems = 0;
		if(search != null) {
			numberOfItems= userDAO.numberOfItemsSearch(search);
		}else {
			numberOfItems= userDAO.numberOfItems();
		}
		int numberOfPages = (int) Math.ceil( (float) numberOfItems / DefineUtil.NUMBER_PER_PAGE);
		
		int currentPage =	1;
		try {
			currentPage = Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException e) {
			// TODO: handle exception
		}
		
		if (currentPage > numberOfPages || currentPage <1) {
			currentPage = 1;
		}
		
		int offset = (currentPage - 1) * DefineUtil.NUMBER_PER_PAGE;
		UserDAO userDAO = new UserDAO();
		List<User> users= null;
		if(search != null) {
			System.out.println(search);
			users= userDAO.getSearch(search, offset);
			 System.out.println(users);
		}else {
			users = userDAO.getItemsPagination(offset);
		}	
		int numberUser = (int) userDAO.numberOfItems();
		
		
		request.setAttribute("numberUser", numberUser);
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("currentPage", currentPage);		
		
		request.setAttribute("user", users);
		RequestDispatcher rd = request.getRequestDispatcher("views/admin/user/indexUser.jsp");
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
		doGet(request, response);
	}

}
