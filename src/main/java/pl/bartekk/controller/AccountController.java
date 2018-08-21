package pl.bartekk.controller;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import pl.bartekk.exception.NotEnoughFundsException;
import pl.bartekk.service.AccountService;

@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
public class AccountController {

    private final AccountService accountService = AccountService.getInstance();

    /**
     * Deposit particular amount of money to specific account.
     *
     * @param name   Name of the account owner.
     * @param amount Amount of the money we want to deposit.
     * @return Response.ok() after successful money deposit.
     */
    @POST
    @Path("/deposit")
    public Response deposit(@QueryParam("name") String name, @QueryParam("amount") BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) != 0) {
            accountService.updateBalance(name, amount);
            return Response.ok().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("Cannot deposit 0 value.").build();
    }

    /**
     * Withdraw particular amount of money from specific account.
     *
     * @param name   Name of the account owner.
     * @param amount
     * @return Response.ok() after successful money withdraw.
     */
    @POST
    @Path("/withdraw")
    public Response withdraw(@QueryParam("name") String name, @QueryParam("amount") BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) != 0) {
            accountService.updateBalance(name, amount.negate());
            return Response.ok().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("Cannot withdraw 0 value.").build();
    }

    /**
     * Transfer specific amount of money from account <code>from</code> to account <code>to</code>.
     *
     * @param from   Owner name of account we want to transfer money from.
     * @param to     Owner name of account we want to transfer money to.
     * @param amount Amount of the money we want to transfer.
     * @return Response.ok() after successful money transfer or {@link NotEnoughFundsException} when
     * account from we want to transfer money has not enough funds.
     */
    @POST
    @Path("/transfer")
    public Response transferMoney(@QueryParam("from") String from, @QueryParam("to") String to, @QueryParam("amount") BigDecimal amount) {
        try {
            accountService.transferMoney(from, to, amount);
            return Response.ok().build();
        } catch (NotEnoughFundsException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
