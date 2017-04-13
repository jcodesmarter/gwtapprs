package poc.raviraj.gwtapp.client.panels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class ContentPanel extends Composite {

	private static ContentPanelUiBinder uiBinder = GWT.create(ContentPanelUiBinder.class);

	interface ContentPanelUiBinder extends UiBinder<Widget, ContentPanel> {
	}

	@UiField
	static SimplePanel contentPanel;

	public ContentPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public static void changePanelContent(Composite newContent) {
		contentPanel.clear();
		contentPanel.add(newContent);
	}
}
