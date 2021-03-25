package controller.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import constants.GlobalConstant;
import daos.UserDAO;
import models.User;

/**
 * Servlet implementation class userDeleteController
 */
@WebServlet("/userDeleteController")
public class UserDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
     private UserDAO userDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserDeleteController() {
        super();
        userDAO = new UserDAO();
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
			response.sendRedirect(request.getContextPath() +"/userIndexController?msg="+GlobalConstant.DELERROR);
			return;
			// TODO: handle exception
		}

		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		User item = userDAO.getItem(id);
		if("admin".equals(item.getUsername())){
			response.sendRedirect(request.getContextPath()+ "/userIndexController?msg="+GlobalConstant.ERR);
			return;
			
		}else {
			if("admin".equals(userLogin.getUsername())) {
				if(userDAO.delItem(id)>0) {
					response.sendRedirect(request.getContextPath() +"/userIndexController?msg="+GlobalConstant.DELSUCCESS);
					return;
				}else {
					response.sendRedirect(request.getContextPath() +"/userIndexController?msg="+GlobalConstant.DELERROR);
					return;
				}
			}else{
				response.sendRedirect(request.getContextPath()+ "/userIndexController?msg="+GlobalConstant.ERR);
				return;
			}
				
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
