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
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.shared.Bugbean;
import com.shared.Prioritybean;
import com.shared.Solutionbean;
import com.google.gwt.user.client.ui.TextArea;

public class ReportComposit extends Composite{
	private ListBox listBox;
	
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	
	List<Prioritybean> listPriority;
	boolean isUpdate = false;
	
	private TextBox textBox_bug;
	private TextBox textBox_project;
	private TextBox textBox_status;
	List<Bugbean> listBug;
	List<Solutionbean> listsolution;
	Bugbean bean;
	private TextArea textArea_soln;
	
	public ReportComposit() {
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		initWidget(horizontalPanel);
		horizontalPanel.setSize("100%", "100%");
		
		DecoratorPanel decoratorPanel = new DecoratorPanel();
		horizontalPanel.add(decoratorPanel);
		horizontalPanel.setCellHorizontalAlignment(decoratorPanel, HasHorizontalAlignment.ALIGN_CENTER);
		
		FlexTable flexTable = new FlexTable();
		decoratorPanel.setWidget(flexTable);
		flexTable.setSize("800px", "100%");
		
		Label lblBugTrackingSystem = new Label("Bug Status Report");
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
				
				int i = listBox.getSelectedIndex();
				
				if(i>-1) {
					
					bean = listBug.get(i);
					
					textBox_bug.setText(bean.getBugname());
					textBox_project.setText(bean.getProjectid().getProjectname());
					textBox_status.setText(bean.getStatus());
					
					listsolution = (List<Solutionbean>) bean.getSolutionCollection();
					
					textArea_soln.setText("");
					
					for(int j=0;j<listsolution.size();j++) {
						
						Solutionbean bean2 = listsolution.get(i);
						textArea_soln.setText(bean2.getSolution());
						
					}
				}
				
				
				
				
			}
		});
		horizontalSplitPanel.setRightWidget(listBox);
		listBox.setSize("100%", "100%");
		listBox.setVisibleItemCount(5);
		
		FlexTable flexTable_1 = new FlexTable();
		flexTable_1.setCellPadding(5);
		horizontalSplitPanel.setLeftWidget(flexTable_1);
		flexTable_1.setSize("100%", "");
		
		Label lblNewLabel = new Label("Bug Name:");
		flexTable_1.setWidget(0, 0, lblNewLabel);
		
		textBox_bug = new TextBox();
		textBox_bug.setReadOnly(true);
		flexTable_1.setWidget(0, 1, textBox_bug);
		textBox_bug.setWidth("300px");
		
		Label lblProject = new Label("Project");
		flexTable_1.setWidget(1, 0, lblProject);
		
		textBox_project = new TextBox();
		textBox_project.setReadOnly(true);
		flexTable_1.setWidget(1, 1, textBox_project);
		textBox_project.setWidth("300px");
		
		Label lblStatus = new Label("Status");
		flexTable_1.setWidget(2, 0, lblStatus);
		
		textBox_status = new TextBox();
		flexTable_1.setWidget(2, 1, textBox_status);
		textBox_status.setWidth("300px");
		
		Button btnChangeBugStatus = new Button("Change Bug Status");
		btnChangeBugStatus.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				change();
			}
		});
		
		Label lblSolution = new Label("Solution");
		flexTable_1.setWidget(3, 0, lblSolution);
		
		textArea_soln = new TextArea();
		textArea_soln.setReadOnly(true);
		flexTable_1.setWidget(3, 1, textArea_soln);
		textArea_soln.setWidth("300px");
		flexTable_1.setWidget(4, 1, btnChangeBugStatus);
		
		//loadlist
		loadBugList();
	}
	
private void loadBugList() {
		
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
	
	private void change(){
		
		if(textBox_status.getText().isEmpty()) {
			
			Window.alert("empty status cannot be updated");
			
		}else {
			
			bean.setStatus(textBox_status.getText());
			
			greetingService.saveBugStatusServer(bean, isUpdate, new AsyncCallback<String[]>() {
				
				@Override
				public void onSuccess(String[] result) {
					
					if(result[0].equals("true")) {
						
						Window.alert("Saved Successfully");
						
												
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
	
	
}
