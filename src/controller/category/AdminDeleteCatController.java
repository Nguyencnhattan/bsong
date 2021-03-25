package controller.category;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import constants.GlobalConstant;
import daos.CategoryDAO;

/**
 * Servlet implementation class userDeleteController
 */
@WebServlet("/adminDeleteCatController")
public class AdminDeleteCatController extends HttpServlet {
	private static final long serialVersionUID = 1L;
     private CategoryDAO catDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminDeleteCatController() {
        super();
        catDAO = new CategoryDAO();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
			
		}catch (Exception e) {
			response.sendRedirect(request.getContextPath() +"/adminIndexCatController?msg="+GlobalConstant.DELERROR);
			return;
			// TODO: handle exception
		}
		if(catDAO.delItem(id)>0) {
			response.sendRedirect(request.getContextPath() +"/adminIndexCatController?msg="+GlobalConstant.DELSUCCESS);
			return;
		}else {
			response.sendRedirect(request.getContextPath() +"/adminIndexCatController?msg="+GlobalConstant.DELERROR);
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
