package com.example.springboot.pet_clinic.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {

	// add a reference to our security data source
	private static final String ADMIN = "ADMIN";
	private static final String OWNER = "OWNER";
	@Autowired
	@Qualifier("securityDataSource")
	private DataSource securityDataSource;


	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.jdbcAuthentication().dataSource(securityDataSource).passwordEncoder(new BCryptPasswordEncoder());

	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/pets/list").hasRole(ADMIN)
				.antMatchers("/pets/form*").hasRole(ADMIN)
				.antMatchers("/pets/save*").hasRole(ADMIN)
				.antMatchers("/pets/delete").hasRole(ADMIN)
				.antMatchers("/owners/list").hasRole(ADMIN)
				.antMatchers("/owners/pets").hasRole(OWNER)
				.antMatchers("/owners/form*").hasRole(ADMIN)
				.antMatchers("/owners/save*").hasRole(ADMIN)
				.antMatchers("/owners/update*").hasRole(ADMIN)
				.antMatchers("/owners/delete").hasRole(ADMIN)
				.antMatchers("/appointments/list").hasAnyRole(ADMIN,OWNER)
				.antMatchers("/appointments/form*").hasRole(ADMIN)
				.antMatchers("/appointments/save*").hasRole(ADMIN)
				.antMatchers("/appointments/delete").hasRole(ADMIN)
				.antMatchers("/resources/**").permitAll()
				.and()
				.formLogin()
				.loginPage("/show-login-page")
				.defaultSuccessUrl("/home")
				.loginProcessingUrl("/authenticateTheUser")
				.permitAll()
				.and()
				.logout()
				.permitAll()
				.and()
				.exceptionHandling()
				.accessDeniedPage("/access-denied");
	}
		
}






