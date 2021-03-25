package controller.songs;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import constants.GlobalConstant;
import daos.SongDAO;
import models.Songs;

/**
 * Servlet implementation class songDeleteController
 */
@WebServlet("/songDeleteController")
public class SongDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
      private SongDAO songDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SongDeleteController() {
        super();
        songDAO = new SongDAO();
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
			response.sendRedirect(request.getContextPath() +"/songIndexController?msg="+GlobalConstant.DELERROR);
			return;
			// TODO: handle exception
		}
		Songs songs = songDAO.getItem(id);
		if(songs== null ) {
			response.sendRedirect(request.getContextPath() +"/songIndexController?msg="+GlobalConstant.DELERROR);
			return;
		}
		if(songDAO.delItem(id)>0) {
			final String dirPartName =request.getServletContext().getRealPath("/uploads");
			String picture = songs.getPicture();
			if(!picture.isEmpty()) {
				String filaPathName = dirPartName + File.separator +picture;
				File file = new File(filaPathName);
				if(file.exists()) {
					file.delete();
				}
			}
			response.sendRedirect(request.getContextPath() +"/songIndexController?msg="+GlobalConstant.DELSUCCESS);
			return;
		}else {
			response.sendRedirect(request.getContextPath() +"/songIndexController?msg="+GlobalConstant.DELERROR);
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
