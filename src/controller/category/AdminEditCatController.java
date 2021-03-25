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
 * Servlet implementation class adminEditCatController
 */
@WebServlet("/adminEditCatController")
public class AdminEditCatController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	private CategoryDAO catDAO;
	
    public AdminEditCatController() {
        super();
        
        catDAO = new CategoryDAO();
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
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));	
		}catch (Exception e) {
			response.sendRedirect(request.getContextPath()+ "/adminIndexCatController?msg="+GlobalConstant.ERRORID);
			return;
			// TODO: handle exception
		}
		Category item = catDAO.getItem(id);
		request.setAttribute("categories", item);
		RequestDispatcher rd = request.getRequestDispatcher("views/admin/cat/editCat.jsp");
		rd.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath()+ "/login");
			return;
		}
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));	
		}catch (Exception e) {
			response.sendRedirect(request.getContextPath()+ "/adminIndexCatController?msg="+GlobalConstant.ERRORID);
			return;
			// TODO: handle exception
		}
		Category item = catDAO.getItem(id);
		String name = request.getParameter("name");
		item.setName(name);
		if(catDAO.editItem(item)>0) {
			response.sendRedirect(request.getContextPath()+ "/adminIndexCatController?msg="+GlobalConstant.EDITSUCCESS);
			return;
		}else {
			RequestDispatcher rd = request.getRequestDispatcher("views/admin/user/editCat.jsp?msg="+GlobalConstant.EDITERROR);
			rd.forward(request, response);
			return;
		}
	}

}
