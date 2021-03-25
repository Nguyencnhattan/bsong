package controller.publics;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import daos.SongDAO;
import models.Songs;

/**
 * Servlet implementation class publicIndexController
 */
@WebServlet("/publicDetailController")
public class PublicDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SongDAO songDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PublicDetailController() {
        super();
        songDAO = new  SongDAO();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		}catch (Exception e) {
			// TODO: handle exception
			response.sendRedirect(request.getContextPath()+ "/publicNotFound");
			return;
		}
		Songs item = songDAO.getItem(id);
		if(item == null) {
			response.sendRedirect(request.getContextPath()+ "/publicNotFound");
			return;
		}
		HttpSession session = request.getSession();
		String hasVisited = (String) session.getAttribute("hasvisited: "+ id);
		if(hasVisited == null) {
			session.setAttribute("hasvisited: "+ id, "yes");
			session.setMaxInactiveInterval(3600);
			songDAO.increaseView(id);
		}
		
		ArrayList<Songs> songs = songDAO.getRelatedItem(item, 5);
		request.setAttribute("songs", item);
		request.setAttribute("relatedsongs", songs);
		RequestDispatcher rd = request.getRequestDispatcher("views/public/detail.jsp");
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
