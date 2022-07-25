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
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
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
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.datepicker.client.DateBox;
import com.shared.Loginbean;
import com.shared.Prioritybean;
import com.shared.Projectbean;

public class ProjectComposit extends Composite{
	private ListBox listBox;
	private Label label_status;
	private TextBox textBox_id;
	private TextBox textBox_status;
	private ListBox listBox_loginId;
	private Button btnNew;
	private Button btnSave;
	private TextBox textBox_name;
	private DateBox dateBox_start;
	private DateBox dateBox_end;
	
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	
	List<Projectbean> listProject;
	List<Loginbean> listLogin;
	boolean isUpdate = false;
	Projectbean bean;
	
	public ProjectComposit() {
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		initWidget(horizontalPanel);
		horizontalPanel.setSize("100%", "100%");
		
		DecoratorPanel decoratorPanel = new DecoratorPanel();
		horizontalPanel.add(decoratorPanel);
		horizontalPanel.setCellHorizontalAlignment(decoratorPanel, HasHorizontalAlignment.ALIGN_CENTER);
		
		FlexTable flexTable = new FlexTable();
		decoratorPanel.setWidget(flexTable);
		flexTable.setSize("800px", "100%");
		
		Label lblBugTrackingSystem = new Label("Project Administration");
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
		
		Label lblStudentId = new Label("Project Id:");
		flexTable_1.setWidget(1, 0, lblStudentId);
		
		textBox_id = new TextBox();
		textBox_id.setEnabled(false);
		flexTable_1.setWidget(1, 1, textBox_id);
		textBox_id.setWidth("300px");
		
		Label lblStudentName = new Label("Project Name");
		flexTable_1.setWidget(2, 0, lblStudentName);
		
		textBox_name = new TextBox();
		flexTable_1.setWidget(2, 1, textBox_name);
		textBox_name.setWidth("300px");
		
		Label lblContactNo = new Label("Start Date:");
		flexTable_1.setWidget(3, 0, lblContactNo);
		
		dateBox_start = new DateBox();
		dateBox_start.setFormat(new DateBox.DefaultFormat(DateTimeFormat
				.getFormat("dd/MM/yyyy")));
		flexTable_1.setWidget(3, 1, dateBox_start);
		dateBox_start.setWidth("300px");
		
		Label lblEndDate = new Label("End Date:");
		flexTable_1.setWidget(4, 0, lblEndDate);
		
		dateBox_end = new DateBox();
		dateBox_end.setFormat(new DateBox.DefaultFormat(DateTimeFormat
				.getFormat("dd/MM/yyyy")));
		flexTable_1.setWidget(4, 1, dateBox_end);
		dateBox_end.setWidth("300px");
		
		Label lblStatus = new Label("Status");
		flexTable_1.setWidget(5, 0, lblStatus);
		
		textBox_status = new TextBox();
		flexTable_1.setWidget(5, 1, textBox_status);
		textBox_status.setWidth("300px");
		
		Label lblDepartment = new Label("Manager");
		flexTable_1.setWidget(6, 0, lblDepartment);
		
		listBox_loginId = new ListBox();
		flexTable_1.setWidget(6, 1, listBox_loginId);
		listBox_loginId.setSize("305px", "30px");
		
		Grid grid_1 = new Grid(2, 2);
		flexTable_1.setWidget(7, 1, grid_1);
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
		loadLoginList();
	}
	
	private void loadList() {
		
		greetingService.listProjectServer(new AsyncCallback<List<Projectbean>>() {
			
			@Override
			public void onSuccess(List<Projectbean> result) {
				
				listBox.clear();
				listProject = result;
				
				for (Iterator<Projectbean> iterator = result.iterator(); iterator
						.hasNext();) {
					Projectbean entityBean = (Projectbean) iterator.next();
					listBox.addItem(entityBean.getProjectname());
					
					
					HorizontalPanel hp = new HorizontalPanel();
					hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
					hp.setStyleName("back");
					
					
					
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
				
				listBox_loginId.addItem(entityBean.getLoginid());
				
			
				
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
	
	private void save() {
		
		if(textBox_name.getText().isEmpty()||dateBox_start.getValue()==null||dateBox_end.getValue()==null||textBox_status.getText().isEmpty()) {
			
			Window.alert("Required fields cannot be empty!");
			
		}else {
			
			bean = new Projectbean();
			bean.setProjectname(textBox_name.getText());
			bean.setStartdate(dateBox_start.getValue());
			bean.setEnddate(dateBox_end.getValue());
			bean.setStatus(textBox_status.getText());
			
			Loginbean lbean = listLogin.get(listBox_loginId.getSelectedIndex());
			bean.setLoginid(lbean);
			
						
			if(isUpdate) {
				
				//bean.setPriorityid(Integer.parseInt(textBox_id.getText()));
			}
			
			greetingService.saveProjectServer(bean, isUpdate, new AsyncCallback<String[]>() {
				
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
		textBox_status.setText("");
		listBox_loginId.setSelectedIndex(0);
		
		textBox_name.setFocus(true);
	}
}
