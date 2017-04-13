package poc.raviraj.gwtapp.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class OkMessageDialog extends Composite {

	private static OkMessageDialogUiBinder uiBinder = GWT.create(OkMessageDialogUiBinder.class);

	interface OkMessageDialogUiBinder extends UiBinder<Widget, OkMessageDialog> {
	}
	
	@UiField
	DialogBox okMessageDialogBox;
	@UiField
	Label okMessageDialogBox_MESSAGE;
	@UiField
	Button okMessageDialogBox_OK;

	public OkMessageDialog() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public OkMessageDialog(String header, String message){
		this();
		okMessageDialogBox.setText(header);
		okMessageDialogBox_MESSAGE.setText(message);
	}
	
	public void setOkButtonClickHandler(ClickHandler okButtonClickHandler){
		okMessageDialogBox_OK.addClickHandler(okButtonClickHandler);
	}
	
	public void show(){
		okMessageDialogBox.show();
		okMessageDialogBox.center();
	}
	
	public void hide(){
		okMessageDialogBox.hide();
	}
	
	@UiHandler("okMessageDialogBox_OK")
	public void okMessageDialogBox_OK(ClickEvent event){
		hide();
	}
}
