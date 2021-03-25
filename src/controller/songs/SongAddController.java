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
 * Servlet implementation class userAddController
 */
@WebServlet("/songAddController")
@MultipartConfig
public class SongAddController extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SongAddController() {
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
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		// request.setCharacterEncoding("UTF-8");
		CategoryDAO categoryDAO = new CategoryDAO();
		List<Category> categories = categoryDAO.getCategories();
		request.setAttribute("categories", categories);
		RequestDispatcher rd = request.getRequestDispatcher("views/admin/song/addSong.jsp");
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
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		SongDAO songDAO = new SongDAO();
		StringBuilder sbd = new StringBuilder();
		String name = request.getParameter("name");
		int catId = Integer.parseInt(request.getParameter("category"));
		
		String description = request.getParameter("preview");
		String detail = request.getParameter("detail");
		//upload
		Part filePart = request.getPart("picture");
		//lấy và đổi tên
		String fileName = FileUtil.getName(filePart);
		String picture = FileUtil.rename(fileName);
		System.out.println(picture);
		if (!"".equals(fileName)) {
			String rootPath = request.getServletContext().getRealPath("");
			String dirUploadPath = rootPath + "uploads";
			File createDir = new File(dirUploadPath);
			if (!createDir.exists()) {
				createDir.mkdir();
			}
			// string, string builder
			StringBuilder sb = new StringBuilder();
			String filePath = sb.append(dirUploadPath).append(File.separator).append(picture).toString();
			filePart.write(filePath); // Truyền vào đường dẫn upload file
			System.out.println("dirUploadPath :" + dirUploadPath);

		}
		//final String dirPathName = request.getServletContext().getRealPath("/files");
		//File dirFile = new File(dirPathName);
		Songs song = new Songs(name, description, detail, picture, new Category(catId));
		int countRecordInserted = songDAO.add(song);
		if (countRecordInserted > 0) {

			String url = sbd.append(request.getContextPath()).append("/songIndexController?msg=")
					.append(GlobalConstant.SUCCESS).toString();
			response.sendRedirect(url);
			// success
			return;
		} 
			String url = sbd.append(request.getContextPath()).append("/songIndexController?msg=")
					.append(GlobalConstant.ERROR).toString();
			response.sendRedirect(url);
		
	}
		
}
