package service;

import java.util.ArrayList;
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
import d.company.Company;
import e.customer.Customer;
import entity.Income;
import h.facade.AdminFacade;
import i.couponSystem.CouponSystemException;

@Path("/myAdmin")
public class AdminService {

	@Context
	private HttpServletRequest request;

	//create checking for customer name and customer id
	@POST
	@Path("/company")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Company createCompany(Company company) {
		AdminFacade admin = (AdminFacade) request.getSession().getAttribute("facade");
		List<Company> companyList=new ArrayList<>();
		try {
			companyList=admin.getAllCompanies();
			for (Company company2 : companyList) {
				if((company.getCompName().equals(company2.getCompName()))||(company.getId()==company2.getId())||(company.getId()==0)){
					company.setId(0);
					return company;
				}
				
			}
			admin.createCompany(company);
		} catch (CouponSystemException e) {
			e.getMessage();
		}
		return company;
	}

	// http://localhost:8080/CouponSystemWeb/rest/myAdmin/deletecompany/(id of
	// company)
	@DELETE
	@Path("/deletecompany/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Company deleteCompany(@PathParam("id") long id) {
		AdminFacade admin = (AdminFacade) request.getSession().getAttribute("facade");
		Company company = new Company();
		try {
			company = admin.getCompany(id);
			if(company.getId()!=0){
				admin.removeCompany(company);				
			}
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
		return company;
	}

	// Need to complete
	@PUT
	@Path("/updatecompany/{compName}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Company updateCompany(Company company) {
		AdminFacade admin = (AdminFacade) request.getSession().getAttribute("facade");
		try {
			admin.updateCompany(company);
			company=admin.getCompany(company.getId());


		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
		return company;

	}

	// http://localhost:8080/CouponSystemWeb/rest/myAdmin/deletecompany/(id of
	// company)
	@GET
	@Path("/companyid/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Company getCompany(@PathParam("id") long id) {
		AdminFacade admin = (AdminFacade) request.getSession().getAttribute("facade");
		Company company = new Company();
		try {
			company = admin.getCompany(id);
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
		return company;
	}

	// http://localhost:8080/CouponSystemWeb/rest/myAdmin/companies
	@GET
	@Path("/companies")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Company> getCompany() {
		AdminFacade admin = (AdminFacade) request.getSession().getAttribute("facade");
		List<Company> companyList = new ArrayList<>();
		try {
			companyList = admin.getAllCompanies();
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
		
		return companyList;
	}
//create checking for customer name and customer id
	@POST
	@Path("/customer")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Customer createCustomer(Customer customer) {
		AdminFacade admin = (AdminFacade) request.getSession().getAttribute("facade");
		
		try {
			List<Customer> customerList=new ArrayList<>();
				customerList=admin.getAllCustomers();
				for (Customer customer2 : customerList) {
					if((customer.getCustName().equals(customer2.getCustName()))||(customer.getId()==customer2.getId())||(customer.getId()==0)){
						customer.setId(0);
						return customer;
					}
					
				}
				admin.createCustomer(customer);
				
		} catch (CouponSystemException e) {
			e.getMessage();
		}
		return customer;
	}

	@DELETE
	@Path("/deletecustomer/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer deleteCustomer(@PathParam("id") long id) {
		AdminFacade admin = (AdminFacade) request.getSession().getAttribute("facade");
		Customer customer = new Customer();
		try {
			customer = admin.getCustomerById(id);
			if(customer.getId()!=0){
				admin.removeCustomer(customer);				
			}
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
		return customer;
	}

	// Need to complete
	@PUT
	@Path("/updatecustomer/{custName}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Customer updateCustomer(Customer customer) {
		AdminFacade admin = (AdminFacade) request.getSession().getAttribute("facade");
		try {
			admin.updateCustomer(customer);
			customer=admin.getCustomerById(customer.getId());
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
		return customer;
	}

	@GET
	@Path("/customerid/{id}")
	@Produces({ MediaType.APPLICATION_JSON})
	public Customer getCustomer(@PathParam("id") long id) {
		AdminFacade admin = (AdminFacade) request.getSession().getAttribute("facade");
			Customer customer=new Customer();
		try {
			customer=admin.getCustomerById(id);
				
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
		return customer;


	}

	@GET
	@Path("/customers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Customer> getCustomer() {
		AdminFacade admin = (AdminFacade) request.getSession().getAttribute("facade");
		List<Customer> customerList = new ArrayList<>();
		try {
			customerList = admin.getAllCustomers();
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
		return customerList;
	}
	
	@GET
	@Path("/allincome")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Income> getAllIncome(){
		AdminFacade admin = (AdminFacade) request.getSession().getAttribute("facade");
		Helper h= new Helper();
		return h.getAllincome();
	}
	
	@GET
	@Path("/incomecustomer/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Income> getCustomerIncome(@PathParam("id") long id){
		AdminFacade admin = (AdminFacade) request.getSession().getAttribute("facade");
		Customer customer = new Customer();
		Helper h= new Helper();
		try {
			customer=admin.getCustomerById(id);
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
		return h.getIncomeByCustomer(customer.getCustName());
		
		
	}
	
	@GET
	@Path("/incomecompany/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Income> getCompanyIncome(@PathParam("id")long id){
		AdminFacade admin = (AdminFacade) request.getSession().getAttribute("facade");
		Company company =new Company();
		Helper h=new Helper();
		try {
			company=admin.getCompany(id);
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
		return h.getIncomeByCompany(company.getCompName());
	}
	
}

	
	
	