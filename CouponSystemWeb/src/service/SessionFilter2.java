package service;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionFilter2 implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest servreq = (HttpServletRequest) request;
		HttpServletResponse servres = (HttpServletResponse) response;
		HttpSession session=servreq.getSession(false);
		if(session!=null){
			chain.doFilter(request, response);	
			
		}
		else{
		servres.sendRedirect("http://localhost:8080/CouponSystemWeb/login.html");
//			servreq.getRequestDispatcher("http://localhost:8080/CouponSystemWeb/login.html").forward(servreq, servres);
		
		}
		
	}

	

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
