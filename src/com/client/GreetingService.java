package com.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.shared.Bugassignedbean;
import com.shared.Bugbean;
import com.shared.Loginbean;
import com.shared.Loginprofilebean;
import com.shared.Prioritybean;
import com.shared.Projectbean;
import com.shared.Projectmemberbean;
import com.shared.Solutionbean;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	String greetServer(String name) throws IllegalArgumentException;

	List<Prioritybean> listPriorityServer();

	String[] savePriorityServer(Prioritybean bean, boolean isUpdate);

	List<Loginbean> listLoginServer();

	

	String[] saveLoginServer(Loginbean bean, Loginprofilebean loginprofilebean,
			boolean isUpdate);

	List<Projectbean> listProjectServer();

	String[] saveProjectServer(Projectbean bean, boolean isUpdate);

	List<Projectmemberbean> listProjectmemberServer();

	String[] saveProjectmemberServer(Projectmemberbean bean, boolean isUpdate);

	String[] saveBugServer(Bugbean bean, boolean isUpdate);

	List<Bugbean> listBugServer();

	List<Bugassignedbean> listBugAServer();

	String[] saveBugAServer(Bugassignedbean bean, boolean isUpdate);

	List<Solutionbean> listSolutionServer();

	String[] saveSolutionServer(Solutionbean bean, boolean isUpdate);

	String[] saveBugStatusServer(Bugbean bean, boolean isUpdate);

	String[] loginServer(Loginbean loginbean);
}
