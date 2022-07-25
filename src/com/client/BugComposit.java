package com.client;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.client.ui.TextArea;
import com.shared.Bugbean;
import com.shared.Loginbean;
import com.shared.Prioritybean;
import com.shared.Projectbean;

public class BugComposit extends Composite{
	private ListBox listBox;
	private Label label_status;
	private TextBox textBox_id;
	private TextBox textBox_status;
	private ListBox listBox_project;
	private Button btnNew;
	private Button btnSave;
	private TextBox textBox_name;
	private DateBox dateBox_raised;
	private TextArea textArea_desc;
	private ListBox listBox_priority;
	private ListBox listBox_user;
	
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	
	List<Bugbean> listBug;
	List<Loginbean> listLogin;
	List<Projectbean> listProject;
	List<Prioritybean> listPriority;
	boolean isUpdate = false;
	Bugbean bean;
	
	public BugComposit() {
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		initWidget(horizontalPanel);
		horizontalPanel.setSize("100%", "100%");
		
		DecoratorPanel decoratorPanel = new DecoratorPanel();
		horizontalPanel.add(decoratorPanel);
		horizontalPanel.setCellHorizontalAlignment(decoratorPanel, HasHorizontalAlignment.ALIGN_CENTER);
		
		FlexTable flexTable = new FlexTable();
		decoratorPanel.setWidget(flexTable);
		flexTable.setSize("800px", "100%");
		
		Label lblBugTrackingSystem = new Label("Bug Administration");
		flexTable.setWidget(0, 0, lblBugTrackingSystem);
		lblBugTrackingSystem.setStyleName("heading");
		flexTable.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
		flexTable.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
		
		HorizontalSplitPanel horizontalSplitPanel = new HorizontalSplitPanel();
		horizontalSplitPanel.setSplitPosition("500px");
		flexTable.setWidget(1, 0, horizontalSplitPanel);
		horizontalSplitPanel.setSize("794px", "500px");
		
		listBox = new ListBox();
		listBox.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				
				
			}
		});
		horizontalSplitPanel.setRightWidget(listBox);
		listBox.setSize("100%", "100%");
		listBox.setVisibleItemCount(5);
		
		FlexTable flexTable_1 = new FlexTable();
		flexTable_1.setCellPadding(5);
		horizontalSplitPanel.setLeftWidget(flexTable_1);
		flexTable_1.setSize("100%", "");
		
		label_status = new Label(":");
		flexTable_1.setWidget(0, 0, label_status);
		flexTable_1.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
		
		Label lblStudentId = new Label("Bug Id:");
		flexTable_1.setWidget(1, 0, lblStudentId);
		
		textBox_id = new TextBox();
		textBox_id.setEnabled(false);
		flexTable_1.setWidget(1, 1, textBox_id);
		textBox_id.setWidth("300px");
		
		Label lblStudentName = new Label("Bug Name");
		flexTable_1.setWidget(2, 0, lblStudentName);
		
		textBox_name = new TextBox();
		flexTable_1.setWidget(2, 1, textBox_name);
		textBox_name.setWidth("300px");
		
		Label lblStatus = new Label("Project");
		flexTable_1.setWidget(3, 0, lblStatus);
		
		listBox_project = new ListBox();
		flexTable_1.setWidget(3, 1, listBox_project);
		listBox_project.setSize("305px", "30px");
		
		Label lblContactNo = new Label("Raised Date");
		flexTable_1.setWidget(4, 0, lblContactNo);
		
		dateBox_raised = new DateBox();
		dateBox_raised.setFormat(new DateBox.DefaultFormat(DateTimeFormat
				.getFormat("dd/MM/yyyy")));
		flexTable_1.setWidget(4, 1, dateBox_raised);
		dateBox_raised.setWidth("300px");
		
		Label lblDepartment = new Label("Manager");
		flexTable_1.setWidget(5, 0, lblDepartment);
		
		listBox_user = new ListBox();
		flexTable_1.setWidget(5, 1, listBox_user);
		listBox_user.setSize("305px", "30px");
		
		Label lblPriority = new Label("Priority");
		flexTable_1.setWidget(6, 0, lblPriority);
		
		listBox_priority = new ListBox();
		flexTable_1.setWidget(6, 1, listBox_priority);
		listBox_priority.setSize("305px", "30px");
		
		Label lblDescription = new Label("Description");
		flexTable_1.setWidget(7, 0, lblDescription);
		
		textArea_desc = new TextArea();
		flexTable_1.setWidget(7, 1, textArea_desc);
		textArea_desc.setSize("300px", "100px");
		
		Label lblStatus_1 = new Label("Status");
		flexTable_1.setWidget(8, 0, lblStatus_1);
		
		textBox_status = new TextBox();
		flexTable_1.setWidget(8, 1, textBox_status);
		textBox_status.setWidth("300px");
		
		Grid grid_1 = new Grid(2, 2);
		flexTable_1.setWidget(9, 1, grid_1);
		grid_1.setWidth("");
		
		btnNew = new Button("New");
		btnNew.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				empty();
			}
		});
		grid_1.setWidget(1, 0, btnNew);
		
		btnSave = new Button("Save");
		btnSave.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				save();
			}
		});
		grid_1.setWidget(1, 1, btnSave);
		
		//
		loadList();
		loadProjectList();
		loadPriorityList();
		loadLoginList();
	}
	
	
	
	private void save(){
		
		if(textBox_name.getText().isEmpty()||dateBox_raised.getValue()==null||textBox_status.getText().isEmpty()) {
			
			Window.alert("Required fields cannot be empty!");
			
		}else {
			
			bean = new Bugbean();
			bean.setBugname(textBox_name.getText());
			
			bean.setProjectid(listProject.get(listBox_project.getSelectedIndex()));
			bean.setRaiseddate(dateBox_raised.getValue());
			bean.setLoginid(listLogin.get(listBox_user.getSelectedIndex()));
			bean.setPriorityid(listPriority.get(listBox_priority.getSelectedIndex()));
			bean.setDescription(textArea_desc.getSelectedText());
			bean.setStatus(textBox_status.getText());
			
			
						
			if(isUpdate) {
				
				//bean.setPriorityid(Integer.parseInt(textBox_id.getText()));
			}
			
			greetingService.saveBugServer(bean, isUpdate, new AsyncCallback<String[]>() {
				
				@Override
				public void onSuccess(String[] result) {
					
					if(result[0].equals("true")) {
						
						Window.alert("Saved Successfully");
						loadList();
												
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
	private void empty(){
		
		textBox_id.setText("");
		textBox_name.setText("");
		listBox_project.setSelectedIndex(0);
		listBox_user.setSelectedIndex(0);
		listBox_priority.setSelectedIndex(0);
		textArea_desc.setText(" ");
		textBox_status.setText("");
		
		textBox_name.setFocus(true);
	}
	
	private void loadProjectList() {
		
		greetingService.listProjectServer(new AsyncCallback<List<Projectbean>>() {
					
					@Override
					public void onSuccess(List<Projectbean> result) {
						
						listBox.clear();
						listProject = result;
						
						for (Iterator<Projectbean> iterator = result.iterator(); iterator
								.hasNext();) {
							Projectbean entityBean = (Projectbean) iterator.next();
							
							listBox_project.addItem(entityBean.getProjectname());
							
						}
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						ErrorBox box = new ErrorBox(caught.getLocalizedMessage());
						box.center();
						box.show();
					}
				});
			}
	
	private void loadLoginList() {
		
		greetingService.listLoginServer(new AsyncCallback<List<Loginbean>>() {
			
			@Override
			public void onSuccess(List<Loginbean> result) {
				
				listBox.clear();
				listLogin = result;
				
				for (Iterator<Loginbean> iterator = result.iterator(); iterator
						.hasNext();) {
					Loginbean entityBean = (Loginbean) iterator.next();
					
					listBox_user.addItem(entityBean.getLoginid());
				
					
				}
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				ErrorBox box = new ErrorBox(caught.getLocalizedMessage());
				box.center();
				box.show();
			}
		});
	}

	private void loadPriorityList() {
	
	greetingService.listPriorityServer(new AsyncCallback<List<Prioritybean>>() {
		
		@Override
		public void onSuccess(List<Prioritybean> result) {
			
			listBox.clear();
			listPriority = result;
			
			for (Iterator<Prioritybean> iterator = result.iterator(); iterator
					.hasNext();) {
				Prioritybean entityBean = (Prioritybean) iterator.next();
				listBox_priority.addItem(entityBean.getPrioritytype());
				
				
				
				
			}
			
		}
		
		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			ErrorBox box = new ErrorBox(caught.getLocalizedMessage());
			box.center();
			box.show();
		}
	});
}

	
private void loadList() {
		
		greetingService.listBugServer(new AsyncCallback<List<Bugbean>>() {
			
			@Override
			public void onSuccess(List<Bugbean> result) {
				
				listBox.clear();
				listBug = result;
				
				for (Iterator<Bugbean> iterator = result.iterator(); iterator
						.hasNext();) {
					Bugbean entityBean = (Bugbean) iterator.next();
					listBox.addItem(entityBean.getBugname());
					
					
				}
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				ErrorBox box = new ErrorBox(caught.getLocalizedMessage());
				box.center();
				box.show();
			}
		});
	}
}
