package controller.category;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import constants.GlobalConstant;
import daos.CategoryDAO;
import models.Category;
import utils.AuthUtil;

/**
 * Servlet implementation class publicIndexController
 */
@WebServlet("/adminAddCatController")
public class AdminAddCatController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminAddCatController() {
		super();
		
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath()+ "/login");
			return;
		}
		RequestDispatcher rd = request.getRequestDispatcher("views/admin/cat/addCat.jsp");
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
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		StringBuilder sbd = new StringBuilder();
		CategoryDAO categoryDAO = new CategoryDAO();
		String name = request.getParameter("name");
		System.out.println("name" + name);
		Category cat = new Category(name);
		if(categoryDAO.hasCat(name) == true) {
			RequestDispatcher rd = request.getRequestDispatcher("views/admin/cat/addCat.jsp?msg="+GlobalConstant.HASURES);
			rd.forward(request, response);
			return;
		}else {
		
		int countRecordInserted = categoryDAO.Add(cat);
		if (countRecordInserted > 0) {

			String url = sbd.append(request.getContextPath()).append("/adminIndexCatController?msg=")
					.append(GlobalConstant.SUCCESS).toString();
			response.sendRedirect(url);
			// success

		} else {
			RequestDispatcher rd = request.getRequestDispatcher("views/admin/cat/addCat.jsp?msg=" + GlobalConstant.ERROR);
			rd.forward(request, response);
		}
		}
	}

}
