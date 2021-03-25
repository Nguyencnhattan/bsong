package controller.contact;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daos.ContactDAO;
import models.Contact;
import utils.AuthUtil;
import utils.DefineUtil;

/**
 * Servlet implementation class contactIndexController
 */
@WebServlet("/contactIndexController")
public class ContactIndexController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       private ContactDAO contactDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContactIndexController() {
        super();
        contactDAO = new ContactDAO();
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
			numberOfItems= contactDAO.numberOfItemsSearch(search);
		}else {
			numberOfItems= contactDAO.numberOfItems();
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
		
		
		int numberCon = (int) contactDAO.numberOfItems();
		ContactDAO contactDAO = new ContactDAO();
		
		
		List<Contact> contacts = null;
		if(search != null) {
			System.out.println(search);
			contacts= contactDAO.getSearch(search, offset);
			 System.out.println(contactDAO);
		}else {
			contacts = contactDAO.getItemsPagination(offset);}
		request.setAttribute("numberCon", numberCon);
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("contacts", contacts);
		RequestDispatcher rd = request.getRequestDispatcher("views/admin/contact/indexContact.jsp");
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
