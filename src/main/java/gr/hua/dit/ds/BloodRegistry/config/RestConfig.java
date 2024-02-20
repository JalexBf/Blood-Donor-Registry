package gr.hua.dit.ds.BloodRegistry.config;

import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;
import gr.hua.dit.ds.BloodRegistry.entities.model.Donation;
import gr.hua.dit.ds.BloodRegistry.entities.model.Application;
import gr.hua.dit.ds.BloodRegistry.entities.model.Secretariat;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;


@Configuration
public class RestConfig implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(
            RepositoryRestConfiguration config, CorsRegistry cors) {
        config.exposeIdsFor(BloodDonor.class);
        config.exposeIdsFor(Secretariat.class);
        config.exposeIdsFor(Application.class);
        config.exposeIdsFor(Donation.class);
    }
}
