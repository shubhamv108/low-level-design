package code.shubam.configuration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Integer> {
    Collection<Configuration> fetchByServiceName(String serviceName);
}
