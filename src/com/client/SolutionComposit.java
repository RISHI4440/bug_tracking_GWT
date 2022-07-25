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
import com.google.gwt.user.client.ui.TextArea;
import com.shared.Bugassignedbean;
import com.shared.Bugbean;
import com.shared.Loginbean;
import com.shared.Solutionbean;

public class SolutionComposit extends Composite{
	private ListBox listBox;
	private Label label_status;
	private TextBox textBox_id;
	private ListBox listBox_loginId;
	private Button btnNew;
	private Button btnSave;
	private DateBox dateBox_solv;
	private ListBox listBox_bug;
	private TextArea textArea_soln;
	
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	
	List<Solutionbean> listSolution;
	List<Bugbean> listBug;
	List<Loginbean> listLogin;
	
	Solutionbean bean;
	boolean isUpdate = false;
	
	
	public SolutionComposit() {
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		initWidget(horizontalPanel);
		horizontalPanel.setSize("100%", "100%");
		
		DecoratorPanel decoratorPanel = new DecoratorPanel();
		horizontalPanel.add(decoratorPanel);
		horizontalPanel.setCellHorizontalAlignment(decoratorPanel, HasHorizontalAlignment.ALIGN_CENTER);
		
		FlexTable flexTable = new FlexTable();
		decoratorPanel.setWidget(flexTable);
		flexTable.setSize("800px", "100%");
		
		Label lblBugTrackingSystem = new Label("Solution");
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
		
		Label lblStudentId = new Label("Solution Id:");
		flexTable_1.setWidget(1, 0, lblStudentId);
		
		textBox_id = new TextBox();
		textBox_id.setEnabled(false);
		flexTable_1.setWidget(1, 1, textBox_id);
		textBox_id.setWidth("300px");
		
		Label lblStudentName = new Label("Bug");
		flexTable_1.setWidget(2, 0, lblStudentName);
		
		listBox_bug = new ListBox();
		flexTable_1.setWidget(2, 1, listBox_bug);
		listBox_bug.setSize("305px", "30px");
		
		Label lblStatus = new Label("Developer");
		flexTable_1.setWidget(3, 0, lblStatus);
		
		listBox_loginId = new ListBox();
		flexTable_1.setWidget(3, 1, listBox_loginId);
		listBox_loginId.setSize("305px", "30px");
		
		Label lblContactNo = new Label("Solved Date");
		flexTable_1.setWidget(4, 0, lblContactNo);
		
		dateBox_solv = new DateBox();
		dateBox_solv.setFormat(new DateBox.DefaultFormat(DateTimeFormat
				.getFormat("dd/MM/yyyy")));
		flexTable_1.setWidget(4, 1, dateBox_solv);
		dateBox_solv.setWidth("300px");
		
		Label lblDescription = new Label("Solution");
		flexTable_1.setWidget(5, 0, lblDescription);
		
		textArea_soln = new TextArea();
		flexTable_1.setWidget(5, 1, textArea_soln);
		textArea_soln.setSize("300px", "100px");
		
		Grid grid_1 = new Grid(2, 2);
		flexTable_1.setWidget(6, 1, grid_1);
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
		loadBugList();
		loadLoginList();
	}
	
	private void save(){
		
		if(listBox_bug.getSelectedIndex()<0||listBox_loginId.getSelectedIndex()<0||dateBox_solv.getValue()==null||textArea_soln.getText().isEmpty()) {
			
			Window.alert("Required fields cannot be empty!");
			
		}else {
			
			bean = new Solutionbean();
			
			
			bean.setBugid((listBug.get(listBox_bug.getSelectedIndex())));
			bean.setSolveddate(dateBox_solv.getValue());
			bean.setLoginid(listLogin.get(listBox_loginId.getSelectedIndex()));
			bean.setSolution(textArea_soln.getText());
			
			
			
						
			if(isUpdate) {
				
				//bean.setPriorityid(Integer.parseInt(textBox_id.getText()));
			}
			
			greetingService.saveSolutionServer(bean, isUpdate, new AsyncCallback<String[]>() {
				
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
	
	
	private void loadList() {
		
		greetingService.listSolutionServer(new AsyncCallback<List<Solutionbean>>() {
			
			@Override
			public void onSuccess(List<Solutionbean> result) {
				
				listBox.clear();
				listSolution = result;
				
				for (Iterator<Solutionbean> iterator = result.iterator(); iterator
						.hasNext();) {
					Solutionbean entityBean = (Solutionbean) iterator.next();
					listBox.addItem(entityBean.getSolutionid()+" - "+entityBean.getBugid().getBugname());
					
					
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
	
	private void empty(){
		textBox_id.setText("");
		listBox_bug.setSelectedIndex(0);
		listBox_loginId.setSelectedIndex(0);
		textArea_soln.setText("");
		
		listBox_bug.setFocus(true);
	}
}
