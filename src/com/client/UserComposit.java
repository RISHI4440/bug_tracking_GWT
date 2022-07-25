package com.client;

import java.util.Date;
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
import com.shared.Loginprofilebean;
import com.shared.Prioritybean;
import com.shared.Questionbasebean;

public class UserComposit extends Composite{
	private ListBox listBox;
	private Label label_status;
	private TextBox textBox_id;
	private TextBox textBox_pin;
	private Button btnNew;
	private Button btnSave;
	private PasswordTextBox passwordTextBox;
	private TextBox textBox_first;
	private TextBox textBox_last;
	private ListBox listBox_type;
	private DateBox dateBox_reg;
	private TextBox textBox_hno;
	private TextBox textBox_street;
	private TextBox textBox_city;
	private TextBox textBox_state;
	private TextBox textBox_country;
	private TextBox textBox_contact;
	private TextBox textBox_email;
	private ListBox listBox_security;
	private DateBox dateBox_birth;
	private TextBox textBox_ans;
	
	
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	
	List<Loginbean> listLogin;
	List<Questionbasebean> listQuestion;
	boolean isUpdate = false;
	Loginbean bean;
	Loginprofilebean loginprofilebean;
	
	public UserComposit() {
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		initWidget(horizontalPanel);
		horizontalPanel.setSize("100%", "100%");
		
		DecoratorPanel decoratorPanel = new DecoratorPanel();
		horizontalPanel.add(decoratorPanel);
		horizontalPanel.setCellHorizontalAlignment(decoratorPanel, HasHorizontalAlignment.ALIGN_CENTER);
		
		FlexTable flexTable = new FlexTable();
		decoratorPanel.setWidget(flexTable);
		flexTable.setSize("800px", "100%");
		
		Label lblBugTrackingSystem = new Label("User Administration");
		flexTable.setWidget(0, 0, lblBugTrackingSystem);
		lblBugTrackingSystem.setStyleName("heading");
		flexTable.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
		flexTable.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
		
		HorizontalSplitPanel horizontalSplitPanel = new HorizontalSplitPanel();
		horizontalSplitPanel.setSplitPosition("500px");
		flexTable.setWidget(1, 0, horizontalSplitPanel);
		horizontalSplitPanel.setSize("794px", "1000px");
		
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
		
		Label lblStudentId = new Label("User Name:");
		flexTable_1.setWidget(1, 0, lblStudentId);
		
		textBox_id = new TextBox();
		flexTable_1.setWidget(1, 1, textBox_id);
		textBox_id.setWidth("300px");
		
		Label lblStudentName = new Label("Password");
		flexTable_1.setWidget(2, 0, lblStudentName);
		
		passwordTextBox = new PasswordTextBox();
		flexTable_1.setWidget(2, 1, passwordTextBox);
		passwordTextBox.setWidth("300px");
		
		Label lblContactNo = new Label("First Name");
		flexTable_1.setWidget(3, 0, lblContactNo);
		
		textBox_first = new TextBox();
		flexTable_1.setWidget(3, 1, textBox_first);
		textBox_first.setWidth("300px");
		
		Label lblLastName = new Label("Last Name");
		flexTable_1.setWidget(4, 0, lblLastName);
		
		textBox_last = new TextBox();
		flexTable_1.setWidget(4, 1, textBox_last);
		textBox_last.setWidth("300px");
		
		Label lblLoginType = new Label("Login Type");
		flexTable_1.setWidget(5, 0, lblLoginType);
		
		listBox_type = new ListBox();
		listBox_type.addItem("ADMIN");
		listBox_type.addItem("MANAGER");
		listBox_type.addItem("DEVELOPER");
		listBox_type.addItem("TESTER");
		flexTable_1.setWidget(5, 1, listBox_type);
		listBox_type.setSize("305px", "30px");
		
		Label lblRegistrationDate = new Label("Registration Date");
		flexTable_1.setWidget(6, 0, lblRegistrationDate);
		
		dateBox_reg = new DateBox();
		dateBox_reg.setFormat(new DateBox.DefaultFormat(DateTimeFormat
				.getFormat("dd/MM/yyyy")));
		flexTable_1.setWidget(6, 1, dateBox_reg);
		dateBox_reg.setWidth("300px");
		
		Label lblBirthDate = new Label("Birth Date");
		flexTable_1.setWidget(7, 0, lblBirthDate);
		
		dateBox_birth = new DateBox();
		dateBox_birth.setFormat(new DateBox.DefaultFormat(DateTimeFormat
				.getFormat("dd/MM/yyyy")));
		flexTable_1.setWidget(7, 1, dateBox_birth);
		dateBox_birth.setWidth("300px");
		
		Label lblHouseNo = new Label("House No");
		flexTable_1.setWidget(8, 0, lblHouseNo);
		
		textBox_hno = new TextBox();
		flexTable_1.setWidget(8, 1, textBox_hno);
		textBox_hno.setWidth("300px");
		
		Label lblStreet = new Label("Street");
		flexTable_1.setWidget(9, 0, lblStreet);
		
		textBox_street = new TextBox();
		flexTable_1.setWidget(9, 1, textBox_street);
		textBox_street.setWidth("300px");
		
		Label lblCity = new Label("City");
		flexTable_1.setWidget(10, 0, lblCity);
		
		textBox_city = new TextBox();
		flexTable_1.setWidget(10, 1, textBox_city);
		textBox_city.setWidth("300px");
		
		Label lblState = new Label("State");
		flexTable_1.setWidget(11, 0, lblState);
		
		textBox_state = new TextBox();
		flexTable_1.setWidget(11, 1, textBox_state);
		textBox_state.setWidth("300px");
		
		Label lblCountry = new Label("Country");
		flexTable_1.setWidget(12, 0, lblCountry);
		
		textBox_country = new TextBox();
		flexTable_1.setWidget(12, 1, textBox_country);
		textBox_country.setWidth("300px");
		
		Label lblStatus = new Label("Pin Code");
		flexTable_1.setWidget(13, 0, lblStatus);
		
		textBox_pin = new TextBox();
		flexTable_1.setWidget(13, 1, textBox_pin);
		textBox_pin.setWidth("300px");
		
		Label lblContactNo_1 = new Label("Contact No");
		flexTable_1.setWidget(14, 0, lblContactNo_1);
		
		textBox_contact = new TextBox();
		flexTable_1.setWidget(14, 1, textBox_contact);
		textBox_contact.setWidth("300px");
		
		Label lblEmail = new Label("Email");
		flexTable_1.setWidget(15, 0, lblEmail);
		
		textBox_email = new TextBox();
		flexTable_1.setWidget(15, 1, textBox_email);
		textBox_email.setWidth("300px");
		
		Label lblSecurityQuestion = new Label("Security Question");
		flexTable_1.setWidget(16, 0, lblSecurityQuestion);
		
		listBox_security = new ListBox();
		listBox_security.addItem("Who is your child hood hero?");
		listBox_security.addItem("Who is your best Teacher?");
		listBox_security.addItem("What is your birth Place?");
		listBox_security.addItem("Your First Mobile NO?");
		listBox_security.addItem("The Movie Name You Like?");
		flexTable_1.setWidget(16, 1, listBox_security);
		listBox_security.setSize("305px", "30px");
		
		Label lblAnswer = new Label("Answer");
		flexTable_1.setWidget(17, 0, lblAnswer);
		
		textBox_ans = new TextBox();
		flexTable_1.setWidget(17, 1, textBox_ans);
		textBox_ans.setWidth("300px");
		
		Grid grid_1 = new Grid(2, 2);
		flexTable_1.setWidget(18, 1, grid_1);
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
		
		//load
		
		loadList();
	}
	private void empty(){
		textBox_id.setText("");
		passwordTextBox.setText("");
		textBox_first.setText("");
		textBox_last.setText("");
		listBox_type.setSelectedIndex(0);
		textBox_hno.setText("");
		textBox_street.setText("");
		textBox_city.setText("");
		textBox_state.setText("");
		textBox_country.setText("");
		textBox_contact.setText("");
		textBox_pin.setText("");
		textBox_email.setText("");
		textBox_id.setFocus(true);
		
	}
	
	private void save() {
		
		if(textBox_first.getText().isEmpty()||textBox_last.getText().isEmpty()||textBox_ans.getText().isEmpty()||textBox_city.getText().isEmpty()||textBox_country.getText().isEmpty()||textBox_email.getText().isEmpty()||textBox_hno.getText().isEmpty()||textBox_pin.getText().isEmpty()||textBox_state.getText().isEmpty()||textBox_street.getText().isEmpty()) {
			
			Window.alert("Required fields cannot be empty!");
		}else {
			
			bean = new Loginbean();
			bean.setLoginid(textBox_id.getText());
			bean.setPassword(passwordTextBox.getText());
			bean.setFirstname(textBox_first.getText());
			bean.setLastname(textBox_last.getText());
			bean.setLogintype(listBox_type.getItemText(listBox_type.getSelectedIndex()));
			bean.setLoginstatus("never");
			bean.setRegdate(new Date());
			
			Questionbasebean questionbasebean = new Questionbasebean();
			questionbasebean.setQuestionid(listBox_security.getSelectedIndex()+1);
			bean.setQuestionid(questionbasebean);
			
			bean.setSanswer(textBox_ans.getText());
						
			bean.setFirstlogin(new Date());
			bean.setFirstlogin(new Date());
			bean.setPassmodifieddate(new Date());
						
			loginprofilebean = new Loginprofilebean();
			loginprofilebean.setBirthdate(dateBox_birth.getValue());
			loginprofilebean.setHno(textBox_hno.getText());
			loginprofilebean.setStreet(textBox_street.getText());
			loginprofilebean.setCity(textBox_city.getText());
			loginprofilebean.setState(textBox_state.getText());
			loginprofilebean.setCountry(textBox_country.getText());
			loginprofilebean.setPincode(textBox_pin.getText());
			loginprofilebean.setContactno(textBox_contact.getText());
			loginprofilebean.setEmail(textBox_email.getText());
			loginprofilebean.setProfilemodifieddate(new Date());
			
			
			
			if(isUpdate) {
				
				//need
			}
			
			greetingService.saveLoginServer(bean, loginprofilebean, isUpdate, new AsyncCallback<String[]>() {
				
				@Override
				public void onSuccess(String[] result) {
					
					if(result[0].equals("true")) {
						
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
		
		greetingService.listLoginServer(new AsyncCallback<List<Loginbean>>() {
			
			@Override
			public void onSuccess(List<Loginbean> result) {
				
				listBox.clear();
				listLogin = result;
				
				for (Iterator<Loginbean> iterator = result.iterator(); iterator
						.hasNext();) {
					Loginbean entityBean = (Loginbean) iterator.next();
					listBox.addItem(entityBean.getLoginid());
					
					
					
					
					
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
