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
	private static final String admin = "ADMIN";
	private static final String owner = "OWNER";
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
				.antMatchers("/pets/list").hasRole(admin)
				.antMatchers("/pets/form*").hasRole(admin)
				.antMatchers("/pets/save*").hasRole(admin)
				.antMatchers("/pets/delete").hasRole(admin)
				.antMatchers("/owners/list").hasRole(admin)
				.antMatchers("/owners/pets").hasRole(owner)
				.antMatchers("/owners/form*").hasRole(admin)
				.antMatchers("/owners/save*").hasRole(admin)
				.antMatchers("/owners/update*").hasRole(admin)
				.antMatchers("/owners/delete").hasRole(admin)
				.antMatchers("/appointments/list").hasAnyRole(admin,owner)
				.antMatchers("/appointments/form*").hasRole( admin)
				.antMatchers("/appointments/save*").hasRole(admin)
				.antMatchers("/appointments/delete").hasRole(admin)
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






