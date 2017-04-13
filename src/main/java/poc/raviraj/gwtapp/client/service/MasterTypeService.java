package poc.raviraj.gwtapp.client.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import poc.raviraj.gwtapp.shared.Department;
import poc.raviraj.gwtapp.shared.Designation;
import poc.raviraj.gwtapp.shared.Gender;
import poc.raviraj.gwtapp.shared.Role;

@Path("/mastertypes")
public interface MasterTypeService extends RestService {

	@GET
	@Path("/genders")
	public void getAllGenders(MethodCallback<List<Gender>> genders);
	
	@GET
	@Path("/departments")
	public void getAllDepartments(MethodCallback<List<Department>> departments);
	
	@GET
	@Path("/designations")
	public void getAllDesignations(MethodCallback<List<Designation>> desginations);
	
	@GET
	@Path("/roles")
	public void getAllRoles(MethodCallback<List<Role>> roles);
}
