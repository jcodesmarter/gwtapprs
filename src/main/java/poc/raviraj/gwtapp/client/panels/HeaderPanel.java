package poc.raviraj.gwtapp.client.panels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;

import poc.raviraj.gwtapp.client.pages.AboutPage;
import poc.raviraj.gwtapp.client.pages.ContactPage;
import poc.raviraj.gwtapp.client.pages.HomePage;

public class HeaderPanel extends Composite {

	private static HeaderPanelUiBinder uiBinder = GWT.create(HeaderPanelUiBinder.class);

	interface HeaderPanelUiBinder extends UiBinder<Widget, HeaderPanel> {
	}

	@UiField
	MenuItem homeLink;
	@UiField
	MenuItem aboutLink;
	@UiField
	MenuItem contactLink;

	public HeaderPanel() {
		initWidget(uiBinder.createAndBindUi(this));

		homeLink.setScheduledCommand(new ScheduledCommand() {
			@Override
			public void execute() {
				ContentPanel.changePanelContent(new HomePage());
			}
		});

		aboutLink.setScheduledCommand(new ScheduledCommand() {
			@Override
			public void execute() {
				ContentPanel.changePanelContent(new AboutPage());
			}
		});

		contactLink.setScheduledCommand(new ScheduledCommand() {
			@Override
			public void execute() {
				ContentPanel.changePanelContent(new ContactPage());
			}
		});
	}

}
