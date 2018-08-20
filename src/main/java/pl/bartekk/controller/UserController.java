package pl.bartekk.controller;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pl.bartekk.service.UserService;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

    private UserService userService = UserService.getInstance();

    /**
     * Create new user.
     *
     * @param name Name of the new user.
     * @return Response.ok() after successful user creation.
     */
    @POST
    @Path("/new")
    public Response createUser(@QueryParam("name") String name) {
        userService.createNewUser(name);
        return Response.status(Response.Status.CREATED).build();
    }

    /**
     * Get details about particular user.
     *
     * @param name Name of the user.
     * @return Response.ok() and user entity if found.
     */
    @GET
    @Path("/details")
    public Response getUser(@QueryParam("name") String name) {
        return Response.ok().entity(userService.getUser(name)).build();
    }

    /**
     * Get list of all registered users.
     *
     * @return Response.ok() and list of all users available in database.
     */
    @GET
    @Path("/all")
    public Response getAllUsers() {
        return Response.ok().entity(userService.getAllUsers()).build();
    }
}