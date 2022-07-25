package com.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.shared.Loginbean;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class BugTracking implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	
	DockLayoutPanel dp;
	ScrollPanel sp;
	Button blogin, borg, bbugs, breports,  bcontact;
	
	DecoratedPopupPanel ppOrg, ppBug;
	Anchor anc_user, anc_project, anc_members, anc_priority;
	Anchor anc_bug, anc_buga, anc_solution;
	
	LoginComposit loginComposit;
	
	
	static boolean isLogedIn = false;
	
	public void onModuleLoad() {
	
		dp = new DockLayoutPanel(Unit.PX);
		dp.setSize("100%", "100%");
		dp.setStyleName("cwt-DockPanel");

		dp.addNorth(createNorth(), 120);
		dp.addNorth(createMenu(), 45);
		dp.addSouth(createFooter(), 40);
		dp.add(createMain());
		
		RootLayoutPanel rp = RootLayoutPanel.get();
		rp.add(dp);
		
	}
	
	private Widget createMenu() {
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.setStyleName("menu");
		
		HorizontalPanel hp2 = new HorizontalPanel();
		hp2.setSize("900px", "40px");
		//hp2.setBorderWidth(1);
		
		hp.add(hp2);
		hp.setCellHorizontalAlignment(hp2, HasHorizontalAlignment.ALIGN_CENTER);
		
		//Menu
		blogin = new Button("Login");
		blogin.setStyleName("menub");
		blogin.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				isLogedIn = false;
				blogin.setText("Login");
				LoginComposit composit = new LoginComposit();
				sp.setWidget(composit);
				composit.btnLogin.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						
						login();
						
					}
				});
				
				
			}
		});
		
		hp2.add(blogin);
		
		borg = new Button("Organization");
		borg.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				if(isLogedIn) {
				
					showOrgMenu();
				}else {
					
					Window.alert("Access Denied");
				}
				
				
			}
		});
		borg.setStyleName("menub");
		
		hp2.add(borg);
		
		bbugs = new Button("Bugs");
		bbugs.setStyleName("menub");
		hp2.add(bbugs);
		bbugs.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
			
				if(isLogedIn) {
				showBugMenu();
				}else {
					
					Window.alert("Access Denied");
				}
				
			}
		});
		
		breports = new Button("Reports");
		breports.setStyleName("menub");
		hp2.add(breports);
		breports.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				if(isLogedIn){
					
					ReportComposit composit = new ReportComposit();
					sp.setWidget(composit);

					
				}else {
					
					Window.alert("Access Denied");
				}
				
			}
		});
		
		
		
		
		bcontact = new Button("Contact Us");
		bcontact.setStyleName("menub");
		bcontact.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				ContactDialog dialog = new ContactDialog();
				dialog.center();
				dialog.show();
			}
		});
		
		hp2.add(bcontact);
		
		
		
		return hp;
	}
	
	private void login() {
		
		
		if(loginComposit.textBox_user.getText().isEmpty()||loginComposit.passwordTextBox.getText().isEmpty()) {
		
			Window.alert("username & password should not be empty");
			
		}else {
			
				Loginbean loginbean= new Loginbean();
				loginbean.setPassword(loginComposit.passwordTextBox.getText());
				loginbean.setLoginid(loginComposit.textBox_user.getText());
				
				greetingService.loginServer(loginbean, new AsyncCallback<String[]>() {
					
					@Override
					public void onSuccess(String[] result) {
						
						if(result[0].equals("true")) {
							
							isLogedIn = true;
							blogin.setText("Logout");
							MainComposit composit = new MainComposit();
							sp.setWidget(composit);
							
													
						}else {
							
							ErrorBox box = new ErrorBox(result[1]);
							box.show();
							
						}
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						
						ErrorBox box = new ErrorBox(caught.getLocalizedMessage());
						box.show();
						
					}
				});
			
			
			
			
		}
		
		
	}
	
	private Widget createMain() {
		
		
		sp = new ScrollPanel();
		sp.addStyleName("myScroll");
		sp.setSize("100%", "100%");
		
		//Main Widget
		isLogedIn = false;
		blogin.setText("Login");
		loginComposit = new LoginComposit();
		sp.setWidget(loginComposit);
		loginComposit.btnLogin.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				login();
				
			}
		});
		
		
		return sp;
	}
	
	private Widget createNorth() {
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.setStyleName("header");
		
		HorizontalPanel hp2 = new HorizontalPanel();
		hp2.setStyleName("logo");
		hp.add(hp2);
		hp.setCellHorizontalAlignment(hp2, HasHorizontalAlignment.ALIGN_CENTER);
		
		
		
		
		return hp;
	}
	
	private Widget createFooter() {
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.setStyleName("footer");
		
		hp.add(new HTML(""));
		
	
		
		return hp;
	}
	
	private void showOrgMenu() {
		
		if(ppOrg==null) {
			
			ppOrg = new DecoratedPopupPanel(true);
			ppOrg.setAnimationEnabled(true);
			ppOrg.setWidth("180px");
			
			VerticalPanel vp = new VerticalPanel();
			vp.setWidth("130px");
			
			anc_user = new Anchor("User");
			anc_user.setStyleName("smenu");
			vp.add(anc_user);
			
			anc_user.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					
					UserComposit composit = new UserComposit();
					sp.setWidget(composit);
					ppOrg.setVisible(false);
					
					
				}
			});
			
			anc_project = new Anchor("Project");
			anc_project.setStyleName("smenu");
			vp.add(anc_project);
			
			anc_project.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					
					ProjectComposit composit = new ProjectComposit();
					sp.setWidget(composit);
					ppOrg.setVisible(false);
					
					
				}
			});
			
			anc_members = new Anchor("Members");
			anc_members.setStyleName("smenu");
			vp.add(anc_members);
			anc_members.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					
					MemeberComposit composit = new MemeberComposit();
					sp.setWidget(composit);
					ppOrg.setVisible(false);
					
					
				}
			});
			
			
			anc_priority = new Anchor("Priority");
			anc_priority.setStyleName("smenu");
			vp.add(anc_priority);
			anc_priority.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					
					PriorityComposit composit = new PriorityComposit();
					sp.setWidget(composit);
					ppOrg.setVisible(false);
					
					
				}
			});
			
			
			ppOrg.add(vp);
			
			
			
		}
		
		ppOrg.showRelativeTo(borg);
	}
	
	private void showBugMenu() {
		
		if(ppBug==null) {
			
			ppBug = new DecoratedPopupPanel(true);
			ppBug.setAnimationEnabled(true);
			ppBug.setWidth("180px");
			
			VerticalPanel vp = new VerticalPanel();
			vp.setWidth("130px");
			
			anc_bug = new Anchor("Bug");
			anc_bug.setStyleName("smenu");
			vp.add(anc_bug);
			
			anc_bug.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					
					BugComposit composit = new BugComposit();
					sp.setWidget(composit);
					ppBug.setVisible(false);
					
					
				}
			});
			
			anc_buga = new Anchor("Bug Assigned");
			anc_buga.setStyleName("smenu");
			vp.add(anc_buga);
			
			anc_buga.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					
					BugAComposit composit = new BugAComposit();
					sp.setWidget(composit);
					ppBug.setVisible(false);
					
					
				}
			});
			
			
			anc_solution = new Anchor("Solution");
			anc_solution.setStyleName("smenu");
			vp.add(anc_solution);
			anc_solution.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					
					SolutionComposit composit = new SolutionComposit();
					sp.setWidget(composit);
					ppBug.setVisible(false);
					
					
				}
			});
			
			
			
			ppBug.add(vp);
			
			
			
		}
		
		ppBug.showRelativeTo(bbugs);
	}
}
