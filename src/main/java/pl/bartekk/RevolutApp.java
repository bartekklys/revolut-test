package pl.bartekk;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

/**
 * Design and implement a RESTful API (including data model and the backing implementation) for balance
 * transfers between accounts.
 * Explicit requirements:
 * 1 - keep it simple and to the point (e.g. no need to implement any authentication, assume the APi is
 * invoked by another internal system/service)
 * 2 - use whatever frameworks/libraries you like (except Spring, sorry!) but don't forget about the
 * requirement #1
 * 3 - the datastore should run in-memory for the sake of this test
 * 4 - the final result should be executable as a standalone program (should not require a pre-installed
 * container/server)
 * 5 - demonstrate with tests that the API works as expected
 * Implicit requirements:
 * 1 - the code produced by you is expected to be of high quality.
 * 2 - there are no detailed requirements, use common sense.
 */
public class RevolutApp extends Service<RevolutServiceConfiguration> {

    public static void main(String[] args) throws Exception {
        new RevolutApp().run(args);
    }

    @Override
    public void initialize(Bootstrap<RevolutServiceConfiguration> bootstrap) {
        bootstrap.setName("test");
    }

    @Override
    public void run(RevolutServiceConfiguration revolutServiceConfiguration, Environment environment) {
        environment.addResource(new Controller());
    }
}
