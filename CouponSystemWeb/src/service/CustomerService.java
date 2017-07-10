package service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import c.coupon.Coupon;
import c.coupon.CouponType;
import entity.Income;
import entity.IncomeType;
import h.facade.CustomerFacade;
import i.couponSystem.CouponSystemException;

@Path("myCustomer")
public class CustomerService {
	
	@Context
	private HttpServletRequest request;

	
	@POST
	@Path("/purchase")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Coupon purchaseCoupon(Coupon coupon){
		CustomerFacade customer = (CustomerFacade) request.getSession().getAttribute("facade");
		Income income=new Income();
		Calendar cal=Calendar.getInstance();
		System.out.println(coupon);
		try {
			Helper helper=new Helper();
			customer.PurchaseCoupon(coupon);
			income.setAmount(coupon.getPrice());
			income.setDate(cal.getTime());
			income.setDescription(IncomeType.CUSTOMER_PURCHASE);
			income.setName(customer.getCustmer().getCustName());
			helper.log(income);
			
			
		} catch (CouponSystemException  e) {
			e.printStackTrace();
		}
		return coupon;
	}
	
	@GET
	@Path("/coupons")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Coupon> getAllCoupons(){
		CustomerFacade customer = (CustomerFacade) request.getSession().getAttribute("facade");
		List<Coupon> couponList=new ArrayList<>();
		try {
			couponList=customer.getAllPurchasedCoupons();
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
		return couponList;
	}
	
	@GET
	@Path("/couponprice/{price}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Coupon> getCouponByPrice(@PathParam("price")double price){
		CustomerFacade customer = (CustomerFacade) request.getSession().getAttribute("facade");
		List<Coupon> couponList=new ArrayList<>();
		try {
			couponList=customer.getCouponsByPrice(price);
			System.out.println(couponList);
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
		return couponList;
	}
	
	@GET
	@Path("/coupontype/{type}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Coupon> getCouponByType(@PathParam("type")String type){
		CustomerFacade customer = (CustomerFacade) request.getSession().getAttribute("facade");
		List<Coupon> couponList=new ArrayList<>();
		CouponType couponType=CouponType.valueOf(type);
		try {
			couponList=customer.getCouponsByType(couponType);
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
		return couponList;
	}
	
	@GET
	@Path("/availble")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Coupon> getCouponByType(){
		CustomerFacade customer = (CustomerFacade) request.getSession().getAttribute("facade");
		List<Coupon> couponList=new ArrayList<>();
		try {
			couponList=customer.getAvailableCouppon();
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
		return couponList;
	}
	

}
