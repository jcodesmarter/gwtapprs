package poc.raviraj.gwtapp.client.pages;

import java.util.List;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.ContextMenuHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;

import poc.raviraj.gwtapp.client.GwtApp;
import poc.raviraj.gwtapp.client.OkCancelMessageDialog;
import poc.raviraj.gwtapp.client.PleaseWaitDialog;
import poc.raviraj.gwtapp.client.UserDataContextMenu;
import poc.raviraj.gwtapp.client.panels.ContentPanel;
import poc.raviraj.gwtapp.client.service.UserService;
import poc.raviraj.gwtapp.shared.User;

public class ViewRegisteredUsers extends Composite {

	private static ViewRegisteredUsersUiBinder uiBinder = GWT.create(ViewRegisteredUsersUiBinder.class);

	interface ViewRegisteredUsersUiBinder extends UiBinder<Widget, ViewRegisteredUsers> {
	}

	@UiField
	TextBox runTimeSearch;
	@UiField
	CellTable<User> userCellTable;
	@UiField
	SimplePager pager;

	private ListDataProvider<User> userDataProvider;
	private PleaseWaitDialog pleaseWait = new PleaseWaitDialog();
	private Timer timer = null;

	private static UserService userService = GWT.create(UserService.class);

	public ViewRegisteredUsers() {
		this(0);
	}

	public ViewRegisteredUsers(final int pageNo) {
		initWidget(uiBinder.createAndBindUi(this));

		addUserIdColumn(userCellTable);
		addFirstNameColumn(userCellTable);
		addLastNameColumn(userCellTable);
		addGenderColumn(userCellTable);
		addDateOfJoiningColumn(userCellTable);
		addUserNameColumn(userCellTable);
		addDepartmentColumn(userCellTable);
		addDesignationColumn(userCellTable);
		addRoleColumn(userCellTable);
		addEditButtonColumn(userCellTable);
		addNewCopyButtonColumn(userCellTable);
		addDeleteButtonColumn(userCellTable);

		// configuring ListDataProvider
		userDataProvider = new ListDataProvider<User>();
		userDataProvider.addDataDisplay(userCellTable);

		// on double click.. load userregistration form with the data
		final SingleSelectionModel<User> userSelectionModel = new SingleSelectionModel<User>();
		userCellTable.setSelectionModel(userSelectionModel);

		userCellTable.addDomHandler(new DoubleClickHandler() {
			@Override
			public void onDoubleClick(final DoubleClickEvent event) {
				User selected = userSelectionModel.getSelectedObject();
				if (selected != null) {
					ContentPanel.changePanelContent(new UserRegistrationPage(selected.getUserId()));
				}

			}
		}, DoubleClickEvent.getType());

		// Context Menu to get delete option for the row
		final UserDataContextMenu userDataContextMenu = new UserDataContextMenu(userSelectionModel, pager);

		userCellTable.sinkEvents(Event.ONCONTEXTMENU);
		userCellTable.addHandler(new ContextMenuHandler() {
			@Override
			public void onContextMenu(ContextMenuEvent event) {
				event.preventDefault();
				event.stopPropagation();

				PopupPanel contextMenu = userDataContextMenu.getContextMenu();
				contextMenu.setPopupPosition(event.getNativeEvent().getClientX(), event.getNativeEvent().getClientY());
				contextMenu.show();
			}
		}, ContextMenuEvent.getType());

		pager.setDisplay(userCellTable);

		pleaseWait.show();
		searchAllUsers();
	}

	private void addUserIdColumn(CellTable<User> userCellTable) {
		TextColumn<User> userIdColumn = new TextColumn<User>() {
			@Override
			public String getValue(User user) {
				return String.valueOf(user.getUserId());
			}
		};
		userCellTable.addColumn(userIdColumn, "ID");
	}

	private void addFirstNameColumn(CellTable<User> userCellTable) {
		TextColumn<User> firstNameColumn = new TextColumn<User>() {
			@Override
			public String getValue(User user) {
				return user.getFirstName();
			}
		};
		userCellTable.addColumn(firstNameColumn, "First Name");
	}

	private void addLastNameColumn(CellTable<User> userCellTable) {
		TextColumn<User> lastNameColumn = new TextColumn<User>() {
			@Override
			public String getValue(User user) {
				return user.getLastName();
			}
		};
		userCellTable.addColumn(lastNameColumn, "Last Name");
	}

	private void addGenderColumn(CellTable<User> userCellTable) {
		TextColumn<User> genderColumn = new TextColumn<User>() {
			@Override
			public String getValue(User user) {
				return user.getGender().getKeyName();
			}
		};
		userCellTable.addColumn(genderColumn, "Gender");
	}

	private void addDateOfJoiningColumn(CellTable<User> userCellTable) {
		final DateTimeFormat dtf = DateTimeFormat.getFormat("MM/dd/yyyy");
		TextColumn<User> dateOfJoiningColumn = new TextColumn<User>() {
			@Override
			public String getValue(User user) {
				return dtf.format(user.getDateOfJoining());
			}
		};
		userCellTable.addColumn(dateOfJoiningColumn, "Date of Joining");
	}

	private void addUserNameColumn(CellTable<User> userCellTable) {
		TextColumn<User> userNameColumn = new TextColumn<User>() {
			@Override
			public String getValue(User user) {
				return user.getUserName();
			}
		};
		userCellTable.addColumn(userNameColumn, "Username");
	}

	private void addDepartmentColumn(CellTable<User> userCellTable) {
		TextColumn<User> departmentColumn = new TextColumn<User>() {
			@Override
			public String getValue(User user) {
				return user.getDepartment().getKeyName();
			}
		};
		userCellTable.addColumn(departmentColumn, "Department");
	}

	private void addDesignationColumn(CellTable<User> userCellTable) {
		TextColumn<User> designationColumn = new TextColumn<User>() {
			@Override
			public String getValue(User user) {
				return user.getDesignation().getKeyName();
			}
		};
		userCellTable.addColumn(designationColumn, "Designation");
	}

	private void addRoleColumn(CellTable<User> userCellTable) {
		TextColumn<User> roleColumn = new TextColumn<User>() {
			@Override
			public String getValue(User user) {
				return user.getRole().getKeyName();
			}
		};
		userCellTable.addColumn(roleColumn, "Role");
	}

	private void addEditButtonColumn(CellTable<User> userCellTable) {
		Column<User, String> editUserBtn = new Column<User, String>(new ButtonCell()) {
			@Override
			public String getValue(final User object) {
				return "edit";
			}
		};

		editUserBtn.setFieldUpdater(new FieldUpdater<User, String>() {
			@Override
			public void update(int index, final User user, String value) {
				ContentPanel.changePanelContent(new UserRegistrationPage(user.getUserId()));
			}
		});

		userCellTable.addColumn(editUserBtn);
	}

	private void addNewCopyButtonColumn(CellTable<User> userCellTable) {
		Column<User, String> newCopyUserBtn = new Column<User, String>(new ButtonCell()) {
			@Override
			public String getValue(final User object) {
				return "new copy";
			}
		};

		newCopyUserBtn.setFieldUpdater(new FieldUpdater<User, String>() {
			@Override
			public void update(int index, final User user, String value) {
				ContentPanel.changePanelContent(new UserRegistrationPage(user.getUserId(), true));
			}
		});

		userCellTable.addColumn(newCopyUserBtn);
	}

	private void addDeleteButtonColumn(CellTable<User> userCellTable) {
		Column<User, String> deleteUserBtn = new Column<User, String>(new ButtonCell()) {
			@Override
			public String getValue(final User object) {
				return "del";
			}
		};

		deleteUserBtn.setFieldUpdater(new FieldUpdater<User, String>() {
			@Override
			public void update(int index, final User user, String value) {
				final OkCancelMessageDialog confirmBox = new OkCancelMessageDialog("Warning", "This will delete the user. Please click Ok to continue deleting.");
				confirmBox.setOkButtonClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						confirmBox.hide();
						pleaseWait.show();
						deleteUser(user, pager.getPage());

					}
				});
				confirmBox.setCanelButtonClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						confirmBox.hide();
					}
				});
				confirmBox.show();
			}
		});

		userCellTable.addColumn(deleteUserBtn);
	}

	private int counter = 0;

	@UiHandler("runTimeSearch")
	public void runtimeUserSearch_onKeyUpHandler(KeyUpEvent event) {

		if (timer != null) {
			counter++;
			timer.cancel();
		}
		timer = new Timer() {
			@Override
			public void run() {
				searchAllUsers();
			}
		};
		timer.schedule(500);

	}

	private void searchAllUsers() {

		userService.findAll(runTimeSearch.getText().trim(), new MethodCallback<List<User>>() {

			@Override
			public void onSuccess(Method method, List<User> users) {
				userCellTable.setRowData(0, users);
				userCellTable.setRowCount(users.size());

				userDataProvider.setList(users);
				pager.setPage(0);
				pleaseWait.hide();

				GWT.log("Finally ran after " + counter + " number of keyup events");
				counter = 0;
			}

			@Override
			public void onFailure(Method method, Throwable exception) {
				pleaseWait.hide();
				GwtApp.showErrorMessage("Failed to load registered users list: " + exception.getMessage());
				counter = 0;
			}
		});
	}

	public static void deleteUser(User user, final int currentPage) {
		
		userService.delete(user.getUserId(), new MethodCallback<User>() {

			@Override
			public void onFailure(Method method, Throwable exception) {
				GwtApp.showErrorMessage("Failed to delete user : " + exception.getMessage());
			}

			@Override
			public void onSuccess(Method method, User response) {
				ContentPanel.changePanelContent(new ViewRegisteredUsers(currentPage));
				GwtApp.showSuccessMessage("Successfully deleted the user");				
			}
		});
	}
}
