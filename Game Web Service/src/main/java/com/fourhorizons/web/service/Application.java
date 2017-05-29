package com.fourhorizons.web.service;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth.provider.OAuthAuthenticationHandler;
import org.springframework.security.oauth.provider.OAuthProcessingFilterEntryPoint;
import org.springframework.security.oauth.provider.filter.ProtectedResourceProcessingFilter;
import org.springframework.security.oauth.provider.nonce.InMemoryNonceServices;
import org.springframework.security.oauth.provider.nonce.OAuthNonceServices;
import org.springframework.security.oauth.provider.token.InMemoryProviderTokenServices;
import org.springframework.security.oauth.provider.token.OAuthProviderTokenServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fourhorizons.web.service.web.security.OAuthConsumerDetailsService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackageClasses = { Application.class })
public class Application {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setJpaVendorAdapter(jpaVendorAdapter());
		em.setPackagesToScan("com.horizon.web.gameservice.model");
		return em;
	}

	@Bean
	public HibernateJpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setShowSql(true);
		jpaVendorAdapter.setGenerateDdl(true);
		jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQLDialect");
		return jpaVendorAdapter;
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/gameservice");
		dataSource.setUsername("postgres");
		dataSource.setPassword("");
		return dataSource;
	}
	

	@Configuration
    @Order(1) // HIGHEST
    public static class OAuthSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        private ZeroLeggedOAuthProviderProcessingFilter zeroLeggedOAuthProviderProcessingFilter;
        @Autowired
        OAuthConsumerDetailsService oauthConsumerDetailsService;
        @Autowired
        OAuthAuthenticationHandler oauthAuthenticationHandler;
        @Autowired
        OAuthProcessingFilterEntryPoint oauthProcessingFilterEntryPoint;
        @Autowired
        OAuthProviderTokenServices oauthProviderTokenServices;
        @PostConstruct
        public void init() {
            zeroLeggedOAuthProviderProcessingFilter = new ZeroLeggedOAuthProviderProcessingFilter(oauthConsumerDetailsService, new InMemoryNonceServices(), oauthProcessingFilterEntryPoint, oauthAuthenticationHandler, oauthProviderTokenServices);
        }
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/game/**")
                    .addFilterBefore(zeroLeggedOAuthProviderProcessingFilter, UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests().anyRequest().hasRole("OAUTH");
            http.csrf().disable();
        }
    }
	
    @Order(100) // LOWEST
    @Configuration
    public static class NoAuthConfigurationAdapter extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/**").authorizeRequests().anyRequest().permitAll();
            http.csrf().disable();
        }
    }	
    
    public static class ZeroLeggedOAuthProviderProcessingFilter extends ProtectedResourceProcessingFilter {
        ZeroLeggedOAuthProviderProcessingFilter(OAuthConsumerDetailsService oAuthConsumerDetailsService, OAuthNonceServices oAuthNonceServices, OAuthProcessingFilterEntryPoint oAuthProcessingFilterEntryPoint, OAuthAuthenticationHandler oAuthAuthenticationHandler, OAuthProviderTokenServices oAuthProviderTokenServices) {
            super();
            System.out.println("CONSTRUCT Zero Legged OAuth provider");
            setAuthenticationEntryPoint(oAuthProcessingFilterEntryPoint);
            setAuthHandler(oAuthAuthenticationHandler);
            setConsumerDetailsService(oAuthConsumerDetailsService);
            setNonceServices(oAuthNonceServices);
            setTokenServices(oAuthProviderTokenServices);
        }
    }
    
    public static class OAuthProcessingFilterEntryPointImpl extends OAuthProcessingFilterEntryPoint {
    	@Override
    	public void commence(HttpServletRequest arg0, HttpServletResponse arg1, AuthenticationException arg2)
    			throws IOException, ServletException {
    		super.commence(arg0, arg1, arg2);
    	}
    }    
	
    @Bean(name = "oauthAuthenticationEntryPoint")
    public OAuthProcessingFilterEntryPoint oauthAuthenticationEntryPoint() {
        return new OAuthProcessingFilterEntryPointImpl();
    }

    @Bean(name = "oauthProviderTokenServices")
    public OAuthProviderTokenServices oauthProviderTokenServices() {
        // NOTE: we don't use the OAuthProviderTokenServices for 0-legged but it cannot be null
        return new InMemoryProviderTokenServices();
    }   	
}