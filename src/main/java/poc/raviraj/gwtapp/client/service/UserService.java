package poc.raviraj.gwtapp.client.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import poc.raviraj.gwtapp.shared.User;

@Path("/users")
public interface UserService extends RestService {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void save(User user, MethodCallback<User> callback);
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void update(User user, MethodCallback<User> callback);
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public void findAll(@QueryParam("search") String search, MethodCallback<List<User>> callback);
	
	@GET
	@Path("/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public void find(@PathParam("userId") Long userId, MethodCallback<User> callback);
	
	@DELETE
	@Path("/{userId}/{version}")
	@Produces(MediaType.APPLICATION_JSON)
	public void delete(@PathParam("userId") Long userId, @PathParam("version") Long version, MethodCallback<Boolean> callback);

}
