package poc.raviraj.gwtapp.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class OkCancelMessageDialog extends Composite {

	private static OkCancelMessageDialogUiBinder uiBinder = GWT.create(OkCancelMessageDialogUiBinder.class);

	interface OkCancelMessageDialogUiBinder extends UiBinder<Widget, OkCancelMessageDialog> {
	}
	

	@UiField
	DialogBox okCancelMessageDialogBox;
	@UiField
	Label okCancelMessageDialogBox_MESSAGE;
	@UiField
	Button okCancelMessageDialogBox_OK;
	@UiField
	Button okCancelMessageDialogBox_CANCEL;

	public OkCancelMessageDialog() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public OkCancelMessageDialog(String header, String message){
		this();		
		okCancelMessageDialogBox.setText(header);
		okCancelMessageDialogBox_MESSAGE.setText(message);
	}
	
	public void setOkButtonClickHandler(ClickHandler okButtonClickHandler){
		okCancelMessageDialogBox_OK.addClickHandler(okButtonClickHandler);
	}
	
	public void setCanelButtonClickHandler(ClickHandler cancelButtonClickHandler){
		okCancelMessageDialogBox_CANCEL.addClickHandler(cancelButtonClickHandler);
	}
	
	public void show(){
		okCancelMessageDialogBox.show();
		okCancelMessageDialogBox.center();
	}
	
	public void hide(){
		okCancelMessageDialogBox.hide();
	}

}
