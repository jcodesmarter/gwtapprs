package poc.raviraj.gwtapp.client;

import javax.validation.Validator;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.validation.client.AbstractGwtValidatorFactory;
import com.google.gwt.validation.client.GwtValidation;
import com.google.gwt.validation.client.impl.AbstractGwtValidator;

import poc.raviraj.gwtapp.shared.Department;
import poc.raviraj.gwtapp.shared.Designation;
import poc.raviraj.gwtapp.shared.Gender;
import poc.raviraj.gwtapp.shared.Role;
import poc.raviraj.gwtapp.shared.User;

public class ApplicationValidatorFactory extends AbstractGwtValidatorFactory {

	@Override
	public AbstractGwtValidator createValidator() {
		return GWT.create(GwtValidator.class);
	}


	@GwtValidation(value={
			User.class,
			Gender.class,
			Department.class,
			Designation.class,
			Role.class
	})	
	public interface GwtValidator extends Validator{
		
	}
}
