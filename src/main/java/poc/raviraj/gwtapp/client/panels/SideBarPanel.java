package poc.raviraj.gwtapp.client.panels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import poc.raviraj.gwtapp.client.pages.UserRegistrationPage;
import poc.raviraj.gwtapp.client.pages.ViewRegisteredUsers;

public class SideBarPanel extends Composite {

	private static SideBarPanelUiBinder uiBinder = GWT.create(SideBarPanelUiBinder.class);

	interface SideBarPanelUiBinder extends UiBinder<Widget, SideBarPanel> {
	}

	@UiField Anchor userRegistrationForm;
	@UiField Anchor viewRegisteredUsers;
	
	public SideBarPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("userRegistrationForm")
	public void userRegistrationFormClickHandler(ClickEvent event){
		ContentPanel.changePanelContent(new UserRegistrationPage(0L));
	}
	
	@UiHandler("viewRegisteredUsers")
	public void viewRegisteredUsersClickHandler(ClickEvent event){
		ContentPanel.changePanelContent(new ViewRegisteredUsers());
	}
}
