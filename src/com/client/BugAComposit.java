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
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.client.ui.TextArea;
import com.shared.Bugassignedbean;
import com.shared.Bugbean;
import com.shared.Loginbean;
import com.shared.Projectbean;
import com.shared.Projectmemberbean;

public class BugAComposit extends Composite{
	private ListBox listBox;
	private Label label_status;
	private TextBox textBox_id;
	private ListBox listBox_loginId;
	private Button btnNew;
	private Button btnSave;
	private DateBox dateBox_ass;
	
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	
	List<Bugassignedbean> listBugA;
	List<Bugbean> listBug;
	List<Loginbean> listLogin;
	
	boolean isUpdate = false;
	Bugassignedbean bean;
	private ListBox listBox_bug;
	
	public BugAComposit() {
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		initWidget(horizontalPanel);
		horizontalPanel.setSize("100%", "100%");
		
		DecoratorPanel decoratorPanel = new DecoratorPanel();
		horizontalPanel.add(decoratorPanel);
		horizontalPanel.setCellHorizontalAlignment(decoratorPanel, HasHorizontalAlignment.ALIGN_CENTER);
		
		FlexTable flexTable = new FlexTable();
		decoratorPanel.setWidget(flexTable);
		flexTable.setSize("800px", "100%");
		
		Label lblBugTrackingSystem = new Label("Bug Assigned");
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
		
		Label lblStudentId = new Label("Bug Assigned Id:");
		flexTable_1.setWidget(1, 0, lblStudentId);
		
		textBox_id = new TextBox();
		textBox_id.setEnabled(false);
		flexTable_1.setWidget(1, 1, textBox_id);
		textBox_id.setWidth("300px");
		
		Label lblBug = new Label("Bug");
		flexTable_1.setWidget(2, 0, lblBug);
		
		listBox_bug = new ListBox();
		flexTable_1.setWidget(2, 1, listBox_bug);
		listBox_bug.setSize("305px", "30px");
		
		Label lblStatus = new Label("Developer");
		flexTable_1.setWidget(3, 0, lblStatus);
		
		listBox_loginId = new ListBox();
		flexTable_1.setWidget(3, 1, listBox_loginId);
		listBox_loginId.setSize("305px", "30px");
		
		Label lblContactNo = new Label("Assigned Date");
		flexTable_1.setWidget(4, 0, lblContactNo);
		
		dateBox_ass = new DateBox();
		flexTable_1.setWidget(4, 1, dateBox_ass);
		dateBox_ass.setWidth("300px");
		
		Grid grid_1 = new Grid(2, 2);
		flexTable_1.setWidget(5, 1, grid_1);
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
		loadBugList();
	}
	
private void save(){
		
		if(listBox_bug.getSelectedIndex()<0||listBox_loginId.getSelectedIndex()<0||dateBox_ass.getValue()==null) {
			
			Window.alert("Required fields cannot be empty!");
			
		}else {
			
			bean = new Bugassignedbean();
			
			
			bean.setBugid((listBug.get(listBox_bug.getSelectedIndex())));
			bean.setAssdate(dateBox_ass.getValue());
			bean.setLoginid(listLogin.get(listBox_loginId.getSelectedIndex()));
			
			
			
						
			if(isUpdate) {
				
				//bean.setPriorityid(Integer.parseInt(textBox_id.getText()));
			}
			
			greetingService.saveBugAServer(bean, isUpdate, new AsyncCallback<String[]>() {
				
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
	
	private void empty() {
		
		textBox_id.setText("");
		listBox_loginId.setSelectedIndex(0);
		listBox_bug.setSelectedIndex(0);
		
		listBox_bug.setSelectedIndex(0);
	}
	
	private void loadList() {
		
		greetingService.listBugAServer(new AsyncCallback<List<Bugassignedbean>>() {
			
			@Override
			public void onSuccess(List<Bugassignedbean> result) {
				
				listBox.clear();
				listBugA = result;
				
				for (Iterator<Bugassignedbean> iterator = result.iterator(); iterator
						.hasNext();) {
					Bugassignedbean entityBean = (Bugassignedbean) iterator.next();
					listBox.addItem(entityBean.getBugid().getBugname()+" - "+entityBean.getBugaid());
					
					
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
	
	private void loadBugList() {
		
		greetingService.listBugServer(new AsyncCallback<List<Bugbean>>() {
			
			@Override
			public void onSuccess(List<Bugbean> result) {
				
				
				listBug = result;
				
				for (Iterator<Bugbean> iterator = result.iterator(); iterator
						.hasNext();) {
					Bugbean entityBean = (Bugbean) iterator.next();
					listBox_bug.addItem(entityBean.getBugname());
					
					
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

}
