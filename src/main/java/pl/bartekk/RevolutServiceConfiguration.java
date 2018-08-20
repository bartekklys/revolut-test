package pl.bartekk;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class RevolutServiceConfiguration extends Configuration {

    @NotEmpty
    @JsonProperty
    private String template;

    @NotEmpty
    @JsonProperty
    private String defaultName = "Bartek";

    public String getTemplate() {
        return template;
    }

    public String getDefaultName() {
        return defaultName;
    }
}
