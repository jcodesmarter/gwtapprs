package poc.raviraj.gwtapp.client;

import org.fusesource.restygwt.client.Defaults;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class GwtApp implements EntryPoint {

	public static MainContainerLayout CONTAINER = new MainContainerLayout();

	private static String REST_BASE_URL = "http://localhost:8080/cxfrspoc";	

	public void onModuleLoad() {
		Defaults.setServiceRoot(REST_BASE_URL);
        Defaults.setDateFormat(null);

		RootLayoutPanel rootLayoutPanel = RootLayoutPanel.get();
		rootLayoutPanel.add(CONTAINER);

	}

	public static void showErrorMessage(String message) {
		final OkMessageDialog msgbox = new OkMessageDialog("Error", message);
		msgbox.setOkButtonClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				msgbox.hide();
			}
		});
		msgbox.show();
	}
	
	public static void showSuccessMessage(String message){
		final OkMessageDialog msgbox = new OkMessageDialog("Success", message);
		msgbox.setOkButtonClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				msgbox.hide();
			}
		});
		msgbox.show();
	}
}
