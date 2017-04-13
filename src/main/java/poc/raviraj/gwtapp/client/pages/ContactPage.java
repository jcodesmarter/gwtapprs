package poc.raviraj.gwtapp.client.pages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ContactPage extends Composite {

	private static ContactPageUiBinder uiBinder = GWT.create(ContactPageUiBinder.class);

	interface ContactPageUiBinder extends UiBinder<Widget, ContactPage> {
	}

	public ContactPage() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
