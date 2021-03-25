package controller.publics;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import constants.GlobalConstant;
import daos.ContactDAO;
import models.Contact;

/**
 * Servlet implementation class publicIndexController
 */
@WebServlet("/publicContactController")
public class PublicContactController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ContactDAO contactDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PublicContactController() {
        super();
        contactDAO = new ContactDAO();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher rd = request.getRequestDispatcher("views/public/contact.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String 	email = request.getParameter("email");
		String website = request.getParameter("website");
		String message = request.getParameter("message");
		Contact item = new Contact(name, email, website, message);
		if(contactDAO.addItem(item)>0) {
			response.sendRedirect(request.getContextPath() + "/publicContactController?msg="+GlobalConstant.SUCCESS);
			return;
			
		}else {
			RequestDispatcher rd = request.getRequestDispatcher("views/public/contact.jsp?msg"+GlobalConstant.ERROR);
			rd.forward(request, response);
		}
		
		
	}

}
