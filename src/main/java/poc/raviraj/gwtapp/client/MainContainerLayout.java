package poc.raviraj.gwtapp.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class MainContainerLayout extends Composite {

	private static MainContainerLayoutUiBinder uiBinder = GWT.create(MainContainerLayoutUiBinder.class);

	interface MainContainerLayoutUiBinder extends UiBinder<Widget, MainContainerLayout> {
	}

	public MainContainerLayout() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
