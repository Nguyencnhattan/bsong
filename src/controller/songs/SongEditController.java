package controller.songs;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


import constants.GlobalConstant;
import daos.CategoryDAO;
import daos.SongDAO;
import models.Category;
import models.Songs;
import utils.AuthUtil;
import utils.FileUtil;

/**
 * Servlet implementation class songEditController
 */
@WebServlet("/songEditController")
@MultipartConfig
public class SongEditController extends HttpServlet {
	private static final long serialVersionUID = 1L;
     private SongDAO songDAO;
     private CategoryDAO categoryDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SongEditController() {
        super();
        // TODO Auto-generated constructor stub
        songDAO = new SongDAO();
        categoryDAO = new CategoryDAO();
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
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));	
		}catch (Exception e) {
			response.sendRedirect(request.getContextPath()+ "/songIndexController?msg="+GlobalConstant.ERRORID);
			return;
			// TODO: handle exception
		}
		
		Songs song = songDAO.getItem(id);
		 System.out.println(song);
		if(song == null) {
				response.sendRedirect(request.getContextPath()+"/songIndexController?msg="+GlobalConstant.ERRORID);
				return;
		}
		
		CategoryDAO categoryDAO = new CategoryDAO();
		List<Category> categories = categoryDAO.getCategories();
		request.setAttribute("songs", song);
		request.setAttribute("categories", categories);
		
		RequestDispatcher rd = request.getRequestDispatcher("views/admin/song/editSong.jsp");
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
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));	
		}catch (Exception e) {
			response.sendRedirect(request.getContextPath()+ "/songIndexController?msg="+GlobalConstant.ERRORID);
			return;
			// TODO: handle exception
		}
		
		String name = request.getParameter("name");
		String preview = request.getParameter("preview");
		String detail = request.getParameter("detail");
		
		Songs song = songDAO.getItem(id); 
		if(song == null) {
			response.sendRedirect(request.getContextPath()+"/songIndexController?msg="+GlobalConstant.ERRORID);
			return;
		}
	
		int catid = Integer.parseInt(request.getParameter("category"));
		//upload file bị lỗi 
		
		Part filePart = request.getPart("picture");
		//Lấy tên từ file
		String fileName = FileUtil.getName(filePart);
		// Đổi tên file
		
		String picture = "";
		if(fileName.isEmpty()) {
			picture=song.getPicture();
		}else {
			picture=FileUtil.rename(fileName);
		}
		
	
		final String dirPathName = request.getServletContext().getRealPath("/uploads");
		File dirFile = new File(dirPathName);
			if (!dirFile.exists()) {
				dirFile.mkdir();
			}
			
			String filePath = dirPathName + File.separator + picture;
			
			// Truyền vào đường dẫn upload file
			System.out.println("dirUploadPath :" + dirPathName);
		
			Category category = new Category(catid,null);
			Songs item = new Songs(id,name,preview,detail,picture,null,0,category);
		if(songDAO.editItem(item)>0) {
			if(!fileName.isEmpty()) {
				String oldFileName =dirPathName + File.separator + song.getPicture();
				File oldFile = new File (oldFileName);
				if(oldFile.exists()) {
					oldFile.delete();
				}
			filePart.write(filePath);
			}
			response.sendRedirect(request.getContextPath()+ "/songIndexController?msg="+GlobalConstant.EDITSUCCESS);
			return;
		}else {
			List<Category> categories = categoryDAO.getCategories();
			request.setAttribute("categories", categories);
			RequestDispatcher rd = request.getRequestDispatcher("views/admin/song/editSong.jsp");
			rd.forward(request, response);
		}
		
	}

}
