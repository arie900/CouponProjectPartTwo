package service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import c.coupon.Coupon;
import c.coupon.CouponType;
import entity.Income;
import entity.IncomeType;
import h.facade.CompanyFacade;
import i.couponSystem.CouponSystemException;

@Path("myCompany")
public class CompanyService {
	@Context
	private HttpServletRequest request;
	

	@POST
	@Path("/coupon")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Coupon createCoupon(Coupon coupon) {
		CompanyFacade company = (CompanyFacade) request.getSession().getAttribute("facade");
		Income income=new Income();
		Calendar cal=Calendar.getInstance();
		List<Coupon> couponList=new ArrayList<>();
		try {
			couponList=company.getAllCoupons();
			for (Coupon coupon2 : couponList) {
				if((coupon2.getTitle().equals(coupon.getTitle()))||(coupon2.getId()==coupon.getId())||(coupon.getId()==0)){
					coupon.setId(0);
					return coupon;
				}
			}
			company.createCoupon(coupon);
			Helper helper=new Helper();
			income.setAmount(100);
			income.setDate(cal.getTime());
			income.setDescription(IncomeType.COMPANY_NEW_COUPON);
			income.setName(company	.getCompany().getCompName()); 
			helper.log(income);
			
		} catch (CouponSystemException e) {
			e.getMessage();
		}
		return coupon;

	}

	@DELETE
	@Path("/deletecoupon/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Coupon removeCoupon(@PathParam("id") int id) {
		CompanyFacade company=(CompanyFacade) request.getSession().getAttribute("facade");
		Coupon coupon=new Coupon();
		try {
			coupon =company.getCoupon(id);
			if(coupon.getId()!=0){
				company.removeCoupon(coupon);				
			}
		} catch (CouponSystemException e) {
			e.getMessage();
		}
		return coupon;

	}

	@PUT
	@Path("/updatecoupon/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Coupon updateCoupon(Coupon coupon) {
		CompanyFacade company = (CompanyFacade) request.getSession().getAttribute("facade");
		Income income=new Income();
		Calendar cal=Calendar.getInstance();
		try {
			Date endDate=coupon.getEndDate();
			double price=coupon.getPrice();
			company.updateCoupon(coupon, endDate, price);
			coupon=company.getCoupon(coupon.getId());
			Helper helper=new Helper();
			income.setAmount(10);
			income.setDate(cal.getTime());
			income.setDescription(IncomeType.COMPANY_UPDATE);
			income.setName(company.getCompany().getCompName());
			helper.log(income);
		} catch (CouponSystemException e) {
			e.getMessage();
		}
		return coupon;

	}

	@GET
	@Path("/getcoupon/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Coupon getCoupon(@PathParam("id") long id){
		CompanyFacade company=(CompanyFacade) request.getSession().getAttribute("facade");
		Coupon coupon= new Coupon();
		try {
			coupon=company.getCoupon(id);
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
		return coupon;
		
	}

	@GET
	@Path("/getall")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Coupon> getAllCoupon(){
		CompanyFacade company = (CompanyFacade) request.getSession().getAttribute("facade");
		List<Coupon> couponList=new ArrayList<>();
		try {
			couponList=company.getAllCoupons();
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
		
		return couponList;
	}

	@GET
	@Path("/couponbytype/{type}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Coupon> getCouponByType(@PathParam("type") String type) {
		CompanyFacade company=(CompanyFacade) request.getSession().getAttribute("facade");
		CouponType couponType=CouponType.valueOf(type);
		List<Coupon> couponList=new ArrayList<>();
		try {
			couponList=company.getCouponByType(couponType);
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
		return couponList;

	}

}
