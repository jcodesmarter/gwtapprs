package poc.raviraj.gwtapp.client.pages;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SimpleHtmlSanitizer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.validation.client.impl.Validation;

import poc.raviraj.gwtapp.client.GwtApp;
import poc.raviraj.gwtapp.client.OkCancelMessageDialog;
import poc.raviraj.gwtapp.client.PleaseWaitDialog;
import poc.raviraj.gwtapp.client.service.MasterTypeService;
import poc.raviraj.gwtapp.client.service.UserService;
import poc.raviraj.gwtapp.shared.Department;
import poc.raviraj.gwtapp.shared.Designation;
import poc.raviraj.gwtapp.shared.Gender;
import poc.raviraj.gwtapp.shared.Role;
import poc.raviraj.gwtapp.shared.User;

public class UserRegistrationPage extends Composite {

	private static UserRegistrationUiBinder uiBinder = GWT.create(UserRegistrationUiBinder.class);

	interface UserRegistrationUiBinder extends UiBinder<Widget, UserRegistrationPage> {
	}

	private static DateTimeFormat dateFormat = DateTimeFormat.getFormat("MM/dd/yyyy");

	@UiField
	Label mode;
	@UiField
	Hidden userId;
	@UiField
	Hidden version;
	@UiField
	TextBox firstName;
	@UiField
	TextBox lastName;
	@UiField
	TextBox userName;
	@UiField
	DateBox dateOfJoining;
	@UiField
	ListBox department;
	@UiField
	ListBox designation;
	@UiField
	ListBox role;
	@UiField
	Button saveButton;
	@UiField
	Button resetButton;
	@UiField
	FormPanel userRegistrationForm;
	@UiField
	FlowPanel radioButtonPanelForGender;
	@UiField
	Hidden gender;

	@UiField
	HTML validationMessageSection;

	private List<Gender> genderList;
	private List<RadioButton> genderRadioButtonList;
	private List<Department> departmentList;
	private List<Designation> designationList;
	private List<Role> roleList;

	private final PleaseWaitDialog pleaseWait = new PleaseWaitDialog();

	private UserService userService = GWT.create(UserService.class);
	private MasterTypeService masterTypeService = GWT.create(MasterTypeService.class);

	public UserRegistrationPage() {
		this(0L);
	}

	public UserRegistrationPage(final Long uid) {
		this(uid, false);
	}

	public UserRegistrationPage(final Long uid, final boolean resetUserIdToZeroForCreatingNewUserCopy) {
		initWidget(uiBinder.createAndBindUi(this));

		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {

				pleaseWait.show();
				// setting focus on first name input box
				firstName.setFocus(true);

				// setting default value and date format for date of joining
				// field
				dateOfJoining.setFormat(new DateBox.DefaultFormat(dateFormat));
				dateOfJoining.setValue(new Date(), true);
				dateOfJoining.getTextBox().setReadOnly(true);

				// loading list box options for genders from RPC request
				loadGenderOptions();
				loadDepartmentListOptions();
				loadDesignationListOptions();
				loadRoleListOptions();
				if (uid > 0) {
					// loading user for updation
					loadUser(uid, resetUserIdToZeroForCreatingNewUserCopy);
				}
				pleaseWait.hide();
			}
		});

	}

	@UiHandler("saveButton")
	public void saveButton_ClickHandler(ClickEvent event) {
		validationMessageSection.setHTML("");
		saveButton.setText("Saving ...");
		saveButton.setEnabled(false);

		User user = new User();
		user.setUserId(Long.valueOf(userId.getValue()));
		user.setVersion(Long.valueOf(version.getValue()));
		user.setFirstName(firstName.getText());
		user.setLastName(lastName.getText());

		Gender genderObj = new Gender();
		genderObj.setKeyId(Long.parseLong(gender.getValue()));
		user.setGender(genderObj);

		user.setDateOfJoining(dateOfJoining.getValue());
		user.setUserName(userName.getText());

		Department departmentObj = new Department();
		departmentObj.setKeyId(Long.parseLong(department.getSelectedValue()));
		user.setDepartment(departmentObj);

		Designation designationObj = new Designation();
		designationObj.setKeyId(Long.parseLong(designation.getSelectedValue()));
		user.setDesignation(designationObj);

		Role roleObj = new Role();
		roleObj.setKeyId(Long.parseLong(role.getSelectedValue()));
		user.setRole(roleObj);

		// validation of user object code
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		// validate on the client
		Set<ConstraintViolation<User>> violations = validator.validate(user, Default.class);
		if (!violations.isEmpty()) {
			SafeHtmlBuilder sb = new SafeHtmlBuilder();
			sb.appendHtmlConstant("<h3>Validation Message(s):</h3>");
			sb.appendHtmlConstant("<ul>");
			Iterator<ConstraintViolation<User>> itrViolations = violations.iterator();
			while (itrViolations.hasNext()) {
				ConstraintViolation<User> userViolation = itrViolations.next();
				if (userViolation.getPropertyPath() != null) {
					sb.appendHtmlConstant("<li>");
					sb.append(SimpleHtmlSanitizer.sanitizeHtml(userViolation.getMessage()));
					sb.appendHtmlConstant("</li>");
				}
			}
			sb.appendHtmlConstant("</ul>");
			validationMessageSection.setHTML(sb.toSafeHtml());
			saveButton.setEnabled(true);
			saveButton.setText("Save");
		} else {
			saveUser(user);
		}
	}

	@UiHandler("resetButton")
	public void resetButton_ClickHandler(ClickEvent event) {
		final OkCancelMessageDialog msgbox = new OkCancelMessageDialog("Warning", "This will reset updated form data. Click Ok to confirm.");
		msgbox.setOkButtonClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				userRegistrationForm.reset();
				validationMessageSection.setHTML("");
				dateOfJoining.setValue(new Date(), true);
				msgbox.hide();
			}
		});
		msgbox.setCanelButtonClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				msgbox.hide();
			}
		});
		msgbox.show();
	}

	private void loadUser(Long uid, final boolean resetUserIdToZeroForCreatingNewUserCopy) {

		userService.find(uid, new MethodCallback<User>() {

			@Override
			public void onFailure(Method method, Throwable exception) {
				pleaseWait.hide();
				GwtApp.showErrorMessage("Failed to load user : " + exception.getMessage());
			}

			@SuppressWarnings("deprecation")
			@Override
			public void onSuccess(Method method, User user) {
				pleaseWait.hide();
				if (resetUserIdToZeroForCreatingNewUserCopy) {
					mode.setText("[New Copy User]");
				} else {
					mode.setText("[Updating Existing User]");
					userId.setValue(String.valueOf(user.getUserId()));
				}
				version.setValue(String.valueOf(user.getVersion()));
				firstName.setText(user.getFirstName());
				lastName.setText(user.getLastName());

				gender.setValue(String.valueOf(user.getGender().getKeyId()));
				for (Gender g : genderList) {
					if (g.getKeyId() == user.getGender().getKeyId()) {
						for (RadioButton radioBtn : genderRadioButtonList) {
							if (radioBtn.getText().equals(g.getKeyName())) {
								radioBtn.setChecked(true);
							}
						}
					}
				}

				dateOfJoining.setValue(user.getDateOfJoining());
				userName.setText(user.getUserName());

				for (Department d : departmentList) {
					if (d.getKeyId() == user.getDepartment().getKeyId()) {
						department.setSelectedIndex(departmentList.indexOf(d) + 1);
					}
				}

				for (Designation d : designationList) {
					if (d.getKeyId() == user.getDesignation().getKeyId()) {
						designation.setSelectedIndex(designationList.indexOf(d) + 1);
					}
				}

				for (Role r : roleList) {
					if (r.getKeyId() == user.getRole().getKeyId()) {
						role.setSelectedIndex(roleList.indexOf(r) + 1);
					}
				}

			}
		});
	}

	private void saveUser(User user) {
		userService.save(user, new MethodCallback<User>() {
			@Override
			public void onSuccess(Method method, User response) {
				saveButton.setEnabled(true);
				saveButton.setText("Save");
				userRegistrationForm.reset();
				dateOfJoining.setValue(new Date(), true);
				GwtApp.showSuccessMessage("User has been registered successfully!");
			}

			@Override
			public void onFailure(Method method, Throwable exception) {
				saveButton.setEnabled(true);
				saveButton.setText("Save");
				GwtApp.showErrorMessage("Failed to register user");
			}
		});
	}

	public void loadGenderOptions() {
		masterTypeService.getAllGenders(new MethodCallback<List<Gender>>() {
			@Override
			public void onFailure(Method method, Throwable exception) {
				GwtApp.showErrorMessage("Failed to load gender options");
			}

			@Override
			public void onSuccess(Method method, List<Gender> listOfGenders) {
				genderList = listOfGenders;
				genderRadioButtonList = new ArrayList<RadioButton>();
				for (final Gender g : listOfGenders) {
					RadioButton radioButton = new RadioButton("genderOptions");
					radioButton.setText(g.getKeyName());
					radioButton.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							gender.setValue(String.valueOf(g.getKeyId()));
						}
					});
					genderRadioButtonList.add(radioButton);
					radioButtonPanelForGender.add(radioButton);
				}
			}
		});
	}

	public void loadDepartmentListOptions() {
		masterTypeService.getAllDepartments(new MethodCallback<List<Department>>() {

			@Override
			public void onFailure(Method method, Throwable exception) {
				GwtApp.showErrorMessage("Failed to load department list options");
			}

			@Override
			public void onSuccess(Method method, List<Department> listOfDepartments) {
				departmentList = listOfDepartments;
				for (Department d : listOfDepartments) {
					department.addItem(d.getKeyName(), String.valueOf(d.getKeyId()));
				}
			}

		});
	}

	public void loadDesignationListOptions() {
		masterTypeService.getAllDesignations(new MethodCallback<List<Designation>>() {

			@Override
			public void onFailure(Method method, Throwable exception) {
				GwtApp.showErrorMessage("Failed to load designation list options");
			}

			@Override
			public void onSuccess(Method method, List<Designation> listOfDesignations) {
				designationList = listOfDesignations;
				for (Designation d : listOfDesignations) {
					designation.addItem(d.getKeyName(), String.valueOf(d.getKeyId()));
				}
			}

		});
	}

	public void loadRoleListOptions() {
		masterTypeService.getAllRoles(new MethodCallback<List<Role>>() {

			@Override
			public void onFailure(Method method, Throwable exception) {
				GwtApp.showErrorMessage("Failed to load designation list options");
			}

			@Override
			public void onSuccess(Method method, List<Role> listOfRoles) {
				roleList = listOfRoles;
				for (Role r : listOfRoles) {
					role.addItem(r.getKeyName(), String.valueOf(r.getKeyId()));
				}
			}
		});
	}

}
