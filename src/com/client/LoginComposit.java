package com.client;

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
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class LoginComposit extends Composite{
	public TextBox textBox_user;
	public PasswordTextBox passwordTextBox;
	public Button btnLogin;
	
	public LoginComposit() {
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		initWidget(horizontalPanel);
		horizontalPanel.setSize("100%", "100%");
		
		DecoratorPanel decoratorPanel = new DecoratorPanel();
		horizontalPanel.add(decoratorPanel);
		horizontalPanel.setCellHorizontalAlignment(decoratorPanel, HasHorizontalAlignment.ALIGN_CENTER);
		
		FlexTable flexTable = new FlexTable();
		decoratorPanel.setWidget(flexTable);
		flexTable.setSize("800px", "100%");
		
		Label lblBugTrackingSystem = new Label("Login");
		flexTable.setWidget(0, 0, lblBugTrackingSystem);
		lblBugTrackingSystem.setStyleName("heading");
		flexTable.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
		
		Image image = new Image("images/bug.png");
		flexTable.setWidget(1, 0, image);
		flexTable.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
		
		CaptionPanel cptnpnlLogin = new CaptionPanel("Login");
		cptnpnlLogin.setCaptionHTML("");
		flexTable.setWidget(2, 0, cptnpnlLogin);
		cptnpnlLogin.setSize("400px", "100%");
		
		
		FlexTable flexTable_1 = new FlexTable();
		flexTable_1.setCellPadding(5);
		cptnpnlLogin.setContentWidget(flexTable_1);
		flexTable_1.setSize("100%", "");
		
		Label lblLogin = new Label("login");
		flexTable_1.setWidget(0, 0, lblLogin);
		
		textBox_user = new TextBox();
		flexTable_1.setWidget(0, 1, textBox_user);
		textBox_user.setWidth("300px");
		
		Label lblPassword = new Label("Password");
		flexTable_1.setWidget(1, 0, lblPassword);
		
		passwordTextBox = new PasswordTextBox();
		flexTable_1.setWidget(1, 1, passwordTextBox);
		passwordTextBox.setWidth("300px");
		
		btnLogin = new Button("Login");
		
		flexTable_1.setWidget(2, 1, btnLogin);
		btnLogin.setWidth("100px");
		flexTable_1.getCellFormatter().setHorizontalAlignment(2, 1, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_CENTER);
	}

}
