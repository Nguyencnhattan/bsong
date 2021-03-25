package controller.songs;

import java.io.IOException;
import java.util.List;

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
import utils.AuthUtil;
import utils.DefineUtil;

/**
 * Servlet implementation class songIndexController
 */
@WebServlet("/songIndexController")
public class SongIndexController extends HttpServlet {
	private static final long serialVersionUID = 1L;
     private SongDAO songDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SongIndexController() {
        super();
        songDAO = new SongDAO();
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
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		String search = request.getParameter("search");
		int numberOfItems = 0;
		if(search != null) {
			numberOfItems= songDAO.numberOfItemsSearch(search);
		}else {
			numberOfItems= songDAO.numberOfItems();
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
		SongDAO songDAO = new SongDAO();
		List<Songs> songs= null;
		if(search != null) {
			System.out.println(search);
			songs= songDAO.getSearch(search, offset);
			 System.out.println(songs);
		}else {
			songs = songDAO.getItemsPagination(offset);
		}	
		int numberSongs = (int) songDAO.numberOfItems();
		request.setAttribute("numberSongs", numberSongs);
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("songs", songs);
		RequestDispatcher rd = request.getRequestDispatcher("views/admin/song/indexSong.jsp");
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
