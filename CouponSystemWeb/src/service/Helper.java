package service;


import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import entity.Income;
import entity.IncomeService;

public class Helper {
	private static QueueConnectionFactory  qconFactory;
	private static QueueConnection qcon;
	private static QueueSession qsession;
	private static QueueSender qsender;
	private static Queue queue;
	private static ObjectMessage msg;
	private static IncomeService stub;
	
	
	
	static{
		try {
			InitialContext ctx=new InitialContext();
			qconFactory=(QueueConnectionFactory) ctx.lookup("ConnectionFactory");
			qcon=qconFactory.createQueueConnection();
			qsession=qcon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			stub=(IncomeService) ctx.lookup("IncomeManager/local");
			queue=(Queue) ctx.lookup("queue/Income-Queue");
			qsender=qsession.createSender(queue);
			
			
		} catch (NamingException | JMSException e) {
			e.printStackTrace();
		}
		
	}
	
	public  synchronized static void log(Income income){
		try {
			msg=qsession.createObjectMessage(income);
			qsender.send(msg);
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
	}
	
	public List<Income> getAllincome(){
		List<Income> incomeList= new ArrayList<>();
		incomeList=stub.viewAllIncome();
		return incomeList;
	}
	
	public List<Income> getIncomeByCustomer(String customerName){
		List<Income> incomeList=new ArrayList<>();
		incomeList=stub.viewIncomeByCustomer(customerName);
		return incomeList;
	}
	
	public List<Income> getIncomeByCompany(String compName){
		List<Income> incomeList=new ArrayList<>();
		incomeList=stub.viewIncomeByCompany(compName);
		return incomeList;
	}

}
