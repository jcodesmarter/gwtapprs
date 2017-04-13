package poc.raviraj.gwtapp.client.service;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import poc.raviraj.gwtapp.shared.User;

@Path("/users")
public interface UserService extends RestService {
	
	@POST
	public void save(User user, MethodCallback<User> callback);
	
	@PUT
	public void update(User user, MethodCallback<User> callback);
	
	@GET
	public void findAll(@QueryParam("search") String search, MethodCallback<List<User>> callback);
	
	@GET
	@Path("/{userId}")
	public void find(Long userId, MethodCallback<User> callback);
	
	@DELETE
	@Path("/{userId}")
	public void delete(Long userId, MethodCallback<User> callback);

}
