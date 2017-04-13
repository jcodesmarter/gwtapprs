package poc.raviraj.gwtapp.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class PleaseWaitDialog extends Composite {

	private static PleaseWaitDialogUiBinder uiBinder = GWT.create(PleaseWaitDialogUiBinder.class);

	interface PleaseWaitDialogUiBinder extends UiBinder<Widget, PleaseWaitDialog> {
	}
	
	@UiField PopupPanel pleaseWaitDialogBox;

	public PleaseWaitDialog() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void show(){
		pleaseWaitDialogBox.show();
		pleaseWaitDialogBox.center();
	}
	
	public void hide(){
		pleaseWaitDialogBox.hide();
	}
	

}
