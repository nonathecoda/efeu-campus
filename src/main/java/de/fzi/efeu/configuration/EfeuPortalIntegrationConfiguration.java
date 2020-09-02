package de.fzi.efeu.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.fzi.efeu.efeuportal.ApiClient;
import de.fzi.efeu.efeuportal.api.ContactApi;
import de.fzi.efeu.efeuportal.api.OrderApi;
import de.fzi.efeu.efeuportal.api.ProcessMgmtApi;
import de.fzi.efeu.efeuportal.auth.JwtAuthenticator;

@Configuration
public class EfeuPortalIntegrationConfiguration {

    @Value("${efeuportal.url}")
    private String efeuPortalUrl;

    @Value("${efeuportal.user}")
    private String efeuPortalUser;

    @Value("${efeuportal.password}")
    private String efeuPortalPassword;

    @Bean
    public ApiClient apiClient() {

        ApiClient apiClient = new ApiClient()
                .setVerifyingSsl(false)
                .setBasePath(efeuPortalUrl);
        apiClient.setHttpClient(apiClient
                .getHttpClient()
                .newBuilder().authenticator(new JwtAuthenticator(
                        apiClient,
                        efeuPortalUser,
                        efeuPortalPassword))
                .build());
        return apiClient;
    }

    @Bean
    public OrderApi orderApi() {
        return new OrderApi(apiClient());
    }

    @Bean
    public ContactApi contactApi() { return new ContactApi(apiClient()); }

    @Bean
    public ProcessMgmtApi processMgmtApi() { return new ProcessMgmtApi(apiClient()); }
}
