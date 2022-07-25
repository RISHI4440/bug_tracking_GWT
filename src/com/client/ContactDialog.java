package com.client;

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Image;

public class ContactDialog extends DialogBox{
	public ContactDialog() {
		setAnimationEnabled(true);
		setAutoHideEnabled(true);
		setHTML("Contact Us");
		
		FlexTable flexTable = new FlexTable();
		setWidget(flexTable);
		flexTable.setSize("400px", "300px");
		
		Image image = new Image("images/icon.png");
		flexTable.setWidget(0, 0, image);
		
		Label lblVersion = new Label("Version 1.0");
		lblVersion.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.setWidget(1, 0, lblVersion);
		
		Label lblDevelopedBy = new Label("Developed By");
		lblDevelopedBy.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.setWidget(2, 0, lblDevelopedBy);
		
		Label lblXyz = new Label("	");
		lblXyz.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.setWidget(3, 0, lblXyz);
		
		Label lblPqr = new Label("	");
		lblPqr.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.setWidget(4, 0, lblPqr);
		
		Label lblShwetaMBokade = new Label("	");
		flexTable.setWidget(5, 0, lblShwetaMBokade);
		
		Label lblNewLabel = new Label("Guide:- 	");
		lblNewLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.setWidget(6, 0, lblNewLabel);
		
		Button btnClose = new Button("Close");
		btnClose.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				setVisible(false);
			}
		});
		flexTable.setWidget(7, 0, btnClose);
		flexTable.getFlexCellFormatter().setHorizontalAlignment(7, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_LEFT);
		flexTable.getCellFormatter().setHorizontalAlignment(6, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getCellFormatter().setHorizontalAlignment(5, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
	}

}
