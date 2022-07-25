package com.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import com.client.GreetingService;
import com.shared.Bugassignedbean;
import com.shared.Bugbean;
import com.shared.FieldVerifier;
import com.shared.Loginbean;
import com.shared.Loginbean;
import com.shared.Loginprofilebean;
import com.shared.Prioritybean;
import com.shared.Projectbean;
import com.shared.Projectbean;
import com.shared.Projectmemberbean;
import com.shared.Solutionbean;
import com.sun.org.glassfish.gmbal.Description;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import entity.Bug;
import entity.BugJpaController;
import entity.Bugassigned;
import entity.BugassignedJpaController;

import entity.Login;
import entity.LoginJpaController;
import entity.Loginprofile;
import entity.LoginprofileJpaController;
import entity.Priority;
import entity.PriorityJpaController;
import entity.Project;
import entity.ProjectJpaController;
import entity.Projectmember;
import entity.ProjectmemberJpaController;
import entity.Solution;
import entity.SolutionJpaController;

import entity.Questionbase;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}

	@Override
	public List<Prioritybean> listPriorityServer() {
		
		List<Prioritybean> list = new ArrayList<Prioritybean>();
		List<Priority> listEntity = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get("EBugPU");
			PriorityJpaController controller = new PriorityJpaController(emf);
			
			//listPriority = controller.findPriorityEntities(20, id);
			listEntity = controller.findPriorityEntities();
			
			for (Iterator<Priority> iterator = listEntity.iterator(); iterator.hasNext();) {
				Priority entity2 = (Priority) iterator.next();
				
				Prioritybean bean = new Prioritybean();
				bean.setPriorityid(entity2.getPriorityid());
				bean.setPrioritytype(entity2.getPrioritytype());
				list.add(bean);
				
			}
						
			
		}catch(Exception e) {
			
			e.printStackTrace();
						
		}
		
		finally {
						
			
		}
		
		return list;
	}

	@Override
	public String[] savePriorityServer(Prioritybean bean, boolean isUpdate) {
		
		String status[]={"",""};
		
		Priority entity = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get("EBugPU");
			PriorityJpaController controller = new PriorityJpaController(emf);
			
			
			if(isUpdate) {
				
				entity = controller.findPriority(bean.getPriorityid());
				
				if(entity!=null) {
					
					entity.setPrioritytype(bean.getPrioritytype());
					controller.edit(entity);
				}
				
				
			}else {
				
				entity = new Priority();
				entity.setPrioritytype(bean.getPrioritytype());
				
				controller.create(entity);
				
			}
					
			status[0] = "true";
			status[1] = ""+entity.getPriorityid();
			
			
		}catch(Exception e) {
			
			e.printStackTrace();
			status[0] = "error";
			status[1] = "Error:"+e.getLocalizedMessage();
			
		}
		
		finally {
						
			
		}
		
		return status;
	}

	@Override
	public List<Loginbean> listLoginServer() {
		
		List<Loginbean> list = new ArrayList<Loginbean>();
		List<Login> listEntity = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get("EBugPU");
			LoginJpaController controller = new LoginJpaController(emf);
			
			//listPriority = controller.findPriorityEntities(20, id);
			listEntity = controller.findLoginEntities();
			
			for (Iterator<Login> iterator = listEntity.iterator(); iterator.hasNext();) {
				Login entity2 = (Login) iterator.next();
				
				Loginbean bean = new Loginbean();
				bean.setLoginid(entity2.getLoginid());
				bean.setFirstname(entity2.getFirstname());
				bean.setLastname(entity2.getLastname());
				
				//more iinformations
				
				list.add(bean);
				
			}
						
			
		}catch(Exception e) {
			
			e.printStackTrace();
						
		}
		
		finally {
						
			
		}
		
		return list;
	}

	
	@Override
	public String[] saveLoginServer(Loginbean bean,
			Loginprofilebean bean2, boolean isUpdate) {
		
		String status[]={"",""};
		
		Login entity = null;
		Loginprofile entity2 = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get("EBugPU");
			LoginJpaController controller = new LoginJpaController(emf);
			LoginprofileJpaController  controller2 = new LoginprofileJpaController(emf);
			
			
			if(isUpdate) {
				
				
				
				
			}else {
				
				entity = new Login();
				entity.setLoginid(bean.getLoginid());
				entity.setFirstlogin(bean.getFirstlogin());
				entity.setFirstname(bean.getFirstname());
				entity.setLastname(bean.getLastname());
				entity.setLoginstatus(bean.getLoginstatus());
				entity.setLogintype(bean.getLogintype());
				entity.setPassmodifieddate(bean.getPassmodifieddate());
				entity.setPassword(bean.getPassword());
				
				Questionbase questionbase = new Questionbase();
				questionbase.setQuestionid(bean.getQuestionid().getQuestionid());
				entity.setQuestionid(questionbase);
				
				entity.setRegdate(bean.getRegdate());
				entity.setSanswer(bean.getSanswer());
								
				controller.create(entity);
				
				//LoginProfile
				entity2 = new Loginprofile();
				entity2.setBirthdate(bean2.getBirthdate());
				entity2.setCity(bean2.getCity());
				entity2.setCountry(bean2.getCountry());
				entity2.setEmail(bean2.getEmail());
				entity2.setHno(bean2.getHno());
				
				
				
				entity2.setLoginid(entity);
				entity2.setPincode(bean2.getPincode());
				entity2.setProfilemodifieddate(bean2.getProfilemodifieddate());
				entity2.setState(bean2.getState());
				entity2.setStreet(bean2.getStreet());
				
			}
					
			status[0] = "true";
			status[1] = ""+entity.getLoginid();
			
			
		}catch(Exception e) {
			
			e.printStackTrace();
			status[0] = "error";
			status[1] = "Error:"+e.getLocalizedMessage();
			
		}
		
		finally {
						
			
		}
		
		return status;
	}

	@Override
	public List<Projectbean> listProjectServer() {
		
		List<Projectbean> list = new ArrayList<Projectbean>();
		List<Project> listEntity = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get("EBugPU");
			ProjectJpaController controller = new ProjectJpaController(emf);
			
			//listPriority = controller.findPriorityEntities(20, id);
			listEntity = controller.findProjectEntities();
			
			for (Iterator<Project> iterator = listEntity.iterator(); iterator.hasNext();) {
				Project entity2 = (Project) iterator.next();
				
				Projectbean bean = new Projectbean();
				bean.setEnddate(entity2.getEnddate());
				
				Login login = entity2.getLoginid();
				Loginbean loginbean = new Loginbean();
				loginbean.setLoginid(login.getLoginid());
				loginbean.setFirstname(login.getFirstname());
				
				bean.setLoginid(loginbean);
				
				bean.setProjectid(entity2.getProjectid());
				bean.setProjectname(entity2.getProjectname());
				bean.setStartdate(entity2.getStartdate());
				bean.setStatus(entity2.getStatus());
				
				//more iinformations
				
				list.add(bean);
				
			}
						
			
		}catch(Exception e) {
			
			e.printStackTrace();
						
		}
		
		finally {
						
			
		}
		
		return list;
	}

	@Override
	public String[] saveProjectServer(Projectbean bean, boolean isUpdate) {
		
		String status[]={"",""};
		
		Project entity = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get("EBugPU");
			ProjectJpaController controller = new ProjectJpaController(emf);
			
			
			if(isUpdate) {
				
				
				
				
			}else {
				
				
				entity = new Project();
				entity.setEnddate(bean.getEnddate());
				Login login =new Login();
				login.setLoginid(bean.getLoginid().getLoginid());
				
				entity.setLoginid(login);
				
				entity.setProjectname(bean.getProjectname());
				entity.setStartdate(bean.getStartdate());
				entity.setStatus(bean.getStatus());
				
				controller.create(entity);
				
			}
					
			status[0] = "true";
			status[1] = ""+entity.getProjectid();
			
			
		}catch(Exception e) {
			
			e.printStackTrace();
			status[0] = "error";
			status[1] = "Error:"+e.getLocalizedMessage();
			
		}
		
		finally {
						
			
		}
		
		return status;
	}

	@Override
	public List<Projectmemberbean> listProjectmemberServer() {
		
		List<Projectmemberbean> list = new ArrayList<Projectmemberbean>();
		List<Projectmember> listEntity = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get("EBugPU");
			ProjectmemberJpaController controller = new ProjectmemberJpaController(emf);
			
			//listPriority = controller.findPriorityEntities(20, id);
			listEntity = controller.findProjectmemberEntities();
			
			for (Iterator<Projectmember> iterator = listEntity.iterator(); iterator.hasNext();) {
				Projectmember entity2 = (Projectmember) iterator.next();
				
				Projectmemberbean bean = new Projectmemberbean();
				
				Loginbean loginbean = new Loginbean();
				loginbean.setLoginid(entity2.getLoginid().getLoginid());
				
				bean.setLoginid(loginbean);
				
				Projectbean projectbean =new Projectbean();
				projectbean.setProjectid(entity2.getProjectid().getProjectid());
				projectbean.setProjectname(entity2.getProjectid().getProjectname());
				
				bean.setProjectid(projectbean);
				
				bean.setProjectmid(entity2.getProjectmid());
				bean.setStatus(entity2.getStatus());
				
				
				//more iinformations
				
				list.add(bean);
				
			}
						
			
		}catch(Exception e) {
			
			e.printStackTrace();
						
		}
		
		finally {
						
			
		}
		
		return list;
	}

	@Override
	public String[] saveProjectmemberServer(Projectmemberbean bean,
			boolean isUpdate) {
			
		String status[]={"",""};
		
		Projectmember entity = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get("EBugPU");
			ProjectmemberJpaController controller = new ProjectmemberJpaController(emf);
			
			
			if(isUpdate) {
				
				
				
				
			}else {
				
				
				entity = new Projectmember();
				Login login =new Login();
				login.setLoginid(bean.getLoginid().getLoginid());
				entity.setLoginid(login);
				Project Project=new Project();
				Project.setProjectid(bean.getProjectid().getProjectid());
                entity.setProjectid(Project);
				
				entity.setStatus(bean.getStatus());
				
				controller.create(entity);
				
			}
					
			status[0] = "true";
			status[1] = ""+entity.getProjectid();
			
			
		}catch(Exception e) {
			
			e.printStackTrace();
			status[0] = "error";
			status[1] = "Error:"+e.getLocalizedMessage();
			
		}
		
		finally {
						
			
		}
		
		return status;
	}

	@Override
	public String[] saveBugServer(Bugbean bean, boolean isUpdate) {

		String status[]={"",""};
		
		Bug entity = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get("EBugPU");
			BugJpaController controller = new BugJpaController(emf);
			
			
			if(isUpdate) {
				
				
				
				
			}else {
				
				
				entity = new Bug();
				Login login =new Login();
				login.setLoginid(bean.getLoginid().getLoginid());
				entity.setLoginid(login);
				  entity.setRaiseddate(bean.getRaiseddate());
				entity.setBugname(bean.getBugname());
				Project Project=new Project();
				Project.setProjectid(bean.getProjectid().getProjectid());
                entity.setProjectid(Project);
                 
                Priority Priority =new Priority ();
                Priority .setPriorityid(bean.getPriorityid().getPriorityid());
                entity.setPriorityid(Priority);
                entity.setDescription(bean.getDescription());
              
				entity.setStatus(bean.getStatus());
				
				controller.create(entity);
				
			}
					
			status[0] = "true";
			status[1] = ""+entity.getProjectid();
			
			
		}catch(Exception e) {
			
			e.printStackTrace();
			status[0] = "error";
			status[1] = "Error:"+e.getLocalizedMessage();
			
		}
		
		finally {
						
			
		}
		
		return status;
	}

	@Override
	public List<Bugbean> listBugServer() {

		List<Bugbean> list = new ArrayList<Bugbean>();
		List<Bug> listEntity = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get("EBugPU");
			BugJpaController controller = new BugJpaController(emf);
			
			//listPriority = controller.findPriorityEntities(20, id);
			listEntity = controller.findBugEntities();
			
			for (Iterator<Bug> iterator = listEntity.iterator(); iterator.hasNext();) {
				Bug entity2 = (Bug) iterator.next();
				
				Bugbean bean = new Bugbean();
				bean.setBugname(entity2.getBugname());
				bean.setDescription(entity2.getDescription());
				bean.setBugid(entity2.getBugid());
				
				
				Loginbean loginbean = new Loginbean();
				loginbean.setLoginid(entity2.getLoginid().getLoginid());
				
				bean.setLoginid(loginbean);
				
				Projectbean projectbean =new Projectbean();
				projectbean.setProjectid(entity2.getProjectid().getProjectid());
				projectbean.setProjectname(entity2.getProjectid().getProjectname());
				
				bean.setProjectid(projectbean);
				
				Prioritybean prioritybean =new Prioritybean();
                prioritybean.setPriorityid(entity2.getPriorityid().getPriorityid());
                prioritybean.setPrioritytype(entity2.getPriorityid().getPrioritytype());
                
                bean.setPriorityid(prioritybean);
				
				
				bean.setStatus(entity2.getStatus());
				
				List<Solution> listS = (List<Solution>) entity2.getSolutionCollection();
 				
				List<Solutionbean> listSBean = new ArrayList<Solutionbean>();
				
				for(int i=0;i<listS.size();i++) {
					
					Solution s = listS.get(i);
					Solutionbean sbean = new Solutionbean();
					sbean.setSolution(s.getSolution());
					
					listSBean.add(sbean);
				}
				
				bean.setSolutionCollection(listSBean);
				
				//more iinformations
				
				list.add(bean);
				
			}
						
			
		}catch(Exception e) {
			
			e.printStackTrace();
						
		}
		
		finally {
						
			
		}
		
		return list;
	}

	@Override
	public List<Bugassignedbean> listBugAServer() {
		
		List<Bugassignedbean> list = new ArrayList<Bugassignedbean>();
		List<Bugassigned> listEntity = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get("EBugPU");
			BugassignedJpaController controller = new BugassignedJpaController(emf);
			
			//listPriority = controller.findPriorityEntities(20, id);
			listEntity = controller.findBugassignedEntities();
			
			for (Iterator<Bugassigned> iterator = listEntity.iterator(); iterator.hasNext();) {
				Bugassigned entity2 = (Bugassigned) iterator.next();
				
				Bugassignedbean bean = new Bugassignedbean();
				bean.setBugaid(entity2.getBugaid());
				bean.setAssdate(entity2.getAssdate());
				
				Bugbean bugbean = new Bugbean();
				bugbean.setBugid(entity2.getBugid().getBugid());
				bugbean.setBugname(entity2.getBugid().getBugname());
				bean.setBugid(bugbean);
				
				Loginbean loginbean = new Loginbean();
				loginbean.setLoginid(entity2.getLoginid().getLoginid());
				
				bean.setLoginid(loginbean);
				
							
				
				
				
				
				//more iinformations
				
				list.add(bean);
				
			}
						
			
		}catch(Exception e) {
			
			e.printStackTrace();
						
		}
		
		finally {
						
			
		}
		
		return list;
	}

	@Override
	public String[] saveBugAServer(Bugassignedbean bean, boolean isUpdate) {
		
		String status[]={"",""};
		
		Bugassigned entity = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get("EBugPU");
			BugassignedJpaController controller = new BugassignedJpaController(emf);
			
			
			if(isUpdate) {
				
				
				
				
			}else {
				
				
				entity = new Bugassigned();
				Login login =new Login();
				login.setLoginid(bean.getLoginid().getLoginid());
				entity.setLoginid(login);
				  entity.setAssdate(bean.getAssdate());
				  
				Bug bug=new Bug();
				bug.setBugid(bean.getBugid().getBugid());
				entity.setBugid(bug);
                				
				controller.create(entity);
				
			}
					
			status[0] = "true";
			status[1] = ""+entity.getBugaid();
			
			
		}catch(Exception e) {
			
			e.printStackTrace();
			status[0] = "error";
			status[1] = "Error:"+e.getLocalizedMessage();
			
		}
		
		finally {
						
			
		}
		
		return status;
	}

	@Override
	public List<Solutionbean> listSolutionServer() {
		
		List<Solutionbean> list = new ArrayList<Solutionbean>();
		List<Solution> listEntity = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get("EBugPU");
			SolutionJpaController controller = new SolutionJpaController(emf);
			
			//listPriority = controller.findPriorityEntities(20, id);
			listEntity = controller.findSolutionEntities();
			
			for (Iterator<Solution> iterator = listEntity.iterator(); iterator.hasNext();) {
				Solution entity2 = (Solution) iterator.next();
				
				Solutionbean bean = new Solutionbean();
				bean.setSolution(entity2.getSolution());
				bean.setSolveddate(entity2.getSolveddate());
				bean.setSolutionid(entity2.getSolutionid());
				
				
				Bugbean bugbean = new Bugbean();
				bugbean.setBugid(entity2.getBugid().getBugid());
				bugbean.setBugname(entity2.getBugid().getBugname());
				bean.setBugid(bugbean);
				
				Loginbean loginbean = new Loginbean();
				loginbean.setLoginid(entity2.getLoginid().getLoginid());
				
				bean.setLoginid(loginbean);
				
							
				
				
				
				
				//more iinformations
				
				list.add(bean);
				
			}
						
			
		}catch(Exception e) {
			
			e.printStackTrace();
						
		}
		
		finally {
						
			
		}
		
		return list;
	}

	@Override
	public String[] saveSolutionServer(Solutionbean bean, boolean isUpdate) {
		
		String status[]={"",""};
		
		Solution entity = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get("EBugPU");
			SolutionJpaController controller = new SolutionJpaController(emf);
			
			
			if(isUpdate) {
				
				
				
				
			}else {
				
				
				entity = new Solution();
				
				Login login =new Login();
				login.setLoginid(bean.getLoginid().getLoginid());
				
				entity.setLoginid(login);
				  entity.setSolveddate(bean.getSolveddate());
				 
				Bug bug=new Bug();
				bug.setBugid(bean.getBugid().getBugid());
				entity.setBugid(bug);
				
				entity.setSolution(bean.getSolution());
				
                				
				controller.create(entity);
				
			}
					
			status[0] = "true";
			status[1] = ""+entity.getSolutionid();
			
			
		}catch(Exception e) {
			
			e.printStackTrace();
			status[0] = "error";
			status[1] = "Error:"+e.getLocalizedMessage();
			
		}
		
		finally {
						
			
		}
		
		return status;
	}

	@Override
	public String[] saveBugStatusServer(Bugbean bean, boolean isUpdate) {
		
String status[]={"",""};
		
		Bug entity = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get("EBugPU");
			BugJpaController controller = new BugJpaController(emf);
			
			entity = controller.findBug(bean.getBugid());
				
				if(entity!=null) {
				
					entity.setStatus(bean.getStatus());
					
					controller.edit(entity);
					
				}
              	
			status[0] = "true";
			status[1] = ""+entity.getProjectid();
			
			
		}catch(Exception e) {
			
			e.printStackTrace();
			status[0] = "error";
			status[1] = "Error:"+e.getLocalizedMessage();
			
		}
		
		finally {
						
			
		}
		
		return status;
	}

	@Override
	public String[] loginServer(Loginbean loginbean) {
		
		EntityManager em =null;
		Login entity = null;
		String status[]={"",""};
		
		try {
		
			EntityManagerFactory emf = EMF.get("EBugPU");
			em = emf.createEntityManager();
			
			Query query = em.createQuery("SELECT l FROM Login l WHERE l.loginid = :loginid and l.password = :password");
			query.setParameter("loginid", loginbean.getLoginid());
			query.setParameter("password", loginbean.getPassword());
			
			entity = (Login) query.getSingleResult();
						
				
			if(entity!=null) {
					
				status[0] = "true";
			}
				
			
			
			
			
		}catch(Exception e) {
			
			e.printStackTrace();
			status[0] = "error";
			status[1] = "Error:"+e.getLocalizedMessage();
			
		}
		
		finally {
						
			em.close();
		}
		
		return status;
	}
	
}
