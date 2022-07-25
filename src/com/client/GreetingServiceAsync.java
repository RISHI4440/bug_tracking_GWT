package com.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.shared.Bugassignedbean;
import com.shared.Bugbean;
import com.shared.Loginbean;
import com.shared.Loginprofilebean;
import com.shared.Prioritybean;
import com.shared.Projectbean;
import com.shared.Projectmemberbean;
import com.shared.Solutionbean;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;

	void listPriorityServer(AsyncCallback<List<Prioritybean>> asyncCallback);

	void savePriorityServer(Prioritybean bean, boolean isUpdate,
			AsyncCallback<String[]> asyncCallback);

	void listLoginServer(AsyncCallback<List<Loginbean>> asyncCallback);

	

	void saveLoginServer(Loginbean bean, Loginprofilebean loginprofilebean,
			boolean isUpdate, AsyncCallback<String[]> asyncCallback);

	void listProjectServer(AsyncCallback<List<Projectbean>> asyncCallback);

	void saveProjectServer(Projectbean bean, boolean isUpdate,
			AsyncCallback<String[]> asyncCallback);

	void listProjectmemberServer(
			AsyncCallback<List<Projectmemberbean>> asyncCallback);

	void saveProjectmemberServer(Projectmemberbean bean, boolean isUpdate,
			AsyncCallback<String[]> asyncCallback);

	void saveBugServer(Bugbean bean, boolean isUpdate,
			AsyncCallback<String[]> asyncCallback);

	void listBugServer(AsyncCallback<List<Bugbean>> asyncCallback);

	void listBugAServer(AsyncCallback<List<Bugassignedbean>> asyncCallback);

	void saveBugAServer(Bugassignedbean bean, boolean isUpdate,
			AsyncCallback<String[]> asyncCallback);

	void listSolutionServer(AsyncCallback<List<Solutionbean>> asyncCallback);

	void saveSolutionServer(Solutionbean bean, boolean isUpdate,
			AsyncCallback<String[]> asyncCallback);

	void saveBugStatusServer(Bugbean bean, boolean isUpdate,
			AsyncCallback<String[]> asyncCallback);

	void loginServer(Loginbean loginbean, AsyncCallback<String[]> asyncCallback);
}
