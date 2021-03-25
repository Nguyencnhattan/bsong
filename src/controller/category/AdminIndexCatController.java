package controller.category;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daos.CategoryDAO;
import models.Category;

import utils.AuthUtil;
import utils.DefineUtil;

/**
 * Servlet implementation class publicIndexController
 */
@WebServlet("/adminIndexCatController")
public class AdminIndexCatController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CategoryDAO categoryDAO;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminIndexCatController() {
		super();
		categoryDAO = new CategoryDAO();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath()+ "/login");
			return;
		}
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		String search = request.getParameter("search");
		int numberOfItems = 0;
		if(search != null) {
			numberOfItems= categoryDAO.numberOfItemsSearch(search);
		}else {
			numberOfItems= categoryDAO.numberOfItems();
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
		
		// TODO Auto-generated method stub
		CategoryDAO categoryDAO = new CategoryDAO();
		List<Category> categories= null;
		if(search != null) {
			System.out.println(search);
			 categories= categoryDAO.getSearch(search, offset);
			 System.out.println(categories);
		}else {
			categories = categoryDAO.getItemsPagination(offset);}
		int numberCat = (int) categoryDAO.numberOfItems();
		System.out.println(categories);
		request.setAttribute("numberCat", numberCat);
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("categories", categories);
		RequestDispatcher rd = request.getRequestDispatcher("views/admin/cat/indexCat.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath()+ "/login");
			return;
		}
		doGet(request, response);
	}

}
