package com.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

public class OrgComposit extends Composite{
	public OrgComposit() {
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		initWidget(horizontalPanel);
		horizontalPanel.setSize("100%", "100%");
		
		DecoratorPanel decoratorPanel = new DecoratorPanel();
		horizontalPanel.add(decoratorPanel);
		horizontalPanel.setCellHorizontalAlignment(decoratorPanel, HasHorizontalAlignment.ALIGN_CENTER);
		
		FlexTable flexTable = new FlexTable();
		decoratorPanel.setWidget(flexTable);
		flexTable.setSize("800px", "450px");
		
		Label lblBugTrackingSystem = new Label("About Us");
		flexTable.setWidget(0, 0, lblBugTrackingSystem);
		lblBugTrackingSystem.setStyleName("heading");
		flexTable.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
	}

}
