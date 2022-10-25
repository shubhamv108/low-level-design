package code.shubam.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class ConfigurationController {

    private final ConfigurationRepository repository;

    @Autowired
    public ConfigurationController(final ConfigurationRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Collection<Configuration> getByService(@RequestParam("serviceName") String serviceName) {
        return this.repository.fetchByServiceName(serviceName);
    }

}
