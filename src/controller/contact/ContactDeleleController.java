package controller.contact;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import constants.GlobalConstant;
import daos.ContactDAO;

/**
 * Servlet implementation class contactDeleleController
 */
@WebServlet("/contactDeleleController")
public class ContactDeleleController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ContactDAO contactDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContactDeleleController() {
        super();
        contactDAO = new ContactDAO();
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
			System.out.println(id);
		}catch (Exception e) {
			response.sendRedirect(request.getContextPath() +"/contactIndexController?msg="+GlobalConstant.DELERROR);
			return;
			// TODO: handle exception
		}
		if(contactDAO.delItem(id)>0) {
			response.sendRedirect(request.getContextPath() +"/contactIndexController?msg="+GlobalConstant.DELSUCCESS);
			return;
		}else {
			response.sendRedirect(request.getContextPath() +"/contactIndexController?msg="+GlobalConstant.DELERROR);
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
