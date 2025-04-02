package org.csm;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.csm.auth.SimpleAuthenticator;
import org.csm.auth.SimpleAuthorizer;
import org.csm.controller.SessionResource;
import org.csm.dao.SessionDAO;
import org.csm.util.ValidationExceptionMapper;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.auth.PrincipalImpl;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.auth.AuthValueFactoryProvider;
import org.flywaydb.core.Flyway;

public class ConferenceSessionManagerApplication extends Application<ConferenceConfiguration> {
    public static void main(String[] args) throws Exception {
        new ConferenceSessionManagerApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<ConferenceConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(
                        bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );
    }

    @Override
    public void run(ConferenceConfiguration config, Environment environment) {
        try {
            Flyway flyway = Flyway.configure()
                    .dataSource(config.getDataSourceFactory().getUrl(),
                            config.getDataSourceFactory().getUser(),
                            config.getDataSourceFactory().getPassword())
                    .load();

            flyway.migrate();
        } catch (Exception e) {
            throw new RuntimeException("Flyway migration failed: " + e.getMessage(), e);
        }

        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, config.getDataSourceFactory(), "mysql");
        jdbi.installPlugin(new SqlObjectPlugin());

        SessionDAO sessionDAO = jdbi.onDemand(SessionDAO.class);
        environment.jersey().register(new SessionResource(sessionDAO));

        environment.jersey().register(new ValidationExceptionMapper());

        setupAuthentication(environment);
    }

    /**
     * Configures authentication
     */
    private void setupAuthentication(Environment environment) {
        environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<PrincipalImpl>()
                        .setAuthenticator(new SimpleAuthenticator())
                        .setAuthorizer(new SimpleAuthorizer())
                        .setRealm("SESSION API")
                        .buildAuthFilter()
        ));

        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(PrincipalImpl.class));
    }
}