package poc.raviraj.gwtapp.client.pages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class AboutPage extends Composite {

	private static AboutPageUiBinder uiBinder = GWT.create(AboutPageUiBinder.class);

	interface AboutPageUiBinder extends UiBinder<Widget, AboutPage> {
	}

	public AboutPage() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
