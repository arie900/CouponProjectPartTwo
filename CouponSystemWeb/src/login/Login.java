package login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import h.facade.ClientType;

import h.facade.CouponClientFacade;
import i.couponSystem.CouponSystem;
import i.couponSystem.CouponSystemException;

public class Login extends HttpServlet {
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String user=request.getParameter("user");
		String password=request.getParameter("password");
		String role=request.getParameter("role");
		
		HttpSession session=request.getSession(false);
		if(session!=null){
			session.invalidate();
		}
		ClientType type=ClientType.valueOf(request.getParameter("role"));
		CouponClientFacade facade=null;
		try {
		
			facade=CouponSystem.getInstance().login(user, password, type);
				session=request.getSession(true);
				session.setAttribute("facade", facade);		
				if(facade!=null){
				switch (type) {
			case ADMIN:
				response.sendRedirect("admin.html");
				break;
			case COMPANY:
				response.sendRedirect("company.html");
				break;
			case CUSTOMER:
				response.sendRedirect("customer.html");;
				break;

			default:
				break;
			}
				}
				else{
					request.getRequestDispatcher("badlogin.html").forward(request, response);
				}
		} catch (CouponSystemException e) {
			//Maybe make an alert for bad login
			request.getRequestDispatcher("badlogin.html").forward(request, response);
			
		}
	}

}
