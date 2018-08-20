package pl.bartekk;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import pl.bartekk.model.Account;
import pl.bartekk.model.User;
import pl.bartekk.service.RevolutService;

@Path("/revolut")
@Produces(MediaType.APPLICATION_JSON)
public class Controller {

    private RevolutService service = RevolutService.getInstance();

    @POST
    @Path("/createUser")
    public void createUser(@QueryParam("name") String name) {
        service.createNewUser(name);
    }

    @GET
    @Path("/getUser")
    public User getUser(@QueryParam("name") String name) {
        return service.getUser(name);
    }

    @POST
    @Path("/deposit")
    public void deposit(@QueryParam("name") String name, @QueryParam("amount") double amount) {
        User user = service.getUser(name);
        Account account = user.getAccount();
        account.addMoney(amount);
    }

    @POST
    @Path("/transfer")
    public void transferMoney(@QueryParam("from") String from, @QueryParam("to") String to, @QueryParam("amount") double amount) {
        User userFrom = service.getUser(from);
        Account accountFrom = userFrom.getAccount();
        User userTo = service.getUser(to);
        Account accountTo = userTo.getAccount();
        if (accountFrom.getBalance() > amount) {
            accountFrom.subtractMoney(amount);
            accountTo.addMoney(amount);
        } else {
            throw new RuntimeException();
        }
    }

}