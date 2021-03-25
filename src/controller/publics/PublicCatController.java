package controller.publics;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daos.CategoryDAO;
import daos.SongDAO;
import models.Category;
import models.Songs;
import utils.DefineUtil;

/**
 * Servlet implementation class publicIndexController
 */
@WebServlet("/publicCatController")
public class PublicCatController extends HttpServlet {
	private static final long serialVersionUID = 1L;
      private CategoryDAO categoryDAO;
      private SongDAO songDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PublicCatController() {
        super();
        categoryDAO = new CategoryDAO();
        songDAO = new  SongDAO();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int id = 0;
		int currentPage = 1;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		}catch (Exception e) {
			// TODO: handle exception
			response.sendRedirect(request.getContextPath()+ "/publicNotFound");
			return;
		}
		
		try {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}catch (Exception e) {
			currentPage = 1;
		}
		
		Category category = categoryDAO.getItem(id);
		if(category == null) {
			response.sendRedirect(request.getContextPath()+ "/publicNotFound");
			return;
		}
		
		int numberOfItems = songDAO.numberOfItems(id);
		int numberOfPages =(int) Math.ceil((float)numberOfItems / DefineUtil.NUMBER_PER_PAGE);
		
		if(currentPage > numberOfPages || currentPage < 1) {
			currentPage =1;
		}
		
		int offset = (currentPage -1 ) *DefineUtil.NUMBER_PER_PAGE;
		
		ArrayList<Songs> item= songDAO.getAllByCatPaginatioon(offset,id);
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("category", category);
		request.setAttribute("songs", item);
		RequestDispatcher rd = request.getRequestDispatcher("views/public/cat.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
