package controller.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daos.CategoryDAO;
import daos.SongDAO;
import daos.UserDAO;
import utils.AuthUtil;

/**
 * Servlet implementation class index
 */
@WebServlet("/index")
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;
      private SongDAO songDAO;
      private UserDAO userDAO;
      private CategoryDAO categoryDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Index() {
        super();
        songDAO = new SongDAO();
        userDAO = new UserDAO();
        categoryDAO = new CategoryDAO();
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
		int numberSongs = (int) songDAO.numberOfItems();
		int numberCat = (int) categoryDAO.numberOfItems();
		int numberUser = (int) userDAO.numberOfItems();
		request.setAttribute("numberSongs", numberSongs);
		request.setAttribute("numberCat", numberCat);
		request.setAttribute("numberUser", numberUser);
		RequestDispatcher rd = request.getRequestDispatcher("views/admin/index.jsp");
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
		//sout(request.getSerletContext().getRealPath(""));
	}

}
